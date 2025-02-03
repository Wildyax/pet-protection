package net.wildyax.petprotect.render.parrot;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ParrotRenderer;

public class ModParrotRenderer extends ParrotRenderer {

    public ModParrotRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.addLayer(new ParrotMedalRenderLayer(this, context.getItemRenderer()));
    }
}
