package net.wildyax.petprotect.item.medal;


import net.wildyax.petprotect.config.Config;

public class GoldMedalItem extends MedalItem {

    public GoldMedalItem(Properties properties) {
        super(properties);
    }

    @Override
    public boolean protectFromOwner() {
        return Config.COMMON.goldMedalProtectFromOwner.get();
    }

    @Override
    public boolean protectFromTeamPlayer() {
        return Config.COMMON.goldMedalProtectFromTeam.get();
    }

    @Override
    public boolean renderOnPet() {
        return Config.COMMON.goldMedalRenderOnPet.get();
    }
}
