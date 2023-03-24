package juuxel.woodsandmires.data.builtin;

import com.google.common.collect.ImmutableList;
import juuxel.woodsandmires.block.WamBlocks;
import juuxel.woodsandmires.feature.WamConfiguredFeatureKeys;
import juuxel.woodsandmires.feature.WamPlacedFeatureKeys;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.math.intprovider.ClampedIntProvider;
import net.minecraft.util.math.intprovider.UniformIntProvider;
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
    private static List<PlacementModifier> cons(PlacementModifier head, List<PlacementModifier> tail) {
        return ImmutableList.<PlacementModifier>builder().add(head).addAll(tail).build();
    }

    private static List<PlacementModifier> treeModifiers(PlacementModifier countModifier) {
        return VegetationPlacedFeatures.treeModifiers(countModifier);
    }

    private static List<PlacementModifier> countExtraTreeModifiers(int count, float extraChance, int extraCount) {
        return treeModifiers(PlacedFeatures.createCountExtraModifier(count, extraChance, extraCount));
    }

    private static List<PlacementModifier> treeModifiersWithWouldSurvive(PlacementModifier countModifier, Block block) {
        return VegetationPlacedFeatures.treeModifiersWithWouldSurvive(countModifier, block);
    }

    private static List<PlacementModifier> countModifiers(int count) {
        return VegetationPlacedFeatures.modifiers(count);
    }

    private static List<PlacementModifier> chanceModifiers(int chance) {
        return List.of(RarityFilterPlacementModifier.of(chance), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
    }

    private static void registerPineForests(Registerable<PlacedFeature> registerable) {
        register(registerable, WamPlacedFeatureKeys.FOREST_PINE, WamConfiguredFeatureKeys.PINE, treeModifiersWithWouldSurvive(CountPlacementModifier.of(10), WamBlocks.PINE_SAPLING));
        register(registerable, WamPlacedFeatureKeys.SNOWY_PINE_FOREST_TREES, WamConfiguredFeatureKeys.SNOWY_PINE_FOREST_TREES, countExtraTreeModifiers(8, 0.1f, 1));
        register(registerable, WamPlacedFeatureKeys.OLD_GROWTH_PINE_FOREST_TREES, WamConfiguredFeatureKeys.OLD_GROWTH_PINE_FOREST_TREES, countExtraTreeModifiers(10, 0.1f, 1));
        register(registerable, WamPlacedFeatureKeys.PINE_FOREST_BOULDER, WamConfiguredFeatureKeys.PINE_FOREST_BOULDER, chanceModifiers(16));
        register(registerable, WamPlacedFeatureKeys.PINE_FOREST_HEATHER_PATCH, WamConfiguredFeatureKeys.HEATHER_PATCH, chanceModifiers(12));
        register(registerable, WamPlacedFeatureKeys.LUSH_PINE_FOREST_TREES, WamConfiguredFeatureKeys.LUSH_PINE_FOREST_TREES, countExtraTreeModifiers(8, 0.1f, 1));
        register(registerable, WamPlacedFeatureKeys.LUSH_PINE_FOREST_FLOWERS, WamConfiguredFeatureKeys.PLAINS_FLOWERS, chanceModifiers(2));
        register(registerable, WamPlacedFeatureKeys.FALLEN_PINE, WamConfiguredFeatureKeys.FALLEN_PINE, chanceModifiers(7));
    }

    private static void registerMires(Registerable<PlacedFeature> registerable) {
        register(registerable, WamPlacedFeatureKeys.MIRE_PONDS, WamConfiguredFeatureKeys.MIRE_PONDS, List.of(BiomePlacementModifier.of()));
        register(registerable, WamPlacedFeatureKeys.MIRE_FLOWERS, WamConfiguredFeatureKeys.MIRE_FLOWERS, chanceModifiers(2));
        register(registerable, WamPlacedFeatureKeys.MIRE_MEADOW, WamConfiguredFeatureKeys.MIRE_MEADOW, List.of(BiomePlacementModifier.of()));
        register(registerable, WamPlacedFeatureKeys.MIRE_PINE_SNAG, WamConfiguredFeatureKeys.PINE_SNAG, treeModifiersWithWouldSurvive(RarityFilterPlacementModifier.of(6), WamBlocks.PINE_SAPLING));
        register(registerable, WamPlacedFeatureKeys.MIRE_PINE_SHRUB, WamConfiguredFeatureKeys.SHORT_PINE_SHRUB,
            treeModifiersWithWouldSurvive(
                PlacedFeatures.createCountExtraModifier(3, 1 / 3f, 3),
                WamBlocks.PINE_SAPLING
            )
        );
    }

    private static void registerFells(Registerable<PlacedFeature> registerable) {
        register(registerable, WamPlacedFeatureKeys.FELL_VEGETATION, WamConfiguredFeatureKeys.FELL_VEGETATION, List.of(BiomePlacementModifier.of()));
        register(registerable, WamPlacedFeatureKeys.FELL_BOULDER, WamConfiguredFeatureKeys.FELL_BOULDER, chanceModifiers(16));
        register(registerable, WamPlacedFeatureKeys.FELL_POND, WamConfiguredFeatureKeys.FELL_POND, chanceModifiers(7));
        register(registerable, WamPlacedFeatureKeys.FELL_BIRCH_SHRUB, WamConfiguredFeatureKeys.FELL_BIRCH_SHRUB,
            cons(
                RarityFilterPlacementModifier.of(3),
                treeModifiersWithWouldSurvive(
                    PlacedFeatures.createCountExtraModifier(1, 1/3f, 2),
                    Blocks.BIRCH_SAPLING
                )
            )
        );
        register(registerable, WamPlacedFeatureKeys.FELL_LICHEN, WamConfiguredFeatureKeys.FELL_LICHEN, chanceModifiers(2));
        register(registerable, WamPlacedFeatureKeys.FELL_MOSS_PATCH, WamConfiguredFeatureKeys.FELL_MOSS_PATCH, chanceModifiers(5));
        register(registerable, WamPlacedFeatureKeys.FROZEN_TREASURE, WamConfiguredFeatureKeys.FROZEN_TREASURE, countModifiers(2));
        register(registerable, WamPlacedFeatureKeys.FELL_HEATHER_PATCH, WamConfiguredFeatureKeys.HEATHER_PATCH, chanceModifiers(5));
    }

    private static void registerGroves(Registerable<PlacedFeature> registerable) {
        register(registerable, WamPlacedFeatureKeys.PINY_GROVE_TREES, WamConfiguredFeatureKeys.PINY_GROVE_TREES, countExtraTreeModifiers(10, 0.1f, 1));
    }

    private static void registerVanillaBiomes(Registerable<PlacedFeature> registerable) {
        register(registerable, WamPlacedFeatureKeys.PLAINS_FLOWERS, WamConfiguredFeatureKeys.PLAINS_FLOWERS, chanceModifiers(20));
        register(registerable, WamPlacedFeatureKeys.FOREST_TANSY, WamConfiguredFeatureKeys.FOREST_TANSY,
            cons(
                CountPlacementModifier.of(ClampedIntProvider.create(UniformIntProvider.create(-4, 1), 0, 1)),
                chanceModifiers(7)
            )
        );
        register(registerable, WamPlacedFeatureKeys.TAIGA_HEATHER_PATCH, WamConfiguredFeatureKeys.HEATHER_PATCH,
            cons(
                CountPlacementModifier.of(ClampedIntProvider.create(UniformIntProvider.create(-4, 1), 0, 1)),
                chanceModifiers(7)
            )
        );
    }

    private WamPlacedFeatures() {
    }

    public static void register(Registerable<PlacedFeature> registerable) {
        registerPineForests(registerable);
        registerMires(registerable);
        registerFells(registerable);
        registerGroves(registerable);
        registerVanillaBiomes(registerable);
    }

    private static void register(Registerable<PlacedFeature> registerable, RegistryKey<PlacedFeature> key,
                                 RegistryKey<ConfiguredFeature<?, ?>> feature, List<PlacementModifier> modifiers) {
        RegistryEntryLookup<ConfiguredFeature<?, ?>> lookup = registerable.getRegistryLookup(RegistryKeys.CONFIGURED_FEATURE);
        PlacedFeature placed = new PlacedFeature(lookup.getOrThrow(feature), modifiers);
        registerable.register(key, placed);
    }
}
