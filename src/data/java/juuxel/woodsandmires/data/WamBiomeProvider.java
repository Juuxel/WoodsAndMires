package juuxel.woodsandmires.data;

import juuxel.woodsandmires.biome.WamBiomes;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.world.biome.Biome;

import java.util.stream.Stream;

public final class WamBiomeProvider extends EncoderBasedDataProvider<Biome> {
    public WamBiomeProvider(FabricDataGenerator dataGenerator) {
        super(dataGenerator, Biome.CODEC, BuiltinRegistries.BIOME);
    }

    @Override
    protected Stream<Biome> getEntries() {
        return Stream.of(
            WamBiomes.PINE_FOREST,
            WamBiomes.SNOWY_PINE_FOREST,
            WamBiomes.OLD_GROWTH_PINE_FOREST,
            WamBiomes.PINE_FOREST_CLEARING,
            WamBiomes.PINE_MIRE,
            WamBiomes.FELL
        ).map(BuiltinRegistries.BIOME::getOrThrow);
    }

    @Override
    public String getName() {
        return "Biomes";
    }
}
