package juuxel.woodsandmires.feature;

import juuxel.woodsandmires.WoodsAndMires;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;

public final class WamFeatures {
    public static final Feature<ShrubFeatureConfig> SHRUB = new ShrubFeature(ShrubFeatureConfig.CODEC);
    public static final Feature<DefaultFeatureConfig> MIRE_PONDS = new MirePondsFeature(DefaultFeatureConfig.CODEC);
    public static final Feature<MeadowFeatureConfig> MEADOW = new MeadowFeature(MeadowFeatureConfig.CODEC);
    public static final Feature<FellPondFeatureConfig> FELL_POND = new FellPondFeature(FellPondFeatureConfig.CODEC);
    public static final Feature<FallenLogFeatureConfig> FALLEN_LOG = new FallenLogFeature(FallenLogFeatureConfig.CODEC);

    private WamFeatures() {
    }

    public static void init() {
        register("shrub", SHRUB);
        register("mire_ponds", MIRE_PONDS);
        register("meadow", MEADOW);
        register("fell_pond", FELL_POND);
        register("fallen_log", FALLEN_LOG);
    }

    private static void register(String id, Feature<?> feature) {
        Registry.register(Registry.FEATURE, WoodsAndMires.id(id), feature);
    }
}
