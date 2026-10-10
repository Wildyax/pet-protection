package net.wildyax.petprotection.item.medal.soul;

import net.wildyax.petprotection.config.Config;

public class SoulIronMedalItem extends SoulMedalItem {

    public SoulIronMedalItem(Properties properties) {
        super(properties);
    }

    @Override
    public boolean allowResurrection() {
        return Config.SERVER.ironMedal.allowResurrection.get();
    }
}
