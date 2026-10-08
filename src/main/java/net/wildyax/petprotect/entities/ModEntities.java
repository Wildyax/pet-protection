package net.wildyax.petprotect.entities;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.wildyax.petprotect.PetProtect;

import java.util.function.Supplier;


public class ModEntities {

    public static final DeferredRegister<EntityType<?>> ENTITIES =
            DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, PetProtect.MOD_ID);

    public static final Supplier<EntityType<ResurrectionPearl>> RESURRECTION_PEARL =
            ENTITIES.register(
                    "resurrection_pearl",
                    () -> EntityType.Builder.<ResurrectionPearl>of(
                            ResurrectionPearl::new,
                            MobCategory.MISC
                    )
                            .sized(0.25f, 0.25f)
                            .build("resurrection_pearl")
            );

    public static void register(IEventBus eventBus) {
        ENTITIES.register(eventBus);
    }
}
