package net.wildyax.petprotect.utils;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.wildyax.petprotect.PetProtect;
import net.wildyax.petprotect.item.ModItems;

import java.util.function.Supplier;

public class ModCreativeTabs {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, PetProtect.MOD_ID);

    public static final Supplier<CreativeModeTab> PET_PROTECT_TAB =
            CREATIVE_MODE_TABS.register("pet_protect_tab", () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(ModItems.DIAMOND_MEDAL.get()))
                    .title(Component.translatable("creative_tab.petprotect.pet_protect"))
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
