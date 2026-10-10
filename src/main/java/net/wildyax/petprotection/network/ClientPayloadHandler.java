package net.wildyax.petprotection.network;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.Entity;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.wildyax.petprotection.utils.ModAttachments;


public class ClientPayloadHandler {

    /**
     * Set the medal data attachment on the client side entity
     * for rendering the medal
     * @param data Entity and item data
     * @param context Context
     */
    public static void setEntityMedalItemData(final MedalItemData data, final IPayloadContext context) {
        ClientLevel level = Minecraft.getInstance().level;

        if (level != null && level.isClientSide) {
            Entity entity = level.getEntity(data.entityID());

            if (entity != null) {
                entity.getData(ModAttachments.MEDAL_ITEM).deserializeNBT(level.registryAccess(), data.itemTag());
            }
        }
    }
}
