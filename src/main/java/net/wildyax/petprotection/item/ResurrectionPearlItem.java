package net.wildyax.petprotection.item;

import net.minecraft.ChatFormatting;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.Level;
import net.wildyax.petprotection.component.ModDataComponent;
import net.wildyax.petprotection.component.SoulMedalDataComponent;
import net.wildyax.petprotection.entities.ResurrectionPearl;

import java.util.function.Consumer;

public class ResurrectionPearlItem extends Item implements ProjectileItem {

    public ResurrectionPearlItem(Properties properties) {
        super(properties);
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return true;
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand usedHand) {
        ItemStack itemStack = player.getItemInHand(usedHand);
        level.playSound((Player)null, player.getX(), player.getY(), player.getZ(), SoundEvents.SNOWBALL_THROW, SoundSource.NEUTRAL, 0.5F, 0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F));
        if (level instanceof ServerLevel serverLevel) {
            Projectile.spawnProjectileFromRotation(ResurrectionPearl::new, serverLevel, itemStack, player, -20F, 0.5F, 1.0F);
        }

        itemStack.shrink(1);
        return InteractionResult.SUCCESS;
    }

    @Override
    public Projectile asProjectile(Level level, Position position, ItemStack itemStack, Direction direction) {
        return new ResurrectionPearl(level, position.x(), position.y(), position.z(), itemStack);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay display, Consumer<Component> tooltipAdder, TooltipFlag flag) {
        SoulMedalDataComponent soulMedalData = stack.get(ModDataComponent.SOUL_MEDAL_DATA);
        if (soulMedalData == null) {
            tooltipAdder.accept(Component.translatable("tooltip.petprotection.no_pet_assigned"));
        } else {
            Component petName = soulMedalData.entityTagName().isEmpty()
                ? EntityType.byString(soulMedalData.entityType()).map(EntityType::getDescription).orElse(Component.literal(soulMedalData.entityType()))
                : Component.literal(soulMedalData.entityTagName()).withStyle(ChatFormatting.BLUE);

            tooltipAdder.accept(Component.translatable("tooltip.petprotection.resurrection_pearl.pet_name", petName).withStyle(ChatFormatting.GRAY));
        }
    }
}
