package net.wildyax.petprotection.event;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.AnvilUpdateEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.player.AnvilRepairEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.network.PacketDistributor;
import net.wildyax.petprotection.PetProtection;
import net.wildyax.petprotection.component.ModDataComponent;
import net.wildyax.petprotection.component.SoulMedalDataComponent;
import net.wildyax.petprotection.item.ModItems;
import net.wildyax.petprotection.item.medal.MedalItem;
import net.wildyax.petprotection.item.medal.soul.SoulMedalItem;
import net.wildyax.petprotection.network.MedalItemData;
import net.wildyax.petprotection.utils.ModAttachments;

import java.util.Objects;
import java.util.UUID;

import com.mojang.authlib.GameProfile;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.GAME, modid = PetProtection.MOD_ID)
public class ServerEvent {

    /**
     * This event is launched when a player interact with an entity.
     * Insert or remove the medal of the pet.
     * @param event Event
     */
    @SubscribeEvent
    public static void onPlayerInteractEntity(PlayerInteractEvent.EntityInteract event) {
        Player player = event.getEntity();
        ItemStack handItem = event.getItemStack();

        // Make sure that only the owner can put or remove a medal
        if (event.getLevel().isClientSide()
            || !(event.getTarget() instanceof TamableAnimal pet)
            || !pet.isTame()
            || !Objects.equals(pet.getOwnerUUID(), player.getUUID())) {
            return;
        }

        ItemStackHandler medalSlot = pet.getData(ModAttachments.MEDAL_ITEM);

        if (handItem.getItem() instanceof MedalItem) {
            if (medalSlot.getStackInSlot(0).isEmpty()) {
                medalSlot.insertItem(0, handItem.copyWithCount(1), false);
                handItem.consume(1, player);
                PacketDistributor.sendToPlayersTrackingEntity(pet, createMedalPayload(pet));
                event.getLevel().playSound(null, pet.getX(), pet.getY(), pet.getZ(), SoundEvents.ARMOR_EQUIP_WOLF.value(), SoundSource.NEUTRAL);
            } else {
                player.displayClientMessage(Component.translatable("message.petprotection.has_medal", pet.getName()), true);
            }
            cancelInteraction(event);
        } else if (handItem.is(Items.SHEARS)) {
            // Vanilla shears remove the wolf armor: the medal is removed only when crouching
            if (pet instanceof Wolf wolf && wolf.isWearingBodyArmor() && !player.isCrouching()) {
                return;
            }
            if (medalSlot.getStackInSlot(0).isEmpty()) {
                player.displayClientMessage(Component.translatable("message.petprotection.has_no_medal", pet.getName()), true);
            } else {
                pet.spawnAtLocation(medalSlot.extractItem(0, 1, false), 1);
                PacketDistributor.sendToPlayersTrackingEntity(pet, createMedalPayload(pet));
                event.getLevel().playSound(null, pet.getX(), pet.getY(), pet.getZ(), SoundEvents.ARMOR_UNEQUIP_WOLF, SoundSource.NEUTRAL);
            }
            cancelInteraction(event);
        }
    }

    /**
     * Cancel damage event if pet has a medal and the medal
     * allow to cancel damages from the hit source.
     * @param event Event
     */
    @SubscribeEvent
    public static void onEntityIncomingDamage(LivingIncomingDamageEvent event) {
        if (event.getEntity().level().isClientSide()
            || !(event.getEntity() instanceof TamableAnimal pet)
            || !pet.isTame()
            || !(event.getSource().getEntity() instanceof Player attacker)
            || !(pet.getData(ModAttachments.MEDAL_ITEM).getStackInSlot(0).getItem() instanceof MedalItem medal)
        ) {
            return;
        }

        boolean isOwner = Objects.equals(attacker.getUUID(), pet.getOwnerUUID());
        boolean isProtected = isOwner ? medal.protectFromOwner() : medal.protectFromTeamPlayer() && pet.getOwner() instanceof Player owner && !attacker.canHarmPlayer(owner);
        if (isProtected) {
            // Wolves and parrots stand up when hurt, before this event is fired: make them sit again
            // Cats don't stand up, and can sit on bed or chest without being ordered to
            if (pet.isInSittingPose() && !(pet instanceof Cat)) {
                pet.setOrderedToSit(true);
            }
            event.setCanceled(true);
        }
    }

