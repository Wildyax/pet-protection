package net.wildyax.petprotect.render.wolf;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.WolfRenderer;

public class ModWolfRenderer extends WolfRenderer {

    public ModWolfRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.addLayer(new WolfMedalRenderLayer(this, context.getItemRenderer()));
    }
}
