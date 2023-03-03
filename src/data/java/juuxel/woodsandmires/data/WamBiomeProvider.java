package juuxel.woodsandmires.data;

import juuxel.woodsandmires.biome.WamBiomeKeys;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.world.biome.Biome;

import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

public final class WamBiomeProvider extends EncoderBasedDataProvider<Biome> {
    public WamBiomeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture, RegistryKeys.BIOME);
    }

    @Override
    protected Stream<RegistryKey<Biome>> getEntries() {
        return WamBiomeKeys.ALL.stream();
    }

    @Override
    public String getName() {
        return "Biomes";
    }
}
