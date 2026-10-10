package net.wildyax.petprotection.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.CatModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.wildyax.petprotection.item.medal.MedalItem;
import net.wildyax.petprotection.mixin.OcelotModelAccessor;
import net.wildyax.petprotection.utils.ModAttachments;

public class CatMedalRenderLayer extends RenderLayer<Cat, CatModel<Cat>> {

    private final ItemRenderer itemRenderer;

    public CatMedalRenderLayer(RenderLayerParent<Cat, CatModel<Cat>> renderer, ItemRenderer itemRenderer) {
        super(renderer);
        this.itemRenderer = itemRenderer;
    }


    @Override
    public void render(
            PoseStack poseStack,
            MultiBufferSource multiBufferSource,
            int packedLight,
            Cat cat,
            float v, float v1, float v2, float v3, float v4, float v5
    ) {
        ItemStack medalItemStack = cat.getData(ModAttachments.MEDAL_ITEM).getStackInSlot(0);

        if (cat.isTame() && medalItemStack.getItem() instanceof MedalItem medalItem && medalItem.renderOnPet()) {
            poseStack.pushPose();

            if (this.getParentModel().young) {
                poseStack.scale(0.5F, 0.5F, 0.5F);
                poseStack.translate(0.0F, 1.5F, 0.0F);
            }

            // Follow the cat body (standing, sitting, crouching...)
            ModelPart body = ((OcelotModelAccessor) this.getParentModel()).getBody();
            body.translateAndRotate(poseStack);

            poseStack.translate(0F, 0.175F, -0.425F);
            poseStack.mulPose(Axis.XP.rotationDegrees(-90F));
            poseStack.mulPose(Axis.ZP.rotationDegrees(180F));

            poseStack.scale(0.22F, 0.22F, 0.22F);


            this.itemRenderer.renderStatic(medalItemStack, ItemDisplayContext.FIXED, packedLight, OverlayTexture.NO_OVERLAY, poseStack, multiBufferSource, cat.level(), 0);

            poseStack.popPose();

        }
    }
}