    /**
     * Save entity data on a soul medal item and let the pet
     * drop it when he dies
     * Event priority set to lowest in case an other mod prevent entity from dying
     * @param event Event
     */
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onEntityDeath(LivingDeathEvent event) {
        // Ignore if we are on client side
        if (event.getEntity().level().isClientSide()) {
            return;
        }
        if (event.getEntity() instanceof TamableAnimal pet && pet.isTame()) {
            if (!pet.getData(ModAttachments.MEDAL_ITEM).getStackInSlot(0).isEmpty()) {
                ItemStack medalStack = pet.getData(ModAttachments.MEDAL_ITEM).getStackInSlot(0);

                if (medalStack.getItem() instanceof MedalItem medalItem) {
                    SoulMedalItem soulMedalItem = (SoulMedalItem) medalItem.getSoulMedalItem();

                    if (soulMedalItem.allowResurrection()) {
                        ItemStack soulMedalStack = new ItemStack(soulMedalItem);
                        // saveWithoutId save entity data even if the entity is passenger of boat or minecart
                        CompoundTag entityData = pet.saveWithoutId(new CompoundTag());
                        entityData.putString("id", EntityType.getKey(pet.getType()).toString());
                        soulMedalStack.set(ModDataComponent.SOUL_MEDAL_DATA, new SoulMedalDataComponent(
                                pet.hasCustomName() ? Objects.requireNonNull(pet.getCustomName()).getString() : "",
                                EntityType.getKey(pet.getType()).toString(),
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
        if (event.getTarget() instanceof TamableAnimal pet && pet.isTame() && event.getEntity() instanceof ServerPlayer player) {
            PacketDistributor.sendToPlayer(player, createMedalPayload(pet));
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

        // The soul vessel and the soul medal can be placed in any order
        ItemStack soulMedalStack;
        if (itemLeft.is(ModItems.SOUL_VESSEL.get())) {
            soulMedalStack = itemRight;
        } else if (itemRight.is(ModItems.SOUL_VESSEL.get())) {
            soulMedalStack = itemLeft;
        } else {
            return;
        }

        // We make sure that soul medal has data before to create resurrection pearl
        SoulMedalDataComponent soulMedalData = soulMedalStack.get(ModDataComponent.SOUL_MEDAL_DATA);
        if (soulMedalStack.getItem() instanceof SoulMedalItem soulMedal && soulMedal.allowResurrection() && soulMedalData != null) {
            ItemStack resurrectionPearlStack = ModItems.RESURRECTION_PEARL.toStack();
            resurrectionPearlStack.set(ModDataComponent.SOUL_MEDAL_DATA, soulMedalData);

            event.setOutput(resurrectionPearlStack);
            event.setMaterialCost(1);
            event.setCost(2);
        }
    }

    /**
     * The anvil always consume the whole left stack:
     * give back the extar soul vessels if they were placed on the left
     * @param event Event
    */
    @SubscribeEvent
    public static void onAnvilRepair(AnvilRepairEvent event) {
        ItemStack itemLeft = event.getLeft();
        if (!event.getEntity().level().isClientSide()
            && event.getOutput().is(ModItems.RESURRECTION_PEARL.get())
            && itemLeft.is(ModItems.SOUL_VESSEL.get())
            && itemLeft.getCount() > 1
        ) {
            event.getEntity().getInventory().placeItemBackInInventory(itemLeft.copyWithCount(itemLeft.getCount() - 1));
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

    /**
     * Create the payload to sync the pet medal with clients
     * @param pet Pet
     * @return Payload
    */
    private static MedalItemData createMedalPayload(TamableAnimal pet) {
        CompoundTag tag = pet.getData(ModAttachments.MEDAL_ITEM).serializeNBT(pet.registryAccess());
        return new MedalItemData(pet.getId(), tag);
    }

    /**
     * Cancel the vanilla interaction, so the pet does not sit or stand up
     * @param event Event
    */
    private static void cancelInteraction(PlayerInteractEvent.EntityInteract event) {
        event.setCancellationResult(InteractionResult.SUCCESS);
        event.setCanceled(true);
    }
}
