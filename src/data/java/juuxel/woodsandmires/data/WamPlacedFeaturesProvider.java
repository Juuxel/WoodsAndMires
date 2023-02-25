package juuxel.woodsandmires.data;

import juuxel.woodsandmires.feature.WamPlacedFeatures;
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
        return Stream.of(
            WamPlacedFeatures.FOREST_PINE,
            WamPlacedFeatures.SNOWY_FOREST_PINE,
            WamPlacedFeatures.OLD_GROWTH_FOREST_PINE,
            WamPlacedFeatures.GIANT_PINE,
            WamPlacedFeatures.PINE_FOREST_BOULDER,
            WamPlacedFeatures.MIRE_PONDS,
            WamPlacedFeatures.MIRE_FLOWERS,
            WamPlacedFeatures.MIRE_MEADOW,
            WamPlacedFeatures.MIRE_PINE_SNAG,
            WamPlacedFeatures.MIRE_PINE_SHRUB,
            WamPlacedFeatures.CLEARING_MEADOW,
            WamPlacedFeatures.CLEARING_BIRCH,
            WamPlacedFeatures.CLEARING_FLOWERS,
            WamPlacedFeatures.CLEARING_SNAG,
            WamPlacedFeatures.CLEARING_PINE_SHRUB,
            WamPlacedFeatures.CLEARING_FALLEN_PINE,
            WamPlacedFeatures.FELL_VEGETATION,
            WamPlacedFeatures.FELL_BOULDER,
            WamPlacedFeatures.FELL_POND,
            WamPlacedFeatures.FELL_BIRCH_SHRUB,
            WamPlacedFeatures.PLAINS_FLOWERS,
            WamPlacedFeatures.FOREST_TANSY
        ).map(RegistryEntry::value);
    }

    @Override
    public String getName() {
        return "Placed Features";
    }
}
