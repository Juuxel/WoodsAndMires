package juuxel.woodsandmires.data.builtin;

import com.google.common.collect.ImmutableList;
import juuxel.woodsandmires.WoodsAndMires;
import juuxel.woodsandmires.block.WamBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.intprovider.ClampedIntProvider;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.feature.PlacedFeatures;
import net.minecraft.world.gen.feature.VegetationPlacedFeatures;
import net.minecraft.world.gen.placementmodifier.BiomePlacementModifier;
import net.minecraft.world.gen.placementmodifier.CountPlacementModifier;
import net.minecraft.world.gen.placementmodifier.PlacementModifier;
import net.minecraft.world.gen.placementmodifier.RarityFilterPlacementModifier;
import net.minecraft.world.gen.placementmodifier.SquarePlacementModifier;

import java.util.List;

public final class WamPlacedFeatures {
    public static final RegistryCollector<RegistryEntry<PlacedFeature>> PLACED_FEATURES = new RegistryCollector<>();

    private static List<PlacementModifier> cons(PlacementModifier head, List<PlacementModifier> tail) {
        return ImmutableList.<PlacementModifier>builder().add(head).addAll(tail).build();
    }

    private static List<PlacementModifier> treeModifiers(PlacementModifier countModifier) {
        return VegetationPlacedFeatures.modifiers(countModifier);
    }

    private static List<PlacementModifier> countExtraTreeModifiers(int count, float extraChance, int extraCount) {
        return treeModifiers(PlacedFeatures.createCountExtraModifier(count, extraChance, extraCount));
    }

    private static List<PlacementModifier> treeModifiersWithWouldSurvive(PlacementModifier countModifier, Block block) {
        return VegetationPlacedFeatures.modifiersWithWouldSurvive(countModifier, block);
    }

    private static List<PlacementModifier> countModifiers(int count) {
        return VegetationPlacedFeatures.modifiers(count);
    }

