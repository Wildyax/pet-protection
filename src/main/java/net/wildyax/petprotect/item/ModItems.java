package net.wildyax.petprotect.item;

import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.wildyax.petprotect.PetProtect;
import net.wildyax.petprotect.item.medal.DiamondMedalItem;
import net.wildyax.petprotect.item.medal.GoldMedalItem;
import net.wildyax.petprotect.item.medal.IronMedalItem;

public class ModItems {

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(PetProtect.MOD_ID);

    public static final DeferredItem<Item> IRON_MEDAL = ITEMS.register("iron_medal",
            () -> new IronMedalItem(new Item.Properties()
                    .stacksTo(1)
            )
    );

    public static final DeferredItem<Item> GOLD_MEDAL = ITEMS.register("gold_medal",
            () -> new GoldMedalItem(new Item.Properties()
                    .stacksTo(1)
            )
    );

    public static final DeferredItem<Item> DIAMOND_MEDAL = ITEMS.register("diamond_medal",
            () -> new DiamondMedalItem(new Item.Properties()
                    .stacksTo(1)
            )
    );

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
