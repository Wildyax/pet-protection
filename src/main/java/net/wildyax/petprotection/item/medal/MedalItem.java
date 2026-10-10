package net.wildyax.petprotection.item.medal;

import net.minecraft.world.item.Item;

public abstract class MedalItem extends Item {

    public MedalItem(Properties properties) {
        super(properties);
    }

    public abstract Item getSoulMedalItem();

    public abstract boolean protectFromOwner();

    public abstract boolean protectFromTeamPlayer();

    public abstract boolean renderOnPet();
}
