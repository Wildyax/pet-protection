package net.wildyax.petprotect.item.medal;


import net.wildyax.petprotect.config.Config;

public class DiamondMedalItem extends MedalItem{

    public DiamondMedalItem(Properties properties) {
        super(properties);
    }

    @Override
    public boolean protectFromOwner() {
        return Config.COMMON.diamondMedalProtectFromOwner.get();
    }

    @Override
    public boolean protectFromTeamPlayer() {
        return Config.COMMON.diamondMedalProtectFromTeam.get();
    }

    @Override
    public boolean renderOnPet() {
        return Config.COMMON.diamondMedalRenderOnPet.get();
    }
}
