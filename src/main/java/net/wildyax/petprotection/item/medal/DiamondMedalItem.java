package net.wildyax.petprotection.item.medal;


import net.minecraft.world.item.Item;
import net.wildyax.petprotection.config.Config;
import net.wildyax.petprotection.item.ModItems;

public class DiamondMedalItem extends MedalItem{

    public DiamondMedalItem(Properties properties) {
        super(properties);
    }

    @Override
    public boolean protectFromOwner() {
        return Config.SERVER.diamondMedal.protectFromOwner.get();
    }

    @Override
    public boolean protectFromTeamPlayer() {
        return Config.SERVER.diamondMedal.protectFromTeam.get();
    }

    @Override
    public boolean renderOnPet() {
        return Config.CLIENT.diamondMedal.renderOnPet.get();
    }

    @Override
    public Item getSoulMedalItem() {
        return ModItems.SOUL_DIAMOND_MEDAL.get();
    }
}
