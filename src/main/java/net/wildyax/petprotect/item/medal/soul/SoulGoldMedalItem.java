package net.wildyax.petprotect.item.medal.soul;

import net.minecraft.world.item.Item;
import net.wildyax.petprotect.config.Config;
import net.wildyax.petprotect.item.ModItems;

public class SoulGoldMedalItem extends SoulMedalItem {

    public SoulGoldMedalItem(Properties properties) {
        super(properties);
    }

    @Override
    public boolean allowResurrection() {
        return Config.COMMON.goldMedalAllowResurrection.get();
    }

    @Override
    public Item getMedalItem() {
        return ModItems.GOLD_MEDAL.get();
    }
}
