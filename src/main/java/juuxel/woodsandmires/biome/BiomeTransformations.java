package juuxel.woodsandmires.biome;

import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.layer.util.LayerRandomnessSource;

public final class BiomeTransformations {
    private static final int PINE_FOREST_ID = getRawId(WamBiomes.PINE_FOREST);
    private static final int PINE_FOREST_CLEARING_ID = getRawId(WamBiomes.PINE_FOREST_CLEARING);
    private static final int PINE_FOREST_HILLS_ID = getRawId(WamBiomes.PINE_FOREST_HILLS);
    private static final int PINE_MIRE_ID = getRawId(WamBiomes.PINE_MIRE);
    private static final int KETTLE_POND_ID = getRawId(WamBiomes.KETTLE_POND);

    private static int getRawId(RegistryKey<Biome> biomeKey) {
        return BuiltinRegistries.BIOME.getRawId(BuiltinRegistries.BIOME.getOrThrow(biomeKey));
    }

    private BiomeTransformations() {
    }

    public static int transformMediumSubBiome(LayerRandomnessSource random, int biome) {
        if (biome == PINE_FOREST_ID && random.nextInt(3) == 0) {
            return PINE_FOREST_CLEARING_ID;
        }

        return biome;
    }

    public static int transformLargeSubBiome(LayerRandomnessSource random, int biome) {
        if (biome == PINE_FOREST_HILLS_ID && random.nextInt(3) == 0) {
            return PINE_MIRE_ID;
        }

        return biome;
    }

    public static int transformSmallSubBiome(LayerRandomnessSource random, int biome) {
        if (biome == PINE_FOREST_HILLS_ID && random.nextInt(3) == 0) {
            return KETTLE_POND_ID;
        }

        return biome;
    }
}
