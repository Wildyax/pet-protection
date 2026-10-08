package net.wildyax.petprotect.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public record SoulMedalDataComponent(
        String entityTagName,
        String entityType,
        String entityOwner,
        CompoundTag entityData
) {

    public static final Codec<SoulMedalDataComponent> MEDAL_CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.STRING.fieldOf("entityTagName").forGetter(SoulMedalDataComponent::entityTagName),
                    Codec.STRING.fieldOf("entityType").forGetter(SoulMedalDataComponent::entityType),
                    Codec.STRING.fieldOf("entityOwner").forGetter(SoulMedalDataComponent::entityOwner),
                    CompoundTag.CODEC.fieldOf("entityData").forGetter(SoulMedalDataComponent::entityData)
            ).apply(instance, SoulMedalDataComponent::new)
    );

    public static final StreamCodec<ByteBuf, SoulMedalDataComponent> MEDAL_STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8, SoulMedalDataComponent::entityTagName,
            ByteBufCodecs.STRING_UTF8, SoulMedalDataComponent::entityType,
            ByteBufCodecs.STRING_UTF8, SoulMedalDataComponent::entityOwner,
            ByteBufCodecs.COMPOUND_TAG, SoulMedalDataComponent::entityData,
            SoulMedalDataComponent::new
    );
}
