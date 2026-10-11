package net.wildyax.petprotection.event;

import net.minecraft.client.model.player.PlayerModel;
import net.minecraft.client.renderer.entity.CatRenderer;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.ParrotRenderer;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.client.renderer.entity.WolfRenderer;
import net.minecraft.world.entity.player.PlayerSkin;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.wildyax.petprotection.PetProtection;
import net.wildyax.petprotection.entities.ModEntities;
import net.wildyax.petprotection.render.CatMedalRenderLayer;
import net.wildyax.petprotection.render.ParrotMedalRenderLayer;
import net.wildyax.petprotection.render.ParrotOnPlayerShoulderLayer;
import net.wildyax.petprotection.render.WolfMedalRenderLayer;

@EventBusSubscriber(modid = PetProtection.MOD_ID, value = Dist.CLIENT)
public class ClientEvent {

    /**
     * Register the resurrection pearl as a thrown item
     * @param event Event
     */
    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntities.RESURRECTION_PEARL.get(), ThrownItemRenderer::new);
    }

    /**
     * Add the medal layers to the vanilla renderers (pets and players)
     * without replacing them, to stay compatible with other mods
     * classic entity
     * @param event Event
     */
    @SubscribeEvent
    public static void onRegisterLayers(EntityRenderersEvent.AddLayers event) {
        ItemRenderer itemRenderer = event.getContext().getItemRenderer();

        if (event.getRenderer(EntityType.WOLF) instanceof WolfRenderer wolfRenderer) {
            wolfRenderer.addLayer(new WolfMedalRenderLayer(wolfRenderer, itemRenderer));
        }
        if (event.getRenderer(EntityType.CAT) instanceof CatRenderer catRenderer) {
            catRenderer.addLayer(new CatMedalRenderLayer(catRenderer, itemRenderer));
        }
        if (event.getRenderer(EntityType.PARROT) instanceof ParrotRenderer parrotRenderer) {
            parrotRenderer.addLayer(new ParrotMedalRenderLayer(parrotRenderer, itemRenderer));
        }

        for (PlayerSkin.Model skin : event.getSkins()) {
            LivingEntityRenderer<Player, PlayerModel<Player>> renderer = event.getSkin(skin);
            if (renderer != null) {
                renderer.addLayer(new ParrotOnPlayerShoulderLayer(renderer, itemRenderer));
            }
        }
    }
}
