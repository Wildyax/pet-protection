package net.wildyax.petprotection.datagen;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.wildyax.petprotection.PetProtection;

@EventBusSubscriber(modid = PetProtection.MOD_ID, value = Dist.CLIENT)
public class DataGenHandler {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent.Client event) {
        event.createProvider(ModModelProvider::new);
        event.createProvider(ModRecipeProvider.Runner::new);
    }
}
