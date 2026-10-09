package net.wildyax.petprotect.item.medal.soul;

import net.minecraft.world.item.Item;
import net.wildyax.petprotect.config.Config;
import net.wildyax.petprotect.item.ModItems;

public class SoulIronMedalItem extends SoulMedalItem {

    public SoulIronMedalItem(Properties properties) {
        super(properties);
    }

    @Override
    public boolean allowResurrection() {
        return Config.SERVER.ironMedal.allowResurrection.get();
    }

    @Override
    public Item getMedalItem() {
        return ModItems.IRON_MEDAL.get();
    }
}
