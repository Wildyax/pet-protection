package net.wildyax.petprotection.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.ParrotModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.animal.Parrot;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.wildyax.petprotection.item.medal.MedalItem;
import net.wildyax.petprotection.utils.ModAttachments;


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
        ItemStack medalItemStack = parrot.getData(ModAttachments.MEDAL_ITEM).getStackInSlot(0);

        if (parrot.isTame() && medalItemStack.getItem() instanceof MedalItem medalItem && medalItem.renderOnPet()) {
            poseStack.pushPose();

            // Follow the parrot body (standing, sitting, dancing...)
            this.getParentModel().root().getChild("body").translateAndRotate(poseStack);
            renderMedalOnBody(poseStack, multiBufferSource, packedLight, medalItemStack, itemRenderer, parrot.level());

            poseStack.popPose();
        }
    }

    public static void renderMedalOnBody(PoseStack poseStack, MultiBufferSource buffer, int packedLight, ItemStack medalItemStack, ItemRenderer itemRenderer, Level level) {
        poseStack.translate(0F, 0.06F, -0.129F);
        poseStack.mulPose(Axis.XP.rotationDegrees(-27F));
        poseStack.mulPose(Axis.ZP.rotationDegrees(180F));
        poseStack.scale(0.14F, 0.14F, 0.14F);

        itemRenderer.renderStatic(medalItemStack, ItemDisplayContext.FIXED, packedLight, OverlayTexture.NO_OVERLAY, poseStack, buffer, level, 0);
    }
}
