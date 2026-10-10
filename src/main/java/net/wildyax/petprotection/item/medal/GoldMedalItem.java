package net.wildyax.petprotection.item.medal;


import net.minecraft.world.item.Item;
import net.wildyax.petprotection.config.Config;
import net.wildyax.petprotection.item.ModItems;

public class GoldMedalItem extends MedalItem {

    public GoldMedalItem(Properties properties) {
        super(properties);
    }

    @Override
    public boolean protectFromOwner() {
        return Config.SERVER.goldMedal.protectFromOwner.get();
    }

    @Override
    public boolean protectFromTeamPlayer() {
        return Config.SERVER.goldMedal.protectFromTeam.get();
    }

    @Override
    public boolean renderOnPet() {
        return Config.CLIENT.goldMedal.renderOnPet.get();
    }

    @Override
    public Item getSoulMedalItem() {
        return ModItems.SOUL_GOLD_MEDAL.get();
    }
}
