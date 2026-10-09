package net.wildyax.petprotect.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.wildyax.petprotect.item.medal.MedalItem;


public class ParrotOnPlayerShoulderLayer extends RenderLayer<Player, PlayerModel<Player>> {

    private final ItemRenderer itemRenderer;

    public ParrotOnPlayerShoulderLayer(RenderLayerParent<Player, PlayerModel<Player>> renderer, ItemRenderer itemRenderer) {
        super(renderer);
        this.itemRenderer = itemRenderer;
    }

    @Override
    public void render(
            PoseStack poseStack,
            MultiBufferSource buffer,
            int packedLight,
            Player player,
            float limbSwing,
            float limbSwingAmount,
            float partialTicks,
            float ageInTicks,
            float netHeadYaw,
            float headPitch
    ) {
        this.render(poseStack, buffer, packedLight, player, limbSwing, limbSwingAmount, netHeadYaw, headPitch, true);
        this.render(poseStack, buffer, packedLight, player, limbSwing, limbSwingAmount, netHeadYaw, headPitch, false);
    }

    private void render(
            PoseStack poseStack,
            MultiBufferSource buffer,
            int packedLight,
            Player player,
            float limbSwing,
            float limbSwingAmount,
            float netHeadYaw,
            float headPitch,
            boolean leftShoulder
    ) {
        CompoundTag compoundtag = leftShoulder ? player.getShoulderEntityLeft() : player.getShoulderEntityRight();
        var attachmentsTag = compoundtag.get("neoforge:attachments");
        if (attachmentsTag instanceof CompoundTag attachment) {
            var itemTag = attachment.get("petprotect:medal_item");
            if (itemTag instanceof CompoundTag medalItemTag) {

                ItemStackHandler itemStackHandler = new ItemStackHandler();
                itemStackHandler.deserializeNBT(player.level().registryAccess(), medalItemTag);

                if (!itemStackHandler.getStackInSlot(0).isEmpty()) {
                    ItemStack medalItemStack = itemStackHandler.getStackInSlot(0);

                    if (medalItemStack.getItem() instanceof MedalItem medalItem && medalItem.renderOnPet()) {

                        EntityType.byString(compoundtag.getString("id"))
                                .filter(entityType -> entityType == EntityType.PARROT)
                                .ifPresent(entityType -> {
                                    poseStack.pushPose();

                                    // Same position as the vanilla ParrotOnShoulderLayer
                                    poseStack.translate(leftShoulder ? 0.4F : -0.4f, player.isCrouching() ? -1.3F : -1.5F, 0.0F);

                                    // Parrot body pose when on shoulder (values from ParrotModel)
                                    poseStack.translate(0F, 16.5F / 16F, -3F / 16F);
                                    poseStack.mulPose(Axis.XP.rotation(0.4937F));

                                    ParrotMedalRenderLayer.renderMedalOnBody(poseStack, buffer, packedLight, medalItemStack, itemRenderer, player.level());

                                    poseStack.popPose();
                                });
                    }
                }
            }
        }
    }
}
