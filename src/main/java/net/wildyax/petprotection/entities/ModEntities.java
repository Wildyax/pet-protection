package net.wildyax.petprotection.entities;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.wildyax.petprotection.PetProtection;

import java.util.function.Supplier;


public class ModEntities {

    public static final DeferredRegister.Entities ENTITIES =
        DeferredRegister.createEntities(PetProtection.MOD_ID);

    public static final Supplier<EntityType<ResurrectionPearl>> RESURRECTION_PEARL =
        ENTITIES.registerEntityType(
            "resurrection_pearl",
            ResurrectionPearl::new,
            MobCategory.MISC,
            builder -> builder
                .sized(0.25f, 0.25f)
                .clientTrackingRange(4)
                .updateInterval(10)
        );

    public static void register(IEventBus eventBus) {
        ENTITIES.register(eventBus);
    }
}
