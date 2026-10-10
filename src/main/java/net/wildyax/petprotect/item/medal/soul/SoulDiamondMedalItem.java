package net.wildyax.petprotect.item.medal.soul;

import net.wildyax.petprotect.config.Config;

public class SoulDiamondMedalItem extends SoulMedalItem {

    public SoulDiamondMedalItem(Properties properties) {
        super(properties);
    }

    @Override
    public boolean allowResurrection() {
        return Config.SERVER.diamondMedal.allowResurrection.get();
    }
}
