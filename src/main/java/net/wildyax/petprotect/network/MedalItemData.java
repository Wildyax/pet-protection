package net.wildyax.petprotect.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.wildyax.petprotect.PetProtect;

public record MedalItemData(Integer entityID, CompoundTag itemTag) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<MedalItemData> TYPE =
            new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(PetProtect.MOD_ID, "medal_item_data"));

    public static final StreamCodec<ByteBuf, MedalItemData> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT,
            MedalItemData::entityID,
            ByteBufCodecs.COMPOUND_TAG,
            MedalItemData::itemTag,
            MedalItemData::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
