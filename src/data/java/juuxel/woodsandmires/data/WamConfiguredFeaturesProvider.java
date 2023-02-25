package juuxel.woodsandmires.data;

import juuxel.woodsandmires.data.builtin.WamConfiguredFeatures;
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
        return WamConfiguredFeatures.CONFIGURED_FEATURES.stream().map(RegistryEntry::value);
    }

    @Override
    public String getName() {
        return "Configured Features";
    }
}
