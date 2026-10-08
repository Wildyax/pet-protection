package net.wildyax.petprotect.event;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.AnvilUpdateEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import net.wildyax.petprotect.PetProtect;
import net.wildyax.petprotect.component.ModDataComponent;
import net.wildyax.petprotect.component.SoulMedalDataComponent;
import net.wildyax.petprotect.item.ModItems;
import net.wildyax.petprotect.item.medal.MedalItem;
import net.wildyax.petprotect.item.medal.soul.SoulMedalItem;
import net.wildyax.petprotect.network.MedalItemData;
import net.wildyax.petprotect.utils.ModAttachments;

import java.util.Objects;
import java.util.UUID;

import com.mojang.authlib.GameProfile;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.GAME, modid = PetProtect.MOD_ID)
public class ServerEvent {

    /**
     * This event is launched when a player interact with an entity.
     * Insert or remove the medal of the pet.
     * @param event Event
     */
    @SubscribeEvent
    public static void onPlayerInteractEntity(PlayerInteractEvent.EntityInteract event) {

        var player = event.getEntity();
        var entity = event.getTarget();
        var handItem = player.getMainHandItem();

        if (handItem.getItem() instanceof MedalItem medalItem && !event.getLevel().isClientSide()) {
            if (entity instanceof TamableAnimal pet && pet.isTame() && Objects.equals(pet.getOwnerUUID(), player.getUUID())) {
                pet.setOrderedToSit(!pet.isOrderedToSit());
                if (pet.getData(ModAttachments.MEDAL_ITEM).getStackInSlot(0) == ItemStack.EMPTY) {
                    ItemStack medalStack = new ItemStack(medalItem, 1);
                    pet.getData(ModAttachments.MEDAL_ITEM).insertItem(0, medalStack, false);
                    CompoundTag tag = pet.getData(ModAttachments.MEDAL_ITEM).serializeNBT(event.getLevel().registryAccess());
                    PacketDistributor.sendToAllPlayers(new MedalItemData(pet.getId(), tag));
                    handItem.consume(1, player);
                    event.getLevel().playSound(null, pet.getX(), pet.getY(), pet.getZ(), SoundEvents.ARMOR_EQUIP_WOLF.value(), SoundSource.NEUTRAL);
                } else {
                    player.displayClientMessage(Component.translatable("message.petprotect.has_medal", pet.getName().getString()), true);
                }
            }
        } else if (handItem.getItem() == Items.SHEARS && !event.getLevel().isClientSide()) {
            if (entity instanceof TamableAnimal pet && pet.isTame() && Objects.equals(pet.getOwnerUUID(), player.getUUID())) {
                if (pet.getData(ModAttachments.MEDAL_ITEM).getStackInSlot(0) == ItemStack.EMPTY) {
                    pet.setOrderedToSit(!pet.isOrderedToSit());
                    player.displayClientMessage(Component.translatable("message.petprotect.has_no_medal", pet.getName().getString()), true);
                } else {
                    if (pet instanceof Wolf wolf && wolf.isWearingBodyArmor()) {
                        if (!player.isCrouching()) {
                            return;
                        }
                    }
                    ItemStack itemStack = pet.getData(ModAttachments.MEDAL_ITEM).extractItem(0,1,false);
                    pet.spawnAtLocation(itemStack,1);

                    CompoundTag tag = pet.getData(ModAttachments.MEDAL_ITEM).serializeNBT(event.getLevel().registryAccess());
                    PacketDistributor.sendToAllPlayers(new MedalItemData(pet.getId(), tag));
                    event.getLevel().playSound(null, pet.getX(), pet.getY(), pet.getZ(), SoundEvents.ARMOR_UNEQUIP_WOLF, SoundSource.NEUTRAL);
                    event.setCanceled(true);
                }
            }
        }
    }

