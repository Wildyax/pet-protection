package net.wildyax.petprotection.item.medal.soul;

import net.wildyax.petprotection.config.Config;

public class SoulGoldMedalItem extends SoulMedalItem {

    public SoulGoldMedalItem(Properties properties) {
        super(properties);
    }

    @Override
    public boolean allowResurrection() {
        return Config.SERVER.goldMedal.allowResurrection.get();
    }
}
