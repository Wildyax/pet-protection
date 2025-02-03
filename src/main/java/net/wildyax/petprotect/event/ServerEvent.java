package net.wildyax.petprotect.event;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import net.wildyax.petprotect.PetProtect;
import net.wildyax.petprotect.item.medal.MedalItem;
import net.wildyax.petprotect.network.MedalItemData;
import net.wildyax.petprotect.utils.ModAttachments;

import java.util.Objects;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.GAME, modid = PetProtect.MOD_ID)
public class ServerEvent {

    /**
     * This event is launched when a player interact with an entity.
     * I use it to put or remove the medal of the pet.
     * @param event Event
     */
    @SubscribeEvent
    public static void onPlayerInteractEntity(PlayerInteractEvent.EntityInteract event) {

        var player = event.getEntity();
        var entity = event.getTarget();
        var handItem = player.getMainHandItem();

        if (handItem.getItem() instanceof MedalItem medal && !event.getLevel().isClientSide()) {
            if (entity instanceof TamableAnimal pet && pet.isTame() && Objects.equals(pet.getOwnerUUID(), player.getUUID())) {
                pet.setOrderedToSit(!pet.isOrderedToSit());
                if (pet.getData(ModAttachments.MEDAL_ITEM).getStackInSlot(0) == ItemStack.EMPTY) {
                     pet.getData(ModAttachments.MEDAL_ITEM).insertItem(0, new ItemStack(medal, 1), false);

                     CompoundTag tag = pet.getData(ModAttachments.MEDAL_ITEM).serializeNBT(event.getLevel().registryAccess());
                     PacketDistributor.sendToAllPlayers(new MedalItemData(pet.getId(), tag));
                     player.getMainHandItem().shrink(1);

                } else {
                    player.displayClientMessage(Component.translatable("message.petprotect.has_medal", pet.getName().getString()), true);
                }
            }
        } else if (handItem.getItem() == Items.SHEARS && !event.getLevel().isClientSide()) {
            if (entity instanceof TamableAnimal pet && pet.isTame() && Objects.equals(pet.getOwnerUUID(), player.getUUID())) {
                pet.setOrderedToSit(!pet.isOrderedToSit());
                if (pet.getData(ModAttachments.MEDAL_ITEM).getStackInSlot(0) == ItemStack.EMPTY) {
                    player.displayClientMessage(Component.translatable("message.petprotect.has_no_medal", pet.getName().getString()), true);
                } else {
                    ItemStack itemStack = pet.getData(ModAttachments.MEDAL_ITEM).extractItem(0,1,false);
                    pet.spawnAtLocation(itemStack.getItem(),1);

                    CompoundTag tag = pet.getData(ModAttachments.MEDAL_ITEM).serializeNBT(event.getLevel().registryAccess());
                    PacketDistributor.sendToAllPlayers(new MedalItemData(pet.getId(), tag));
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
     * Ensure that server entity data is synced with client when player tracking entity,
     * it allows to keep the medal on dog texture when game restart or player leave chunk
     * @param event Event
     */
    @SubscribeEvent
    public static void onStartTracking(PlayerEvent.StartTracking event) {
        if (event.getTarget() instanceof TamableAnimal pet && pet.isTame()) {
            CompoundTag tag = pet.getData(ModAttachments.MEDAL_ITEM).serializeNBT(pet.registryAccess());
            PacketDistributor.sendToAllPlayers(new MedalItemData(pet.getId(), tag));
        }
    }
}
