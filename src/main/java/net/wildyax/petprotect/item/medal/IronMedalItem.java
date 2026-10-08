package net.wildyax.petprotect.item.medal;


import net.minecraft.world.item.Item;
import net.wildyax.petprotect.config.Config;
import net.wildyax.petprotect.item.ModItems;

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

    @Override
    public Item getSoulMedalItem() {
        return ModItems.SOUL_IRON_MEDAL.get();
    }
}
