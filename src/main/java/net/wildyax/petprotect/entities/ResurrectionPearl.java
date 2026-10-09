package net.wildyax.petprotect.entities;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
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
import net.wildyax.petprotect.component.ModDataComponent;
import net.wildyax.petprotect.component.SoulMedalDataComponent;
import net.wildyax.petprotect.item.ModItems;
import net.wildyax.petprotect.utils.ModAttachments;

import java.util.Optional;
import java.util.UUID;

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

    @Override
    public void handleEntityEvent(byte id) {
        if (id == 3) {
            ParticleOptions particleoptions = ParticleTypes.SOUL;
            this.level().addParticle(particleoptions, this.getX() + 1, this.getY() + 0.5f, this.getZ(), (double)0.0F, (double)0.0F, (double)0.0F);
            this.level().addParticle(particleoptions, this.getX() - 1, this.getY() + 0.5f, this.getZ(), (double)0.0F, (double)0.0F, (double)0.0F);
            this.level().addParticle(particleoptions, this.getX(), this.getY() + 0.5f, this.getZ() + 1, (double)0.0F, (double)0.0F, (double)0.0F);
            this.level().addParticle(particleoptions, this.getX(), this.getY() + 0.5f, this.getZ() - 1, (double)0.0F, (double)0.0F, (double)0.0F);
        }

    }

    @Override
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

                        // Clean the state the pet had when it died (poison, wither, fire...)
                        pet.removeAllEffects();
                        pet.clearFire();
                        pet.resetFallDistance();

                        // If another entity already uses this UUID, the pet would not spawn: give it a new one
                        if (level instanceof ServerLevel serverLevel && serverLevel.getEntity(pet.getUUID()) != null) {
                            pet.setUUID(UUID.randomUUID());
                        }

                        level.playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.TOTEM_USE, SoundSource.NEUTRAL);
                        level.addFreshEntity(pet);
                    }
                });
            }

            this.discard();
        }

    }
}
