package net.wildyax.petprotect.render.parrot;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.ParrotModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.animal.Parrot;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.wildyax.petprotect.item.medal.MedalItem;
import net.wildyax.petprotect.utils.ModAttachments;


public class ParrotMedalRenderLayer extends RenderLayer<Parrot, ParrotModel> {

    private final ItemRenderer itemRenderer;

    public ParrotMedalRenderLayer(RenderLayerParent<Parrot, ParrotModel> renderer, ItemRenderer itemRenderer) {
        super(renderer);
        this.itemRenderer = itemRenderer;
    }

    @Override
    public void render(
            PoseStack poseStack,
            MultiBufferSource multiBufferSource,
            int packedLight,
            Parrot parrot,
            float v, float v1, float v2, float v3, float v4, float v5
    ) {
        if (parrot.isTame() && !parrot.getData(ModAttachments.MEDAL_ITEM).getStackInSlot(0).isEmpty()) {
            ItemStack medalItemStack = parrot.getData(ModAttachments.MEDAL_ITEM).getStackInSlot(0);

            if (medalItemStack.getItem() instanceof MedalItem medalItem && medalItem.renderOnPet()) {

                poseStack.pushPose();

                if (parrot.isInSittingPose()) {
                    poseStack.translate(0F, 1.265F, -0.27F);
                } else {
                    ParrotModel parrotModel = this.getParentModel();
                    ModelPart body = parrotModel.root().getChild("body");
                    body.translateAndRotate(poseStack);
                    poseStack.translate(0F, 0.06F, -0.129F);
                    poseStack.mulPose(Axis.XP.rotationDegrees(-27f));
                }

                poseStack.mulPose(Axis.ZP.rotationDegrees(180f));
                poseStack.scale(0.14F, 0.14F, 0.14F);


                this.itemRenderer.renderStatic(medalItemStack, ItemDisplayContext.FIXED, packedLight, OverlayTexture.NO_OVERLAY, poseStack, multiBufferSource, parrot.level(), 0);

                poseStack.popPose();
            }
        }

    }
}
