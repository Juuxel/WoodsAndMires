package juuxel.woodsandmires.data;

import juuxel.woodsandmires.data.builtin.WamPlacedFeatures;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.gen.feature.PlacedFeature;

import java.util.stream.Stream;

public final class WamPlacedFeaturesProvider extends EncoderBasedDataProvider<PlacedFeature> {
    public WamPlacedFeaturesProvider(FabricDataGenerator dataGenerator) {
        super(dataGenerator, PlacedFeature.CODEC, BuiltinRegistries.PLACED_FEATURE);
    }

    @Override
    protected Stream<PlacedFeature> getEntries() {
        return WamPlacedFeatures.PLACED_FEATURES.stream().map(RegistryEntry::value);
    }

    @Override
    public String getName() {
        return "Placed Features";
    }
}
