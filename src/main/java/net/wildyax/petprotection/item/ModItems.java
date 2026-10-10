package net.wildyax.petprotection.item;

import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.wildyax.petprotection.PetProtection;
import net.wildyax.petprotection.item.medal.DiamondMedalItem;
import net.wildyax.petprotection.item.medal.GoldMedalItem;
import net.wildyax.petprotection.item.medal.IronMedalItem;
import net.wildyax.petprotection.item.medal.soul.SoulDiamondMedalItem;
import net.wildyax.petprotection.item.medal.soul.SoulGoldMedalItem;
import net.wildyax.petprotection.item.medal.soul.SoulIronMedalItem;

public class ModItems {

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(PetProtection.MOD_ID);

    public static final DeferredItem<Item> IRON_MEDAL = ITEMS.register("iron_medal",
            () -> new IronMedalItem(new Item.Properties())
    );
    public static final DeferredItem<Item> SOUL_IRON_MEDAL = ITEMS.register("soul_iron_medal",
            () -> new SoulIronMedalItem(new Item.Properties()
                    .stacksTo(1)
                    .fireResistant()
            )
    );

    public static final DeferredItem<Item> GOLD_MEDAL = ITEMS.register("gold_medal",
            () -> new GoldMedalItem(new Item.Properties())
    );
    public static final DeferredItem<Item> SOUL_GOLD_MEDAL = ITEMS.register("soul_gold_medal",
            () -> new SoulGoldMedalItem(new Item.Properties()
                    .stacksTo(1)
                    .fireResistant()
            )
    );

    public static final DeferredItem<Item> DIAMOND_MEDAL = ITEMS.register("diamond_medal",
            () -> new DiamondMedalItem(new Item.Properties())
    );
    public static final DeferredItem<Item> SOUL_DIAMOND_MEDAL = ITEMS.register("soul_diamond_medal",
            () -> new SoulDiamondMedalItem(new Item.Properties()
                    .stacksTo(1)
                    .fireResistant()
            )
    );

    public static final DeferredItem<Item> SOUL_VESSEL = ITEMS.register("soul_vessel",
            () -> new Item(new Item.Properties())
    );
    public static final DeferredItem<Item> RESURRECTION_PEARL = ITEMS.register("resurrection_pearl",
            () -> new ResurrectionPearlItem(new Item.Properties()
                    .stacksTo(1)
            )
    );

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
