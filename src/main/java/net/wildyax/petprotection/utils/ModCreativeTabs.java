package net.wildyax.petprotection.utils;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.wildyax.petprotection.PetProtection;
import net.wildyax.petprotection.item.ModItems;

import java.util.function.Supplier;

public class ModCreativeTabs {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, PetProtection.MOD_ID);

    public static final Supplier<CreativeModeTab> PET_PROTECT_TAB =
            CREATIVE_MODE_TABS.register("pet_protection_tab", () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(ModItems.DIAMOND_MEDAL.get()))
                    .title(Component.translatable("creative_tab.petprotection.pet_protection"))
                    .displayItems(((itemDisplayParameters, output) -> {

                        output.accept(ModItems.IRON_MEDAL);
                        output.accept(ModItems.GOLD_MEDAL);
                        output.accept(ModItems.DIAMOND_MEDAL);
                        output.accept(ModItems.SOUL_VESSEL);

                    }))
                    .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
