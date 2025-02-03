package net.wildyax.petprotect.datagen;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.wildyax.petprotect.PetProtect;
import net.wildyax.petprotect.item.ModItems;

public class ModItemModelProvider extends ItemModelProvider {

    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, PetProtect.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        basicItem(ModItems.IRON_MEDAL.get());
        basicItem(ModItems.GOLD_MEDAL.get());
        basicItem(ModItems.DIAMOND_MEDAL.get());
    }
}
