package net.wildyax.petprotection.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.WolfModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.wildyax.petprotection.item.medal.MedalItem;
import net.wildyax.petprotection.mixin.WolfModelAccessor;
import net.wildyax.petprotection.utils.ModAttachments;

public class WolfMedalRenderLayer extends RenderLayer<Wolf, WolfModel<Wolf>> {

    private final ItemRenderer itemRenderer;

    public WolfMedalRenderLayer(RenderLayerParent<Wolf, WolfModel<Wolf>> renderer, ItemRenderer itemRenderer) {
        super(renderer);
        this.itemRenderer = itemRenderer;
    }

    @Override
    public void render(
            PoseStack poseStack,
            MultiBufferSource multiBufferSource,
            int packedLight,
            Wolf wolf,
            float v, float v1, float v2, float v3, float v4, float v5
    ) {
        if (wolf.isTame() && !wolf.getData(ModAttachments.MEDAL_ITEM).getStackInSlot(0).isEmpty()) {
            ItemStack medalItemStack = wolf.getData(ModAttachments.MEDAL_ITEM).getStackInSlot(0);

            if (medalItemStack.getItem() instanceof MedalItem medalItem && medalItem.renderOnPet()) {

                poseStack.pushPose();

                if (this.getParentModel().young) {
                    poseStack.scale(0.5F, 0.5F, 0.5F);
                    poseStack.translate(0.0F, 1.5F, 0.0F);
                }

                // Follow the wolf body (standing, sitting, ...)
                WolfModelAccessor wolfModel = (WolfModelAccessor) this.getParentModel();
                ModelPart body = wolfModel.getUpperBody();
                body.translateAndRotate(poseStack);

                poseStack.translate(0.06F, -0.195F, -0.235F);
                poseStack.scale(0.22F, 0.22F, 0.22F);
                poseStack.mulPose(Axis.XP.rotationDegrees(90F));
                poseStack.mulPose(Axis.YP.rotationDegrees(180F));

                this.itemRenderer.renderStatic(medalItemStack, ItemDisplayContext.FIXED, packedLight, OverlayTexture.NO_OVERLAY, poseStack, multiBufferSource, wolf.level(), 0);

                poseStack.popPose();
            }
        }
    }
}
