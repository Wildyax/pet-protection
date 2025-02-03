package net.wildyax.petprotect.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Items;
import net.wildyax.petprotect.item.ModItems;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider {

    public ModRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.IRON_MEDAL)
                .define('X', Items.STRING)
                .define('#', Items.IRON_INGOT)
                .pattern("X")
                .pattern("#")
                .unlockedBy("has_iron_ingot", has(Items.IRON_INGOT))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.GOLD_MEDAL)
                .define('X', Items.STRING)
                .define('#', Items.GOLD_INGOT)
                .pattern("X")
                .pattern("#")
                .unlockedBy("has_gold_ingot", has(Items.GOLD_INGOT))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.DIAMOND_MEDAL)
                .define('X', Items.STRING)
                .define('#', Items.DIAMOND)
                .pattern("X")
                .pattern("#")
                .unlockedBy("has_diamond", has(Items.DIAMOND))
                .save(recipeOutput);
    }
}
