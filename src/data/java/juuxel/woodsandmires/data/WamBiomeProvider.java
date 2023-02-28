package juuxel.woodsandmires.data;

import juuxel.woodsandmires.data.builtin.WamBiomes;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.biome.Biome;

import java.util.stream.Stream;

public final class WamBiomeProvider extends EncoderBasedDataProvider<Biome> {
    public WamBiomeProvider(FabricDataGenerator dataGenerator) {
        super(dataGenerator, Biome.CODEC, BuiltinRegistries.BIOME);
    }

    @Override
    protected Stream<Biome> getEntries() {
        return WamBiomes.BIOMES.stream().map(RegistryEntry::value);
    }

    @Override
    public String getName() {
        return "Biomes";
    }
}
