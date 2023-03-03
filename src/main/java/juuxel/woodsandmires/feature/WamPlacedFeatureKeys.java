package juuxel.woodsandmires.feature;

import juuxel.woodsandmires.WoodsAndMires;
import juuxel.woodsandmires.util.RegistryCollector;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.world.gen.feature.PlacedFeature;

public final class WamPlacedFeatureKeys {
    public static final RegistryCollector<RegistryKey<PlacedFeature>> ALL = new RegistryCollector<>();

    // Pine forest
    public static final RegistryKey<PlacedFeature> FOREST_PINE = key("forest_pine");
    public static final RegistryKey<PlacedFeature> SNOWY_FOREST_PINE = key("snowy_forest_pine");
    public static final RegistryKey<PlacedFeature> OLD_GROWTH_PINE_FOREST_TREES = key("old_growth_pine_forest_trees");
    public static final RegistryKey<PlacedFeature> PINE_FOREST_BOULDER = key("pine_forest_boulder");
    public static final RegistryKey<PlacedFeature> PINE_FOREST_HEATHER_PATCH = key("pine_forest_heather_patch");
    public static final RegistryKey<PlacedFeature> LUSH_PINE_FOREST_TREES = key("lush_pine_forest_trees");
    public static final RegistryKey<PlacedFeature> LUSH_PINE_FOREST_FLOWERS = key("lush_pine_forest_flowers");
    public static final RegistryKey<PlacedFeature> FALLEN_PINE = key("fallen_pine");

    // Mire
    public static final RegistryKey<PlacedFeature> MIRE_PONDS = key("mire_ponds");
    public static final RegistryKey<PlacedFeature> MIRE_FLOWERS = key("mire_flowers");
    public static final RegistryKey<PlacedFeature> MIRE_MEADOW = key("mire_meadow");
    public static final RegistryKey<PlacedFeature> MIRE_PINE_SNAG = key("mire_pine_snag");
    public static final RegistryKey<PlacedFeature> MIRE_PINE_SHRUB = key("mire_pine_shrub");

    // Fells
    public static final RegistryKey<PlacedFeature> FELL_VEGETATION = key("fell_vegetation");
    public static final RegistryKey<PlacedFeature> FELL_BOULDER = key("fell_boulder");
    public static final RegistryKey<PlacedFeature> FELL_POND = key("fell_pond");
    public static final RegistryKey<PlacedFeature> FELL_BIRCH_SHRUB = key("fell_birch_shrub");
    public static final RegistryKey<PlacedFeature> FELL_LICHEN = key("fell_lichen");
    public static final RegistryKey<PlacedFeature> FELL_MOSS_PATCH = key("fell_moss_patch");
    public static final RegistryKey<PlacedFeature> FROZEN_TREASURE = key("frozen_treasure");
    public static final RegistryKey<PlacedFeature> FELL_HEATHER_PATCH = key("fell_heather_patch");

    // Groves
    public static final RegistryKey<PlacedFeature> PINY_GROVE_TREES = key("piny_grove_trees");

    // Vanilla biomes
    public static final RegistryKey<PlacedFeature> PLAINS_FLOWERS = key("plains_flowers");
    public static final RegistryKey<PlacedFeature> FOREST_TANSY = key("forest_tansy");
    public static final RegistryKey<PlacedFeature> TAIGA_HEATHER_PATCH = key("taiga_heather_patch");

    private static RegistryKey<PlacedFeature> key(String id) {
        return ALL.add(RegistryKey.of(RegistryKeys.PLACED_FEATURE, WoodsAndMires.id(id)));
    }
}
