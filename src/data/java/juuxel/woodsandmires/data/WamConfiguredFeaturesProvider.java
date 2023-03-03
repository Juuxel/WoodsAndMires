package juuxel.woodsandmires.data;

import juuxel.woodsandmires.feature.WamConfiguredFeatureKeys;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.world.gen.feature.ConfiguredFeature;

import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

public final class WamConfiguredFeaturesProvider extends EncoderBasedDataProvider<ConfiguredFeature<?, ?>> {
    public WamConfiguredFeaturesProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture, RegistryKeys.CONFIGURED_FEATURE);
    }

    @Override
    protected Stream<RegistryKey<ConfiguredFeature<?, ?>>> getEntries() {
        return WamConfiguredFeatureKeys.ALL.stream();
    }

    @Override
    public String getName() {
        return "Configured Features";
    }
}
