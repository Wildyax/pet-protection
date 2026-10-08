package net.wildyax.petprotect.network;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import net.wildyax.petprotect.PetProtect;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD, modid = PetProtect.MOD_ID)
public class ModNetwork {

    /**
     * Register network PayLoad Handler to communicate
     * between server and client
     * @param event Event
     */
    @SubscribeEvent
    public static void register(final RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar("1");
        registrar.playToClient(
            MedalItemData.TYPE,
            MedalItemData.STREAM_CODEC,
            ClientPayloadHandler::setEntityMedalItemData
        );
    }
}
