package juuxel.woodsandmires.feature;

import com.google.common.collect.ImmutableList;
import juuxel.woodsandmires.WoodsAndMires;
import juuxel.woodsandmires.block.WamBlocks;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryKey;
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
import java.util.NoSuchElementException;

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
    public static final RegistryEntry<PlacedFeature> PINE_FOREST_BOULDER;

    static {
        FOREST_PINE = register("forest_pine", WamConfiguredFeatures.PINE, treeModifiers(CountPlacementModifier.of(10)));
        SNOWY_FOREST_PINE = register("snowy_forest_pine", WamConfiguredFeatures.PINE, treeModifiers(CountPlacementModifier.of(2)));
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
        MIRE_PINE_SNAG = register("mire_pine_snag", WamConfiguredFeatures.PINE_SNAG, treeModifiers(RarityFilterPlacementModifier.of(6)));
        MIRE_PINE_SHRUB = register("mire_pine_shrub", WamConfiguredFeatures.SHORT_PINE_SHRUB,
            treeModifiersWithWouldSurvive(
                PlacedFeatures.createCountExtraModifier(3, 1/3f, 3),
                WamBlocks.PINE_SAPLING
            )
        );
    }

    // Clearings and plains
    public static final RegistryEntry<PlacedFeature> CLEARING_MEADOW;
    public static final RegistryEntry<PlacedFeature> CLEARING_BIRCH;
    public static final RegistryEntry<PlacedFeature> CLEARING_FLOWERS;
    public static final RegistryEntry<PlacedFeature> CLEARING_SNAG;
    public static final RegistryEntry<PlacedFeature> CLEARING_PINE_SHRUB;
    public static final RegistryEntry<PlacedFeature> PLAINS_FLOWERS;

    static {
        CLEARING_MEADOW = register("clearing_meadow", WamConfiguredFeatures.CLEARING_MEADOW, List.of());
        CLEARING_BIRCH = register("clearing_birch", TreeConfiguredFeatures.BIRCH_BEES_005,
            treeModifiersWithWouldSurvive(RarityFilterPlacementModifier.of(3), Blocks.BIRCH_SAPLING)
        );
        CLEARING_FLOWERS = register("clearing_flowers", WamConfiguredFeatures.PLAINS_FLOWERS, chanceModifiers(4));
        CLEARING_SNAG = register("clearing_snag", WamConfiguredFeatures.PINE_SNAG, treeModifiers(RarityFilterPlacementModifier.of(2)));
        CLEARING_PINE_SHRUB = register("clearing_pine_shrub", WamConfiguredFeatures.CLEARING_PINE_SHRUB,
            treeModifiersWithWouldSurvive(
                PlacedFeatures.createCountExtraModifier(4, 1/3f, 3),
                WamBlocks.PINE_SAPLING
            )
        );
        PLAINS_FLOWERS = register("plains_flowers", WamConfiguredFeatures.PLAINS_FLOWERS, chanceModifiers(20));
    }

    // Fells
    public static final RegistryEntry<PlacedFeature> FELL_VEGETATION;
    public static final RegistryEntry<PlacedFeature> FELL_BOULDER;
    public static final RegistryEntry<PlacedFeature> FELL_POND;
    public static final RegistryEntry<PlacedFeature> FELL_BIRCH_SHRUB;

    static {
        FELL_VEGETATION = register("fell_vegetation", WamConfiguredFeatures.FELL_VEGETATION, List.of());
        FELL_BOULDER = register("fell_boulder", WamConfiguredFeatures.FELL_BOULDER, chanceModifiers(16));
        FELL_POND = register("fell_pond", WamConfiguredFeatures.FELL_POND, chanceModifiers(3));
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

    private WamPlacedFeatures() {
    }

    public static void init() {
        BiomeModifications.addFeature(
            context -> context.getBiomeKey() == BiomeKeys.PLAINS,
            GenerationStep.Feature.VEGETAL_DECORATION,
            PLAINS_FLOWERS.getKey().get()
        );
    }

    private static RegistryEntry<PlacedFeature> register(String id, RegistryEntry<? extends ConfiguredFeature<?, ?>> feature, List<PlacementModifier> modifiers) {
        return PlacedFeatures.register(WoodsAndMires.ID + ':' + id, feature, modifiers);
    }
}
