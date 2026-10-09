package net.wildyax.petprotect.item.medal.soul;

import net.minecraft.world.item.Item;
import net.wildyax.petprotect.config.Config;
import net.wildyax.petprotect.item.ModItems;

public class SoulDiamondMedalItem extends SoulMedalItem {

    public SoulDiamondMedalItem(Properties properties) {
        super(properties);
    }

    @Override
    public boolean allowResurrection() {
        return Config.SERVER.diamondMedal.allowResurrection.get();
    }

    @Override
    public Item getMedalItem() {
        return ModItems.DIAMOND_MEDAL.get();
    }
}
