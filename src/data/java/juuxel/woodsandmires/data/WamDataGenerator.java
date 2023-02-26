package juuxel.woodsandmires.data;

import juuxel.woodsandmires.data.builtin.WamBiomes;
import juuxel.woodsandmires.data.builtin.WamConfiguredFeatures;
import juuxel.woodsandmires.data.builtin.WamPlacedFeatures;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public final class WamDataGenerator implements ModInitializer, DataGeneratorEntrypoint {
    @Override
    public void onInitialize() {
        WamConfiguredFeatures.register();
        WamPlacedFeatures.register();
        WamBiomes.register();
    }

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        fabricDataGenerator.addProvider(WamConfiguredFeaturesProvider::new);
        fabricDataGenerator.addProvider(WamPlacedFeaturesProvider::new);
        fabricDataGenerator.addProvider(WamBiomeProvider::new);
        fabricDataGenerator.addProvider(WamBlockLootTableProvider::new);
        fabricDataGenerator.addProvider(WamChestLootTableProvider::new);
    }
}
