package net.wildyax.petprotect.utils;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.wildyax.petprotect.PetProtect;

import java.util.function.Supplier;

public class ModAttachments {

    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES =
            DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, PetProtect.MOD_ID);

    public static final Supplier<AttachmentType<ItemStackHandler>> MEDAL_ITEM = ATTACHMENT_TYPES.register(
            "medal_item", () -> AttachmentType.serializable(() -> new ItemStackHandler(1)).build()
    );

    public static void register(IEventBus eventBus) {
        ATTACHMENT_TYPES.register(eventBus);
    }
}
