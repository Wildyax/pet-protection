package net.wildyax.petprotect.item.medal;


import net.minecraft.world.item.Item;
import net.wildyax.petprotect.config.Config;
import net.wildyax.petprotect.item.ModItems;

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

    @Override
    public Item getSoulMedalItem() {
        return ModItems.SOUL_DIAMOND_MEDAL.get();
    }
}
