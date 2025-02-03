package net.wildyax.petprotect.event;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.resources.PlayerSkin;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.MainThreadPayloadHandler;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import net.wildyax.petprotect.PetProtect;
import net.wildyax.petprotect.network.ClientPayloadHandler;
import net.wildyax.petprotect.network.MedalItemData;
import net.wildyax.petprotect.render.cat.ModCatRenderer;
import net.wildyax.petprotect.render.parrot.ModParrotRenderer;
import net.wildyax.petprotect.render.player.ParrotOnPlayerShoulderLayer;
import net.wildyax.petprotect.render.wolf.ModWolfRenderer;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD, modid = PetProtect.MOD_ID, value = Dist.CLIENT)
public class ClientEvent {

    /**
     * Register entities renderer to display medal on pet
     * @param event Event
     */
    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(EntityType.WOLF, ModWolfRenderer::new);
        event.registerEntityRenderer(EntityType.CAT, ModCatRenderer::new);
        event.registerEntityRenderer(EntityType.PARROT, ModParrotRenderer::new);

    }

    /**
     * For the player renderer I need to do differently than with a
     * classic entity
     * @param event Event
     */
    @SubscribeEvent
    public static void onRegisterLayers(EntityRenderersEvent.AddLayers event) {
        for (PlayerSkin.Model skin : event.getSkins()) {
            LivingEntityRenderer<Player, PlayerModel<Player>> renderer = event.getSkin(skin);
            if (renderer != null) {
                renderer.addLayer(new ParrotOnPlayerShoulderLayer(renderer, Minecraft.getInstance().getItemRenderer()));
            }
        }
    }

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
                new MainThreadPayloadHandler<>(
                        ClientPayloadHandler::setEntityMedalItemData
                )
        );
    }
}
