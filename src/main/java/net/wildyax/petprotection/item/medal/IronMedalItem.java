package net.wildyax.petprotection.item.medal;


import net.minecraft.world.item.Item;
import net.wildyax.petprotection.config.Config;
import net.wildyax.petprotection.item.ModItems;

public class IronMedalItem extends MedalItem {

    public IronMedalItem(Properties properties) {
        super(properties);
    }

    @Override
    public boolean protectFromOwner() {
        return Config.SERVER.ironMedal.protectFromOwner.get();
    }

    @Override
    public boolean protectFromTeamPlayer() {
        return Config.SERVER.ironMedal.protectFromTeam.get();
    }

    @Override
    public boolean renderOnPet() {
        return Config.CLIENT.ironMedal.renderOnPet.get();
    }

    @Override
    public Item getSoulMedalItem() {
        return ModItems.SOUL_IRON_MEDAL.get();
    }
}
