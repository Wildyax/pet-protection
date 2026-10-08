package net.wildyax.petprotect.entities;

import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.neoforged.neoforge.network.PacketDistributor;
import net.wildyax.petprotect.component.ModDataComponent;
import net.wildyax.petprotect.component.SoulMedalDataComponent;
import net.wildyax.petprotect.item.ModItems;
import net.wildyax.petprotect.network.MedalItemData;
import net.wildyax.petprotect.utils.ModAttachments;

import java.util.Optional;

public class ResurrectionPearl extends ThrowableItemProjectile {

    public ResurrectionPearl(EntityType<? extends ResurrectionPearl> entityType, Level level) {
        super(entityType, level);
    }

    public ResurrectionPearl(double x, double y, double z, Level level) {
        super(ModEntities.RESURRECTION_PEARL.get(), x, y, z, level);
    }

    public ResurrectionPearl(LivingEntity shooter, Level level) {
        super(ModEntities.RESURRECTION_PEARL.get(), shooter, level);
    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.RESURRECTION_PEARL.get();
    }

    private ParticleOptions getParticle() {
        ItemStack itemstack = this.getItem();
        return (!itemstack.isEmpty() && !itemstack.is(this.getDefaultItem()) ? new ItemParticleOption(ParticleTypes.ITEM, itemstack) : ParticleTypes.SOUL);
    }

    public void handleEntityEvent(byte id) {
        if (id == 3) {
            ParticleOptions particleoptions = this.getParticle();
            this.level().addParticle(particleoptions, this.getX() + 1, this.getY() + 0.5f, this.getZ(), (double)0.0F, (double)0.0F, (double)0.0F);
            this.level().addParticle(particleoptions, this.getX() - 1, this.getY() + 0.5f, this.getZ(), (double)0.0F, (double)0.0F, (double)0.0F);
            this.level().addParticle(particleoptions, this.getX(), this.getY() + 0.5f, this.getZ() + 1, (double)0.0F, (double)0.0F, (double)0.0F);
            this.level().addParticle(particleoptions, this.getX(), this.getY() + 0.5f, this.getZ() - 1, (double)0.0F, (double)0.0F, (double)0.0F);
        }

    }

    protected void onHit(HitResult result) {
        super.onHit(result);

        Level level = this.level();
        if (!level.isClientSide) {
            level.broadcastEntityEvent(this, (byte)3);
            ItemStack itemStack = this.getItem();
            SoulMedalDataComponent soulMedalData = itemStack.get(ModDataComponent.SOUL_MEDAL_DATA);

            if (soulMedalData != null) {
                Optional<EntityType<?>> entityType = EntityType.byString(soulMedalData.entityData().getString("id"));
                entityType.ifPresent(type -> {
                    Entity entity = type.create(level);

                    if (entity instanceof TamableAnimal pet) {
                        pet.load(soulMedalData.entityData());
                        pet.setHealth(pet.getMaxHealth());
                        pet.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), 0.0F);
                        pet.setBodyArmorItem(ItemStack.EMPTY);

                        pet.getData(ModAttachments.MEDAL_ITEM).extractItem(0,1,false);
                        CompoundTag medalTag = pet.getData(ModAttachments.MEDAL_ITEM).serializeNBT(level.registryAccess());
                        PacketDistributor.sendToAllPlayers(new MedalItemData(pet.getId(), medalTag));

                        level.playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.TOTEM_USE, SoundSource.NEUTRAL);
                        level.addFreshEntity(pet);
                    }
                });
            }

            this.discard();
        }

    }
}
