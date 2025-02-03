package net.wildyax.petprotect.item.medal;


import net.wildyax.petprotect.config.Config;

public class IronMedalItem extends MedalItem {

    public IronMedalItem(Properties properties) {
        super(properties);
    }

    @Override
    public boolean protectFromOwner() {
        return Config.COMMON.ironMedalProtectFromOwner.get();
    }

    @Override
    public boolean protectFromTeamPlayer() {
        return Config.COMMON.ironMedalProtectFromTeam.get();
    }

    @Override
    public boolean renderOnPet() {
        return Config.COMMON.ironMedalRenderOnPet.get();
    }
}
