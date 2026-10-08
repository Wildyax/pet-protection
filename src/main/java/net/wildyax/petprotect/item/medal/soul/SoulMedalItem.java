package net.wildyax.petprotect.item.medal.soul;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.wildyax.petprotect.component.ModDataComponent;
import net.wildyax.petprotect.component.SoulMedalDataComponent;

import java.util.List;

public abstract class SoulMedalItem extends Item {

    public SoulMedalItem(Properties properties) { super(properties); }

    public abstract boolean allowResurrection();

    public abstract Item getMedalItem();

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        SoulMedalDataComponent soulMedalData = stack.get(ModDataComponent.SOUL_MEDAL_DATA);
        if (soulMedalData == null) {
            tooltipComponents.add(Component.translatable("tooltip.petprotect.no_pet_assigned"));
        } else {
            String petName = soulMedalData.entityTagName().isEmpty() ? "§f"+soulMedalData.entityType()+"§r" : "§9"+soulMedalData.entityTagName()+"§r";
            tooltipComponents.add(Component.translatable("tooltip.petprotect.soul_medal.pet_name", petName));
            tooltipComponents.add(Component.translatable("tooltip.petprotect.soul_medal.pet_owner", "§e"+soulMedalData.entityOwner()+"§r"));
        }
    }
}
