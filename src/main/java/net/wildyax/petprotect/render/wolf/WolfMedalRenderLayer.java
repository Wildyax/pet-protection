package net.wildyax.petprotect.render.wolf;

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
import net.wildyax.petprotect.item.medal.MedalItem;
import net.wildyax.petprotect.mixin.WolfModelAccessor;
import net.wildyax.petprotect.utils.ModAttachments;

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
        if (wolf.isTame() && wolf.getData(ModAttachments.MEDAL_ITEM).getStackInSlot(0) != ItemStack.EMPTY) {
            ItemStack medalItemStack = wolf.getData(ModAttachments.MEDAL_ITEM).getStackInSlot(0);

            if (medalItemStack.getItem() instanceof MedalItem medalItem && medalItem.renderOnPet()) {

                poseStack.pushPose();

                WolfModelAccessor wolfModel = (WolfModelAccessor) this.getParentModel();
                ModelPart body = wolfModel.getBody();
                body.translateAndRotate(poseStack);

                if (wolf.isInSittingPose()) {
                    poseStack.translate(0F, -0.28F, -0.34f);
                    poseStack.mulPose(Axis.XP.rotationDegrees(117f));

                } else {
                    poseStack.translate(0F, -0.507F, -0.235F);
                    poseStack.mulPose(Axis.XP.rotationDegrees(90F));
                }

                poseStack.mulPose(Axis.YP.rotationDegrees(180F));
                poseStack.scale(0.22F, 0.22F, 0.22F);

                this.itemRenderer.renderStatic(medalItemStack, ItemDisplayContext.FIXED, packedLight, OverlayTexture.NO_OVERLAY, poseStack, multiBufferSource, wolf.level(), 0);

                poseStack.popPose();
            }
        }
    }
}