    /**
     * Cancel damage event if pet has a medal and the medal
     * allow to cancel damages from the hit source.
     * @param event Event
     */
    @SubscribeEvent
    public static void onEntityIncomingDamage(LivingIncomingDamageEvent event) {
        if (!event.getEntity().level().isClientSide && event.getEntity() instanceof TamableAnimal pet && pet.isTame()) {
            if (pet.getData(ModAttachments.MEDAL_ITEM).getStackInSlot(0) != ItemStack.EMPTY
                    && event.getSource().getEntity() instanceof Player player)
            {
                Item item = pet.getData(ModAttachments.MEDAL_ITEM).getStackInSlot(0).getItem();
                if (item instanceof MedalItem medal) {
                    if (Objects.equals(player.getUUID(), pet.getOwnerUUID()) && medal.protectFromOwner()) {
                        if (pet.isInSittingPose() && !(pet instanceof Cat)) {
                            pet.setOrderedToSit(!pet.isOrderedToSit());
                        }
                        event.setCanceled(true);
                    } else {
                        if (pet.getOwner() instanceof Player petOwner && !player.canHarmPlayer(petOwner) && medal.protectFromTeamPlayer()) {
                            if (pet.isInSittingPose() && !(pet instanceof Cat)) {
                                pet.setOrderedToSit(!pet.isOrderedToSit());
                            }
                            event.setCanceled(true);
                        }
                    }
                }
            }
        }
    }

    /**
     * Save entity data on a soul medal item and let the pet
     * drop it when he dies
     * @param event Event
     */
    @SubscribeEvent
    public static void onEntityDeath(LivingDeathEvent event) {
        // Ignore if we are on client side
        if (event.getEntity().level().isClientSide()) {
            return;
        }
        if (event.getEntity() instanceof TamableAnimal pet && pet.isTame()) {
            if (pet.getData(ModAttachments.MEDAL_ITEM).getStackInSlot(0) != ItemStack.EMPTY) {
                ItemStack medalStack = pet.getData(ModAttachments.MEDAL_ITEM).getStackInSlot(0);

                if (medalStack.getItem() instanceof MedalItem medalItem) {
                    SoulMedalItem soulMedalItem = (SoulMedalItem) medalItem.getSoulMedalItem();

                    if (soulMedalItem.allowResurrection()) {
                        ItemStack soulMedalStack = new ItemStack(soulMedalItem);
                        CompoundTag entityData = new CompoundTag();
                        pet.save(entityData);
                        soulMedalStack.set(ModDataComponent.SOUL_MEDAL_DATA, new SoulMedalDataComponent(
                                pet.hasCustomName() ? Objects.requireNonNull(pet.getCustomName()).getString() : "",
                                pet.getType().toShortString(),
                                getOwnerName(pet),
                                entityData
                        ));

                        pet.spawnAtLocation(soulMedalStack, 1);
                    } else {
                        pet.spawnAtLocation(medalStack, 1);
                    }
                }
            }
        }
    }

    /**
     * Ensure that server entity data is synced with client when player tracking entity,
     * it allows to keep the medal on dog texture when game restart
     * @param event Event
     */
    @SubscribeEvent
    public static void onStartTracking(PlayerEvent.StartTracking event) {
        if (event.getTarget() instanceof TamableAnimal pet && pet.isTame()) {
            CompoundTag tag = pet.getData(ModAttachments.MEDAL_ITEM).serializeNBT(pet.registryAccess());
            PacketDistributor.sendToAllPlayers(new MedalItemData(pet.getId(), tag));
        }
    }

    /**
     * This event is called when an item is set in anvil
     * Add the recipe of the resurrection pearl
     * @param event Event
     */
    @SubscribeEvent
    public static void onAnvilUpdate(AnvilUpdateEvent event) {
        ItemStack itemLeft = event.getLeft();
        ItemStack itemRight = event.getRight();

        if (itemLeft.is(ModItems.SOUL_VESSEL.get()) && itemRight.getItem() instanceof SoulMedalItem soulMedal) {
            if (soulMedal.allowResurrection()) {
                ItemStack resurrectionPearlStack = ModItems.RESURRECTION_PEARL.toStack();
                resurrectionPearlStack.set(ModDataComponent.SOUL_MEDAL_DATA, itemRight.get(ModDataComponent.SOUL_MEDAL_DATA));

                event.setOutput(resurrectionPearlStack);
                event.setMaterialCost(1);
                event.setCost(2);
            }
        }
    }

    /**
     * Get the owner name, even if he is offline
     * @param pet Pet
     * @return Owner name, or "?" if unknown
    */
    private static String getOwnerName(TamableAnimal pet) {
        if (pet.getOwner() != null) {
            return pet.getOwner().getName().getString();
        }
        UUID ownerUUID = pet.getOwnerUUID();
        MinecraftServer server = pet.getServer();
        if (ownerUUID != null && server != null && server.getProfileCache() != null) {
            return server.getProfileCache().get(ownerUUID).map(GameProfile::getName).orElse("?");
        }
        return "?";
    }
}
