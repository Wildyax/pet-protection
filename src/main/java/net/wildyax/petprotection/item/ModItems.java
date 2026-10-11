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

    public static final DeferredItem<Item> IRON_MEDAL = ITEMS.registerItem("iron_medal", IronMedalItem::new );
    public static final DeferredItem<Item> SOUL_IRON_MEDAL = ITEMS.registerItem("soul_iron_medal", SoulIronMedalItem::new,
        props -> props
            .stacksTo(1)
            .fireResistant()
    );

    public static final DeferredItem<Item> GOLD_MEDAL = ITEMS.registerItem("gold_medal", GoldMedalItem::new );
    public static final DeferredItem<Item> SOUL_GOLD_MEDAL = ITEMS.registerItem("soul_gold_medal", SoulGoldMedalItem::new,
        props -> props
            .stacksTo(1)
            .fireResistant()
    );

    public static final DeferredItem<Item> DIAMOND_MEDAL = ITEMS.registerItem("diamond_medal", DiamondMedalItem::new );
    public static final DeferredItem<Item> SOUL_DIAMOND_MEDAL = ITEMS.registerItem("soul_diamond_medal", SoulDiamondMedalItem::new,
        props -> props
            .stacksTo(1)
            .fireResistant()
    );

    public static final DeferredItem<Item> SOUL_VESSEL = ITEMS.registerSimpleItem("soul_vessel");
    public static final DeferredItem<Item> RESURRECTION_PEARL = ITEMS.registerItem("resurrection_pearl", ResurrectionPearlItem::new,
        props -> props
            .stacksTo(1)
    );

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
