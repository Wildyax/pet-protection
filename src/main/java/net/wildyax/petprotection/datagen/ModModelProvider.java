package net.wildyax.petprotection.datagen;

import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.data.PackOutput;
import net.wildyax.petprotection.PetProtection;
import net.wildyax.petprotection.item.ModItems;

public class ModModelProvider extends ModelProvider {

    public ModModelProvider(PackOutput output) {
        super(output, PetProtection.MOD_ID);
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        itemModels.generateFlatItem(ModItems.IRON_MEDAL.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.GOLD_MEDAL.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.DIAMOND_MEDAL.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.SOUL_IRON_MEDAL.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.SOUL_GOLD_MEDAL.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.SOUL_DIAMOND_MEDAL.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.SOUL_VESSEL.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.RESURRECTION_PEARL.get(), ModelTemplates.FLAT_ITEM);
    }
}
