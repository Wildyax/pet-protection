package net.wildyax.petprotect.item.medal;


import net.minecraft.world.item.Item;
import net.wildyax.petprotect.config.Config;
import net.wildyax.petprotect.item.ModItems;

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

    @Override
    public Item getSoulMedalItem() {
        return ModItems.SOUL_GOLD_MEDAL.get();
    }
}
