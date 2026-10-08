package net.wildyax.petprotect.component;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.wildyax.petprotect.PetProtect;

public class ModDataComponent {

    public static final DeferredRegister.DataComponents REGISTRAR =
            DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, PetProtect.MOD_ID);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<SoulMedalDataComponent>> SOUL_MEDAL_DATA =
            REGISTRAR.registerComponentType(
                    "soul_medal_data",
                    builder -> builder
                            .persistent(SoulMedalDataComponent.MEDAL_CODEC)
                            .networkSynchronized(SoulMedalDataComponent.MEDAL_STREAM_CODEC)
            );

    public static void register(IEventBus eventBus) {
        REGISTRAR.register(eventBus);
    }

}
