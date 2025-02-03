package net.wildyax.petprotect.render.cat;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.CatModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.wildyax.petprotect.item.medal.MedalItem;
import net.wildyax.petprotect.utils.ModAttachments;

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
        if (cat.isTame() && cat.getData(ModAttachments.MEDAL_ITEM).getStackInSlot(0) != ItemStack.EMPTY) {
            ItemStack medalItemStack = cat.getData(ModAttachments.MEDAL_ITEM).getStackInSlot(0);

            if (medalItemStack.getItem() instanceof MedalItem medalItem && medalItem.renderOnPet()) {

                poseStack.pushPose();

                if (cat.isInSittingPose()) {
                    poseStack.translate(0F, 0.94F, -0.5F);
                    poseStack.mulPose(Axis.XP.rotationDegrees(-45F));
                    poseStack.mulPose(Axis.YP.rotationDegrees(0F));
                    poseStack.mulPose(Axis.ZP.rotationDegrees(180f));
                } else {
                    poseStack.translate(0F, 1.175F, -0.45F);
                    poseStack.mulPose(Axis.ZP.rotationDegrees(180f));
                }

                poseStack.scale(0.22F, 0.22F, 0.22F);


                this.itemRenderer.renderStatic(medalItemStack, ItemDisplayContext.FIXED, packedLight, OverlayTexture.NO_OVERLAY, poseStack, multiBufferSource, cat.level(), 0);

                poseStack.popPose();
            }
        }
    }
}
