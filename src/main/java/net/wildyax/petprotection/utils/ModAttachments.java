package net.wildyax.petprotection.utils;

import net.minecraft.world.ItemStackWithSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.attachment.IAttachmentHolder;
import net.neoforged.neoforge.attachment.IAttachmentSerializer;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.wildyax.petprotection.PetProtection;

import java.util.function.Supplier;

public class ModAttachments {

    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES =
            DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, PetProtection.MOD_ID);

    public static final Supplier<AttachmentType<ItemStack>> MEDAL_ITEM = ATTACHMENT_TYPES.register(
        "medal_item", () -> AttachmentType.builder(() -> ItemStack.EMPTY)
            .serialize(new MedalItemSerializer())
            .sync(ItemStack.OPTIONAL_STREAM_CODEC)
            .build()
    );

    public static void register(IEventBus eventBus) {
        ATTACHMENT_TYPES.register(eventBus);
    }

    private static class MedalItemSerializer implements IAttachmentSerializer<ItemStack> {

        @Override
        public ItemStack read(IAttachmentHolder holder, ValueInput input) {
            return input.read("item", ItemStack.CODEC).orElseGet(() -> readLegacy(input));
        }

        // 1.21.1 Compatibility -> Read ItemStackHandler instead of current ItemStack
        // ItemStackWithSlot.CODEC is the same codec used by ItemStackHandler for items list
        private static ItemStack readLegacy(ValueInput input) {
            for (ItemStackWithSlot slot : input.listOrEmpty("Items", ItemStackWithSlot.CODEC)) {
                return slot.stack();
            }
            return ItemStack.EMPTY;
        }

        @Override
        public boolean write(ItemStack stack, ValueOutput output) {
            if (stack.isEmpty()) {
                return false;
            }
            output.store("item", ItemStack.CODEC, stack);
            return true;
        }
    }
}
