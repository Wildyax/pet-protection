package net.wildyax.petprotect.item.medal.soul;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.wildyax.petprotect.component.ModDataComponent;
import net.wildyax.petprotect.component.SoulMedalDataComponent;

import java.util.List;

public abstract class SoulMedalItem extends Item {

    public SoulMedalItem(Properties properties) { super(properties); }

    public abstract boolean allowResurrection();

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        SoulMedalDataComponent soulMedalData = stack.get(ModDataComponent.SOUL_MEDAL_DATA);
        if (soulMedalData == null) {
            tooltipComponents.add(Component.translatable("tooltip.petprotect.no_pet_assigned"));
        } else {
            Component petName = soulMedalData.entityTagName().isEmpty()
                ? EntityType.byString(soulMedalData.entityType()).map(EntityType::getDescription).orElse(Component.literal(soulMedalData.entityType()))
                : Component.literal(soulMedalData.entityTagName()).withStyle(ChatFormatting.BLUE);
            Component petOwner = Component.literal(soulMedalData.entityOwner()).withStyle(ChatFormatting.YELLOW);

            tooltipComponents.add(Component.translatable("tooltip.petprotect.soul_medal.pet_name", petName).withStyle(ChatFormatting.GRAY));
            tooltipComponents.add(Component.translatable("tooltip.petprotect.soul_medal.pet_owner", petOwner).withStyle(ChatFormatting.GRAY));
        }
    }
}
