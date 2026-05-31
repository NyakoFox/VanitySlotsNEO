package gay.nyako.vanityslots;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class VanitySlotsRecipeProvider extends FabricRecipeProvider {
    public VanitySlotsRecipeProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected @NotNull RecipeProvider createRecipeProvider(HolderLookup.@NotNull Provider registryLookup, @NotNull RecipeOutput exporter) {
        return new RecipeProvider(registryLookup, exporter) {
            private void generateVanity(Item inputItem, Item outputItem)
            {
                shaped(RecipeCategory.COMBAT, outputItem, 1)
                        .pattern("ggg")
                        .pattern("gLg")
                        .pattern("ggg")
                        .define('g', Items.GLOWSTONE_DUST)
                        .define('L', inputItem)
                        .unlockedBy(getHasName(inputItem), has(inputItem))
                        .save(output);
            }

            @Override
            public void buildRecipes() {
                HolderLookup.RegistryLookup<Item> itemLookup = registries.lookupOrThrow(Registries.ITEM);

                generateVanity(Items.LEATHER_HELMET, VanitySlotsItems.FAMILIAR_WIG);
                generateVanity(Items.LEATHER_CHESTPLATE, VanitySlotsItems.FAMILIAR_SHIRT);
                generateVanity(Items.LEATHER_LEGGINGS, VanitySlotsItems.FAMILIAR_PANTS);
                generateVanity(Items.LEATHER_BOOTS, VanitySlotsItems.FAMILIAR_SNEAKERS);
            }
        };
    }

    @Override
    public @NotNull String getName() {
        return "VanitySlotsRecipeProvider";
    }
}
