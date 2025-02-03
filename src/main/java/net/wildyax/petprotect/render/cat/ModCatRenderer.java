package net.wildyax.petprotect.render.cat;

import net.minecraft.client.renderer.entity.CatRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class ModCatRenderer extends CatRenderer {

    public ModCatRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.addLayer(new CatMedalRenderLayer(this, context.getItemRenderer()));
    }
}
