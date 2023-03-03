package juuxel.woodsandmires.data;

import juuxel.woodsandmires.feature.WamPlacedFeatureKeys;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.world.gen.feature.PlacedFeature;

import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

public final class WamPlacedFeaturesProvider extends EncoderBasedDataProvider<PlacedFeature> {
    public WamPlacedFeaturesProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture, RegistryKeys.PLACED_FEATURE);
    }

    @Override
    protected Stream<RegistryKey<PlacedFeature>> getEntries() {
        return WamPlacedFeatureKeys.ALL.stream();
    }

    @Override
    public String getName() {
        return "Placed Features";
    }
}
