package net.wildyax.petprotect.item.medal.soul;

import net.wildyax.petprotect.config.Config;

public class SoulIronMedalItem extends SoulMedalItem {

    public SoulIronMedalItem(Properties properties) {
        super(properties);
    }

    @Override
    public boolean allowResurrection() {
        return Config.SERVER.ironMedal.allowResurrection.get();
    }
}
