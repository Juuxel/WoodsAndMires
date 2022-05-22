package juuxel.woodsandmires.feature;

import com.google.common.collect.ImmutableList;
import juuxel.woodsandmires.WoodsAndMires;
import juuxel.woodsandmires.biome.WamBiomes;
import juuxel.woodsandmires.block.WamBlocks;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalBiomeTags;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.intprovider.ClampedIntProvider;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.feature.PlacedFeatures;
import net.minecraft.world.gen.feature.TreeConfiguredFeatures;
import net.minecraft.world.gen.feature.VegetationPlacedFeatures;
import net.minecraft.world.gen.placementmodifier.BiomePlacementModifier;
import net.minecraft.world.gen.placementmodifier.CountPlacementModifier;
import net.minecraft.world.gen.placementmodifier.PlacementModifier;
import net.minecraft.world.gen.placementmodifier.RarityFilterPlacementModifier;
import net.minecraft.world.gen.placementmodifier.SquarePlacementModifier;

import java.util.List;

public final class WamPlacedFeatures {
    private static List<PlacementModifier> cons(PlacementModifier head, List<PlacementModifier> tail) {
        return ImmutableList.<PlacementModifier>builder().add(head).addAll(tail).build();
    }

    private static List<PlacementModifier> treeModifiers(PlacementModifier countModifier) {
        return VegetationPlacedFeatures.modifiers(countModifier);
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
    public static final RegistryEntry<PlacedFeature> OLD_GROWTH_FOREST_PINE;
    public static final RegistryEntry<PlacedFeature> GIANT_PINE;
    public static final RegistryEntry<PlacedFeature> PINE_FOREST_BOULDER;

    static {
        FOREST_PINE = register("forest_pine", WamConfiguredFeatures.PINE, treeModifiersWithWouldSurvive(CountPlacementModifier.of(10), WamBlocks.PINE_SAPLING));
        SNOWY_FOREST_PINE = register("snowy_forest_pine", WamConfiguredFeatures.PINE, treeModifiersWithWouldSurvive(CountPlacementModifier.of(2), WamBlocks.PINE_SAPLING));
        OLD_GROWTH_FOREST_PINE = register("old_growth_forest_pine", WamConfiguredFeatures.PINE, treeModifiersWithWouldSurvive(CountPlacementModifier.of(4), WamBlocks.PINE_SAPLING));
        GIANT_PINE = register("giant_pine", WamConfiguredFeatures.GIANT_PINE, treeModifiersWithWouldSurvive(CountPlacementModifier.of(2), WamBlocks.PINE_SAPLING));
        PINE_FOREST_BOULDER = register("pine_forest_boulder", WamConfiguredFeatures.PINE_FOREST_BOULDER, chanceModifiers(16));
    }

    // Mire
    public static final RegistryEntry<PlacedFeature> MIRE_PONDS;
    public static final RegistryEntry<PlacedFeature> MIRE_FLOWERS;
    public static final RegistryEntry<PlacedFeature> MIRE_MEADOW;
    public static final RegistryEntry<PlacedFeature> MIRE_PINE_SNAG;
    public static final RegistryEntry<PlacedFeature> MIRE_PINE_SHRUB;

    static {
        MIRE_PONDS = register("mire_ponds", WamConfiguredFeatures.MIRE_PONDS, List.of());
        MIRE_FLOWERS = register("mire_flowers", WamConfiguredFeatures.MIRE_FLOWERS, chanceModifiers(2));
        MIRE_MEADOW = register("mire_meadow", WamConfiguredFeatures.MIRE_MEADOW, List.of());
        MIRE_PINE_SNAG = register("mire_pine_snag", WamConfiguredFeatures.PINE_SNAG, treeModifiersWithWouldSurvive(RarityFilterPlacementModifier.of(6), WamBlocks.PINE_SAPLING));
        MIRE_PINE_SHRUB = register("mire_pine_shrub", WamConfiguredFeatures.SHORT_PINE_SHRUB,
            treeModifiersWithWouldSurvive(
                PlacedFeatures.createCountExtraModifier(3, 1/3f, 3),
                WamBlocks.PINE_SAPLING
            )
        );
    }

    // Clearings
    public static final RegistryEntry<PlacedFeature> CLEARING_MEADOW;
    public static final RegistryEntry<PlacedFeature> CLEARING_BIRCH;
    public static final RegistryEntry<PlacedFeature> CLEARING_FLOWERS;
    public static final RegistryEntry<PlacedFeature> CLEARING_SNAG;
    public static final RegistryEntry<PlacedFeature> CLEARING_PINE_SHRUB;
    public static final RegistryEntry<PlacedFeature> CLEARING_FALLEN_PINE;

    static {
        CLEARING_MEADOW = register("clearing_meadow", WamConfiguredFeatures.CLEARING_MEADOW, List.of());
        CLEARING_BIRCH = register("clearing_birch", TreeConfiguredFeatures.BIRCH_BEES_005,
            treeModifiersWithWouldSurvive(RarityFilterPlacementModifier.of(3), Blocks.BIRCH_SAPLING)
        );
        CLEARING_FLOWERS = register("clearing_flowers", WamConfiguredFeatures.PLAINS_FLOWERS, chanceModifiers(2));
        CLEARING_SNAG = register("clearing_snag", WamConfiguredFeatures.PINE_SNAG, treeModifiersWithWouldSurvive(RarityFilterPlacementModifier.of(2), WamBlocks.PINE_SAPLING));
        CLEARING_PINE_SHRUB = register("clearing_pine_shrub", WamConfiguredFeatures.CLEARING_PINE_SHRUB,
            treeModifiersWithWouldSurvive(
                PlacedFeatures.createCountExtraModifier(4, 1/3f, 3),
                WamBlocks.PINE_SAPLING
            )
        );
        CLEARING_FALLEN_PINE = register("clearing_fallen_pine", WamConfiguredFeatures.CLEARING_FALLEN_PINE, chanceModifiers(3));
    }

    // Fells
    public static final RegistryEntry<PlacedFeature> FELL_VEGETATION;
    public static final RegistryEntry<PlacedFeature> FELL_BOULDER;
    public static final RegistryEntry<PlacedFeature> FELL_POND;
    public static final RegistryEntry<PlacedFeature> FELL_BIRCH_SHRUB;

    static {
        FELL_VEGETATION = register("fell_vegetation", WamConfiguredFeatures.FELL_VEGETATION, List.of());
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
    }

    // Vanilla biomes
    public static final RegistryEntry<PlacedFeature> PLAINS_FLOWERS;
    public static final RegistryEntry<PlacedFeature> FOREST_TANSY;

    static {
        PLAINS_FLOWERS = register("plains_flowers", WamConfiguredFeatures.PLAINS_FLOWERS, chanceModifiers(20));
        FOREST_TANSY = register("forest_tansy", WamConfiguredFeatures.FOREST_TANSY,
            cons(
                CountPlacementModifier.of(ClampedIntProvider.create(UniformIntProvider.create(-4, 1), 0, 1)),
                chanceModifiers(7)
            )
        );
    }

    private WamPlacedFeatures() {
    }

    public static void init() {
        BiomeModifications.addFeature(
            context -> context.getBiomeKey() == BiomeKeys.PLAINS,
            GenerationStep.Feature.VEGETAL_DECORATION,
            PLAINS_FLOWERS.getKey().get()
        );

        BiomeModifications.addFeature(
            context -> context.hasTag(ConventionalBiomeTags.FOREST) && !WoodsAndMires.ID.equals(context.getBiomeKey().getValue().getNamespace()),
            GenerationStep.Feature.VEGETAL_DECORATION,
            FOREST_TANSY.getKey().get()
        );
    }

    private static RegistryEntry<PlacedFeature> register(String id, RegistryEntry<? extends ConfiguredFeature<?, ?>> feature, List<PlacementModifier> modifiers) {
        return PlacedFeatures.register(WoodsAndMires.ID + ':' + id, feature, modifiers);
    }
}
