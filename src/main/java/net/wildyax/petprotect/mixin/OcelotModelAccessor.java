package net.wildyax.petprotect.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.client.model.OcelotModel;
import net.minecraft.client.model.geom.ModelPart;

@Mixin(OcelotModel.class)
public interface OcelotModelAccessor {

    @Accessor("body")
    ModelPart getBody();
}
