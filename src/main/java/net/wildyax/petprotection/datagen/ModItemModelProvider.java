package net.wildyax.petprotection.datagen;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.wildyax.petprotection.PetProtection;
import net.wildyax.petprotection.item.ModItems;

public class ModItemModelProvider extends ItemModelProvider {

    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, PetProtection.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        basicItem(ModItems.IRON_MEDAL.get());
        basicItem(ModItems.GOLD_MEDAL.get());
        basicItem(ModItems.DIAMOND_MEDAL.get());
        basicItem(ModItems.SOUL_IRON_MEDAL.get());
        basicItem(ModItems.SOUL_GOLD_MEDAL.get());
        basicItem(ModItems.SOUL_DIAMOND_MEDAL.get());
        basicItem(ModItems.SOUL_VESSEL.get());
        basicItem(ModItems.RESURRECTION_PEARL.get());
    }
}