    private static List<PlacementModifier> chanceModifiers(int chance) {
        return List.of(RarityFilterPlacementModifier.of(chance), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
    }

    // Pine forest
    public static final RegistryEntry<PlacedFeature> FOREST_PINE;
    public static final RegistryEntry<PlacedFeature> SNOWY_FOREST_PINE;
    public static final RegistryEntry<PlacedFeature> OLD_GROWTH_PINE_FOREST_TREES;
    public static final RegistryEntry<PlacedFeature> PINE_FOREST_BOULDER;
    public static final RegistryEntry<PlacedFeature> PINE_FOREST_HEATHER_PATCH;
    public static final RegistryEntry<PlacedFeature> LUSH_PINE_FOREST_TREES;
    public static final RegistryEntry<PlacedFeature> LUSH_PINE_FOREST_FLOWERS;
    public static final RegistryEntry<PlacedFeature> FALLEN_PINE;

    static {
        FOREST_PINE = register("forest_pine", WamConfiguredFeatures.PINE, treeModifiersWithWouldSurvive(CountPlacementModifier.of(10), WamBlocks.PINE_SAPLING));
        SNOWY_FOREST_PINE = register("snowy_forest_pine", WamConfiguredFeatures.PINE, treeModifiersWithWouldSurvive(CountPlacementModifier.of(2), WamBlocks.PINE_SAPLING));
        OLD_GROWTH_PINE_FOREST_TREES = register("old_growth_pine_forest_trees", WamConfiguredFeatures.OLD_GROWTH_PINE_FOREST_TREES, countExtraTreeModifiers(10, 0.1f, 1));
        PINE_FOREST_BOULDER = register("pine_forest_boulder", WamConfiguredFeatures.PINE_FOREST_BOULDER, chanceModifiers(16));
        PINE_FOREST_HEATHER_PATCH = register("pine_forest_heather_patch", WamConfiguredFeatures.HEATHER_PATCH, chanceModifiers(12));
        LUSH_PINE_FOREST_TREES = register("lush_pine_forest_trees", WamConfiguredFeatures.LUSH_PINE_FOREST_TREES, countExtraTreeModifiers(8, 0.1f, 1));
        LUSH_PINE_FOREST_FLOWERS = register("lush_pine_forest_flowers", WamConfiguredFeatures.PLAINS_FLOWERS, chanceModifiers(2));
        FALLEN_PINE = register("fallen_pine", WamConfiguredFeatures.FALLEN_PINE, chanceModifiers(7));
    }

    // Mire
    public static final RegistryEntry<PlacedFeature> MIRE_PONDS;
    public static final RegistryEntry<PlacedFeature> MIRE_FLOWERS;
    public static final RegistryEntry<PlacedFeature> MIRE_MEADOW;
    public static final RegistryEntry<PlacedFeature> MIRE_PINE_SNAG;
    public static final RegistryEntry<PlacedFeature> MIRE_PINE_SHRUB;

    static {
        MIRE_PONDS = register("mire_ponds", WamConfiguredFeatures.MIRE_PONDS, List.of(BiomePlacementModifier.of()));
        MIRE_FLOWERS = register("mire_flowers", WamConfiguredFeatures.MIRE_FLOWERS, chanceModifiers(2));
        MIRE_MEADOW = register("mire_meadow", WamConfiguredFeatures.MIRE_MEADOW, List.of(BiomePlacementModifier.of()));
        MIRE_PINE_SNAG = register("mire_pine_snag", WamConfiguredFeatures.PINE_SNAG, treeModifiersWithWouldSurvive(RarityFilterPlacementModifier.of(6), WamBlocks.PINE_SAPLING));
        MIRE_PINE_SHRUB = register("mire_pine_shrub", WamConfiguredFeatures.SHORT_PINE_SHRUB,
            treeModifiersWithWouldSurvive(
                PlacedFeatures.createCountExtraModifier(3, 1/3f, 3),
                WamBlocks.PINE_SAPLING
            )
        );
    }

    // Fells
    public static final RegistryEntry<PlacedFeature> FELL_VEGETATION;
    public static final RegistryEntry<PlacedFeature> FELL_BOULDER;
    public static final RegistryEntry<PlacedFeature> FELL_POND;
    public static final RegistryEntry<PlacedFeature> FELL_BIRCH_SHRUB;
    public static final RegistryEntry<PlacedFeature> FELL_LICHEN;
    public static final RegistryEntry<PlacedFeature> FELL_MOSS_PATCH;
    public static final RegistryEntry<PlacedFeature> FROZEN_TREASURE;
    public static final RegistryEntry<PlacedFeature> FELL_HEATHER_PATCH;

    static {
        FELL_VEGETATION = register("fell_vegetation", WamConfiguredFeatures.FELL_VEGETATION, List.of(BiomePlacementModifier.of()));
        FELL_BOULDER = register("fell_boulder", WamConfiguredFeatures.FELL_BOULDER, chanceModifiers(16));
        FELL_POND = register("fell_pond", WamConfiguredFeatures.FELL_POND, chanceModifiers(7));
        FELL_BIRCH_SHRUB = register("fell_birch_shrub", WamConfiguredFeatures.FELL_BIRCH_SHRUB,
            cons(
                RarityFilterPlacementModifier.of(3),
                treeModifiersWithWouldSurvive(
                    PlacedFeatures.createCountExtraModifier(1, 1/3f, 2),
                    Blocks.BIRCH_SAPLING
                )
            )
        );
        FELL_LICHEN = register("fell_lichen", WamConfiguredFeatures.FELL_LICHEN, chanceModifiers(2));
        FELL_MOSS_PATCH = register("fell_moss_patch", WamConfiguredFeatures.FELL_MOSS_PATCH, chanceModifiers(5));
        FROZEN_TREASURE = register("frozen_treasure", WamConfiguredFeatures.FROZEN_TREASURE, countModifiers(2));
        FELL_HEATHER_PATCH = register("fell_heather_patch", WamConfiguredFeatures.HEATHER_PATCH, chanceModifiers(5));
    }

    // Groves
    public static final RegistryEntry<PlacedFeature> PINY_GROVE_TREES;

    static {
        PINY_GROVE_TREES = register("piny_grove_trees", WamConfiguredFeatures.PINY_GROVE_TREES, countExtraTreeModifiers(10, 0.1f, 1));
    }

    // Vanilla biomes
    public static final RegistryEntry<PlacedFeature> PLAINS_FLOWERS;
    public static final RegistryEntry<PlacedFeature> FOREST_TANSY;
    public static final RegistryEntry<PlacedFeature> TAIGA_HEATHER_PATCH;

    static {
        PLAINS_FLOWERS = register("plains_flowers", WamConfiguredFeatures.PLAINS_FLOWERS, chanceModifiers(20));
        FOREST_TANSY = register("forest_tansy", WamConfiguredFeatures.FOREST_TANSY,
            cons(
                CountPlacementModifier.of(ClampedIntProvider.create(UniformIntProvider.create(-4, 1), 0, 1)),
                chanceModifiers(7)
            )
        );
        TAIGA_HEATHER_PATCH = register("taiga_heather_patch", WamConfiguredFeatures.HEATHER_PATCH,
            cons(
                CountPlacementModifier.of(ClampedIntProvider.create(UniformIntProvider.create(-4, 1), 0, 1)),
                chanceModifiers(7)
            )
        );
    }

    private WamPlacedFeatures() {
    }

    public static void register() {
    }

    private static RegistryEntry<PlacedFeature> register(String id, RegistryEntry<? extends ConfiguredFeature<?, ?>> feature, List<PlacementModifier> modifiers) {
        return PLACED_FEATURES.add(PlacedFeatures.register(WoodsAndMires.ID + ':' + id, feature, modifiers));
    }
}
