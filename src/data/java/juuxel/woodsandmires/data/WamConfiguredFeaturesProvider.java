package juuxel.woodsandmires.data;

import juuxel.woodsandmires.feature.WamConfiguredFeatures;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.gen.feature.ConfiguredFeature;

import java.util.stream.Stream;

public final class WamConfiguredFeaturesProvider extends EncoderBasedDataProvider<ConfiguredFeature<?, ?>> {
    public WamConfiguredFeaturesProvider(FabricDataGenerator dataGenerator) {
        super(dataGenerator, ConfiguredFeature.CODEC, BuiltinRegistries.CONFIGURED_FEATURE);
    }

    @Override
    protected Stream<ConfiguredFeature<?, ?>> getEntries() {
        return Stream.of(
            WamConfiguredFeatures.SHORT_PINE_SHRUB,
            WamConfiguredFeatures.PINE,
            WamConfiguredFeatures.GIANT_PINE,
            WamConfiguredFeatures.PINE_SNAG,
            WamConfiguredFeatures.PLAINS_FLOWERS,
            WamConfiguredFeatures.PINE_FROM_SAPLING,
            WamConfiguredFeatures.PINE_FOREST_BOULDER,
            WamConfiguredFeatures.FOREST_TANSY,
            WamConfiguredFeatures.MIRE_PONDS,
            WamConfiguredFeatures.MIRE_FLOWERS,
            WamConfiguredFeatures.MIRE_MEADOW,
            WamConfiguredFeatures.CLEARING_MEADOW,
            WamConfiguredFeatures.CLEARING_PINE_SHRUB,
            WamConfiguredFeatures.CLEARING_FALLEN_PINE,
            WamConfiguredFeatures.FELL_VEGETATION,
            WamConfiguredFeatures.FELL_BOULDER,
            WamConfiguredFeatures.FELL_POND,
            WamConfiguredFeatures.FELL_BIRCH_SHRUB,
            WamConfiguredFeatures.FELL_LICHEN
        ).map(RegistryEntry::value);
    }

    @Override
    public String getName() {
        return "Configured Features";
    }
}
