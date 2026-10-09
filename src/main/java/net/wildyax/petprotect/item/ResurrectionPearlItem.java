package net.wildyax.petprotect.item;

import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.wildyax.petprotect.component.ModDataComponent;
import net.wildyax.petprotect.component.SoulMedalDataComponent;
import net.wildyax.petprotect.entities.ResurrectionPearl;

import java.util.List;

public class ResurrectionPearlItem extends Item implements ProjectileItem {

    public ResurrectionPearlItem(Properties properties) {
        super(properties);
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return true;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack itemstack = player.getItemInHand(usedHand);
        level.playSound((Player)null, player.getX(), player.getY(), player.getZ(), SoundEvents.SNOWBALL_THROW, SoundSource.NEUTRAL, 0.5F, 0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F));
        if (!level.isClientSide) {
            ResurrectionPearl resurrectionPearl = new ResurrectionPearl(player, level);
            resurrectionPearl.setItem(itemstack);
            resurrectionPearl.shootFromRotation(player, player.getXRot(), player.getYRot(), -20F, 0.5F, 1.0F);
            level.addFreshEntity(resurrectionPearl);
        }

        itemstack.shrink(1);
        return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
    }

    @Override
    public Projectile asProjectile(Level level, Position position, ItemStack itemStack, Direction direction) {
        ResurrectionPearl resurrectionPearl = new ResurrectionPearl(position.x(), position.y(), position.z(), level);
        resurrectionPearl.setItem(itemStack);
        return resurrectionPearl;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        SoulMedalDataComponent soulMedalData = stack.get(ModDataComponent.SOUL_MEDAL_DATA);
        if (soulMedalData == null) {
            tooltipComponents.add(Component.translatable("tooltip.petprotect.no_pet_assigned"));
        } else {
            String petName = soulMedalData.entityTagName().isEmpty() ? "§f"+soulMedalData.entityType()+"§r" : "§9"+soulMedalData.entityTagName()+"§r";
            tooltipComponents.add(Component.translatable("tooltip.petprotect.resurrection_pearl.pet_name", petName));
        }
    }
}
