package juuxel.woodsandmires.feature;

import com.google.common.collect.ImmutableList;
import juuxel.woodsandmires.WoodsAndMires;
import juuxel.woodsandmires.block.WamBlocks;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.decorator.BiomePlacementModifier;
import net.minecraft.world.gen.decorator.CountPlacementModifier;
import net.minecraft.world.gen.decorator.PlacementModifier;
import net.minecraft.world.gen.decorator.RarityFilterPlacementModifier;
import net.minecraft.world.gen.decorator.SquarePlacementModifier;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.feature.PlacedFeatures;
import net.minecraft.world.gen.feature.TreeConfiguredFeatures;
import net.minecraft.world.gen.feature.VegetationPlacedFeatures;

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
    public static final PlacedFeature FOREST_PINE;
    public static final PlacedFeature SNOWY_FOREST_PINE;
    public static final PlacedFeature PINE_FOREST_BOULDER;

    static {
        FOREST_PINE = WamConfiguredFeatures.PINE.withPlacement(treeModifiers(CountPlacementModifier.of(10)));
        SNOWY_FOREST_PINE = WamConfiguredFeatures.PINE.withPlacement(treeModifiers(CountPlacementModifier.of(2)));
        PINE_FOREST_BOULDER = WamConfiguredFeatures.PINE_FOREST_BOULDER.withPlacement(chanceModifiers(16));
    }

    // Mire
    public static final PlacedFeature MIRE_PONDS;
    public static final PlacedFeature MIRE_FLOWERS;
    public static final PlacedFeature MIRE_MEADOW;
    public static final PlacedFeature MIRE_PINE_SNAG;
    public static final PlacedFeature MIRE_PINE_SHRUB;

    static {
        MIRE_PONDS = WamConfiguredFeatures.MIRE_PONDS.withPlacement();
        MIRE_FLOWERS = WamConfiguredFeatures.MIRE_FLOWERS.withPlacement(chanceModifiers(2));
        MIRE_MEADOW = WamConfiguredFeatures.MIRE_MEADOW.withPlacement();
        MIRE_PINE_SNAG = WamConfiguredFeatures.PINE_SNAG.withPlacement(treeModifiers(RarityFilterPlacementModifier.of(6)));
        MIRE_PINE_SHRUB = WamConfiguredFeatures.SHORT_PINE_SHRUB.withPlacement(
            treeModifiersWithWouldSurvive(
                PlacedFeatures.createCountExtraModifier(3, 1/3f, 3),
                WamBlocks.PINE_SAPLING
            )
        );
    }

    // Kettle pond
    public static final PlacedFeature KETTLE_POND_PINE_SHRUB;

    static {
        KETTLE_POND_PINE_SHRUB = WamConfiguredFeatures.SHORT_PINE_SHRUB.withPlacement(
            treeModifiersWithWouldSurvive(
                PlacedFeatures.createCountExtraModifier(3, 1/3f, 3),
                WamBlocks.PINE_SAPLING
            )
        );
    }

    // Clearings and plains
    public static final PlacedFeature CLEARING_MEADOW;
    public static final PlacedFeature CLEARING_BIRCH;
    public static final PlacedFeature CLEARING_FLOWERS;
    public static final PlacedFeature CLEARING_SNAG;
    public static final PlacedFeature CLEARING_PINE_SHRUB;
    public static final PlacedFeature PLAINS_FLOWERS;

    static {
        CLEARING_MEADOW = WamConfiguredFeatures.CLEARING_MEADOW.withPlacement();
        CLEARING_BIRCH = TreeConfiguredFeatures.BIRCH_BEES_005.withPlacement(
            treeModifiersWithWouldSurvive(RarityFilterPlacementModifier.of(3), Blocks.BIRCH_SAPLING)
        );
        CLEARING_FLOWERS = WamConfiguredFeatures.PLAINS_FLOWERS.withPlacement(chanceModifiers(4));
        CLEARING_SNAG = WamConfiguredFeatures.PINE_SNAG.withPlacement(treeModifiers(RarityFilterPlacementModifier.of(2)));
        CLEARING_PINE_SHRUB = WamConfiguredFeatures.CLEARING_PINE_SHRUB.withPlacement(
            treeModifiersWithWouldSurvive(
                PlacedFeatures.createCountExtraModifier(4, 1/3f, 3),
                WamBlocks.PINE_SAPLING
            )
        );
        PLAINS_FLOWERS = WamConfiguredFeatures.PLAINS_FLOWERS.withPlacement(chanceModifiers(20));
    }

    // Fells
    public static final PlacedFeature FELL_VEGETATION;
    public static final PlacedFeature FELL_BOULDER;
    public static final PlacedFeature FELL_POND;
    public static final PlacedFeature FELL_BIRCH_SHRUB;

    static {
        FELL_VEGETATION = WamConfiguredFeatures.FELL_VEGETATION.withPlacement();
        FELL_BOULDER = WamConfiguredFeatures.FELL_BOULDER.withPlacement(chanceModifiers(16));
        FELL_POND = WamConfiguredFeatures.FELL_POND.withPlacement(chanceModifiers(3));
        FELL_BIRCH_SHRUB = WamConfiguredFeatures.FELL_BIRCH_SHRUB.withPlacement(
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
        register("forest_pine", FOREST_PINE);
        register("snowy_forest_pine", SNOWY_FOREST_PINE);
        register("pine_forest_boulder", PINE_FOREST_BOULDER);
        register("mire_ponds", MIRE_PONDS);
        register("mire_flowers", MIRE_FLOWERS);
        register("mire_meadow", MIRE_MEADOW);
        register("mire_pine_snag", MIRE_PINE_SNAG);
        register("mire_pine_shrub", MIRE_PINE_SHRUB);
        register("kettle_pond_pine_shrub", KETTLE_POND_PINE_SHRUB);
        register("clearing_meadow", CLEARING_MEADOW);
        register("clearing_birch", CLEARING_BIRCH);
        register("clearing_flowers", CLEARING_FLOWERS);
        register("clearing_snag", CLEARING_SNAG);
        register("clearing_pine_shrub", CLEARING_PINE_SHRUB);
        register("plains_flowers", PLAINS_FLOWERS);
        register("fell_vegetation", FELL_VEGETATION);
        register("fell_boulder", FELL_BOULDER);
        register("fell_pond", FELL_POND);
        register("fell_birch_shrub", FELL_BIRCH_SHRUB);

        BiomeModifications.addFeature(
            context -> context.getBiomeKey() == BiomeKeys.PLAINS,
            GenerationStep.Feature.VEGETAL_DECORATION,
            keyOf(WamPlacedFeatures.PLAINS_FLOWERS)
        );
    }

    private static void register(String id, PlacedFeature feature) {
        Registry.register(BuiltinRegistries.PLACED_FEATURE, WoodsAndMires.id(id), feature);
    }

    private static RegistryKey<PlacedFeature> keyOf(PlacedFeature feature) {
        return BuiltinRegistries.PLACED_FEATURE.getKey(feature).orElseThrow(() -> new NoSuchElementException("Key not found for feature " + feature));
    }
}
