package gay.nyako.vanityslots;

import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.model.ModelTemplates;
import org.jetbrains.annotations.NotNull;

public class VanitySlotsModelProvider extends FabricModelProvider {
    public VanitySlotsModelProvider(FabricPackOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(@NotNull BlockModelGenerators blockModelGenerators) {

    }

    @Override
    public void generateItemModels(@NotNull ItemModelGenerators itemModelGenerators) {
        itemModelGenerators.generateFlatItem(VanitySlotsItems.FAMILIAR_WIG, ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(VanitySlotsItems.FAMILIAR_SHIRT, ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(VanitySlotsItems.FAMILIAR_PANTS, ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(VanitySlotsItems.FAMILIAR_SNEAKERS, ModelTemplates.FLAT_ITEM);
    }

    @Override
    public @NotNull String getName() {
        return "VanitySlotsModelProvider";
    }
}
