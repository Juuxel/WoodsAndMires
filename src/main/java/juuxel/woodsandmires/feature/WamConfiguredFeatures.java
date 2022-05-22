package juuxel.woodsandmires.feature;

import juuxel.woodsandmires.WoodsAndMires;
import juuxel.woodsandmires.block.WamBlocks;
import juuxel.woodsandmires.tree.BranchTreeDecorator;
import juuxel.woodsandmires.tree.PineTrunkTreeDecorator;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.collection.DataPool;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryEntryList;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.ConfiguredFeatures;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.feature.PlacedFeatures;
import net.minecraft.world.gen.feature.RandomPatchFeatureConfig;
import net.minecraft.world.gen.feature.SimpleBlockFeatureConfig;
import net.minecraft.world.gen.feature.SimpleRandomFeatureConfig;
import net.minecraft.world.gen.feature.SingleStateFeatureConfig;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.feature.VegetationConfiguredFeatures;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.foliage.BlobFoliagePlacer;
import net.minecraft.world.gen.foliage.PineFoliagePlacer;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.stateprovider.WeightedBlockStateProvider;
import net.minecraft.world.gen.treedecorator.AlterGroundTreeDecorator;
import net.minecraft.world.gen.trunk.ForkingTrunkPlacer;
import net.minecraft.world.gen.trunk.GiantTrunkPlacer;
import net.minecraft.world.gen.trunk.StraightTrunkPlacer;

import java.util.List;

public final class WamConfiguredFeatures {
    // General
    public static final RegistryEntry<ConfiguredFeature<ShrubFeatureConfig, ?>> SHORT_PINE_SHRUB;
    public static final RegistryEntry<ConfiguredFeature<TreeFeatureConfig, ?>> PINE;
    public static final RegistryEntry<ConfiguredFeature<TreeFeatureConfig, ?>> GIANT_PINE;
    public static final RegistryEntry<ConfiguredFeature<TreeFeatureConfig, ?>> PINE_SNAG;
    public static final RegistryEntry<ConfiguredFeature<SimpleRandomFeatureConfig, ?>> PLAINS_FLOWERS;
    public static final RegistryEntry<ConfiguredFeature<TreeFeatureConfig, ?>> PINE_FROM_SAPLING;
    public static final RegistryEntry<ConfiguredFeature<SingleStateFeatureConfig, ?>> PINE_FOREST_BOULDER;
    public static final RegistryEntry<ConfiguredFeature<RandomPatchFeatureConfig, ?>> FOREST_TANSY;

    static {
        SHORT_PINE_SHRUB = register("short_pine_shrub", WamFeatures.SHRUB,
            new ShrubFeatureConfig(
                WamBlocks.PINE_LOG.getDefaultState(),
                WamBlocks.PINE_LEAVES.getDefaultState(),
                1, 2, 0.6f
            )
        );
        PINE = register("pine", Feature.TREE,
            new TreeFeatureConfig.Builder(
                BlockStateProvider.of(WamBlocks.PINE_LOG.getDefaultState()),
                new StraightTrunkPlacer(6, 4, 0),
                BlockStateProvider.of(WamBlocks.PINE_LEAVES.getDefaultState()),
                new PineFoliagePlacer(
                    ConstantIntProvider.create(1),
                    ConstantIntProvider.create(1),
                    UniformIntProvider.create(3, 5)
                ),
                new TwoLayersFeatureSize(2, 0, 2)
            )
                .ignoreVines()
                .decorators(
                    List.of(
                        new PineTrunkTreeDecorator(WamBlocks.GROUND_PINE_LOG),
                        new AlterGroundTreeDecorator(
                            new WeightedBlockStateProvider(
                                DataPool.<BlockState>builder()
                                    .add(Blocks.GRASS_BLOCK.getDefaultState(), 1)
                                    .add(Blocks.PODZOL.getDefaultState(), 1)
                            )
                        )
                    )
                )
                .build()
        );
        GIANT_PINE = register("giant_pine", Feature.TREE,
            new TreeFeatureConfig.Builder(
                BlockStateProvider.of(WamBlocks.PINE_LOG.getDefaultState()),
                new GiantTrunkPlacer(10, 4, 2),
                BlockStateProvider.of(WamBlocks.PINE_LEAVES.getDefaultState()),
                new PineFoliagePlacer(
                    ConstantIntProvider.create(1),
                    ConstantIntProvider.create(1),
                    UniformIntProvider.create(3, 5)
                ),
                new TwoLayersFeatureSize(2, 0, 2)
            )
                .ignoreVines()
                .decorators(
                    List.of(
                        new PineTrunkTreeDecorator(WamBlocks.GROUND_PINE_LOG),
                        new AlterGroundTreeDecorator(
                            new WeightedBlockStateProvider(
                                DataPool.<BlockState>builder()
                                    .add(Blocks.GRASS_BLOCK.getDefaultState(), 1)
                                    .add(Blocks.PODZOL.getDefaultState(), 1)
                            )
                        )
                    )
                )
                .build()
        );
        PINE_SNAG = register("pine_snag", Feature.TREE,
            new TreeFeatureConfig.Builder(
                BlockStateProvider.of(WamBlocks.PINE_SNAG_LOG.getDefaultState()),
                new ForkingTrunkPlacer(4, 4, 0),
                BlockStateProvider.of(Blocks.AIR.getDefaultState()),
                new BlobFoliagePlacer(ConstantIntProvider.create(0), ConstantIntProvider.create(0), 0),
                new TwoLayersFeatureSize(2, 0, 2)
            )
                .ignoreVines()
                .decorators(List.of(new BranchTreeDecorator(WamBlocks.PINE_SNAG_BRANCH, 0.2f)))
                .build()
        );
        PLAINS_FLOWERS = register("plains_flowers", Feature.SIMPLE_RANDOM_SELECTOR,
            new SimpleRandomFeatureConfig(
                RegistryEntryList.of(
                    createFlowerPatchFeature(WamBlocks.FIREWEED),
                    createFlowerPatchFeature(WamBlocks.TANSY)
                )
            )
        );
        PINE_FROM_SAPLING = register("pine_from_sapling", Feature.TREE,
            new TreeFeatureConfig.Builder(
                BlockStateProvider.of(WamBlocks.PINE_LOG.getDefaultState()),
                new StraightTrunkPlacer(6, 4, 0),
                BlockStateProvider.of(WamBlocks.PINE_LEAVES.getDefaultState()),
                new PineFoliagePlacer(
                    ConstantIntProvider.create(1),
                    ConstantIntProvider.create(1),
                    UniformIntProvider.create(3, 5)
                ),
                new TwoLayersFeatureSize(2, 0, 2)
            ).ignoreVines().build()
        );
        PINE_FOREST_BOULDER = register("pine_forest_boulder", Feature.FOREST_ROCK,
            new SingleStateFeatureConfig(Blocks.STONE.getDefaultState())
        );
        FOREST_TANSY = register("forest_tansy", Feature.RANDOM_PATCH,
            ConfiguredFeatures.createRandomPatchFeatureConfig(
                Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(BlockStateProvider.of(WamBlocks.TANSY))
            )
        );
    }

    // Mire
    public static final RegistryEntry<ConfiguredFeature<DefaultFeatureConfig, ?>> MIRE_PONDS;
    public static final RegistryEntry<ConfiguredFeature<RandomPatchFeatureConfig, ?>> MIRE_FLOWERS;
    public static final RegistryEntry<ConfiguredFeature<MeadowFeatureConfig, ?>> MIRE_MEADOW;

    static {
        MIRE_PONDS = register("mire_ponds", WamFeatures.MIRE_PONDS);
        MIRE_FLOWERS = register("mire_flowers", Feature.FLOWER,
            VegetationConfiguredFeatures.createRandomPatchFeatureConfig(
                new WeightedBlockStateProvider(
                    DataPool.<BlockState>builder()
                        .add(Blocks.BLUE_ORCHID.getDefaultState(), 1)
                        .add(WamBlocks.TANSY.getDefaultState(), 1)
                ),
                64
            )
        );
        MIRE_MEADOW = register("mire_meadow", WamFeatures.MEADOW,
            new MeadowFeatureConfig(
                new WeightedBlockStateProvider(
                    DataPool.<BlockState>builder()
                        .add(Blocks.GRASS.getDefaultState(), 5)
                        .add(Blocks.FERN.getDefaultState(), 1)
                ),
                0.5f
            )
        );
    }

    // Clearings
    public static final RegistryEntry<ConfiguredFeature<MeadowFeatureConfig, ?>> CLEARING_MEADOW;
    public static final RegistryEntry<ConfiguredFeature<ShrubFeatureConfig, ?>> CLEARING_PINE_SHRUB;
    public static final RegistryEntry<ConfiguredFeature<FallenLogFeatureConfig, ?>> CLEARING_FALLEN_PINE;

    static {
        CLEARING_MEADOW = register("clearing_meadow", WamFeatures.MEADOW,
            new MeadowFeatureConfig(
                new WeightedBlockStateProvider(
                    DataPool.<BlockState>builder()
                        .add(Blocks.GRASS.getDefaultState(), 5)
                        .add(Blocks.FERN.getDefaultState(), 1)
                ),
                0.25f
            )
        );
        CLEARING_PINE_SHRUB = register("clearing_pine_shrub", WamFeatures.SHRUB,
            new ShrubFeatureConfig(
                WamBlocks.PINE_LOG.getDefaultState(),
                WamBlocks.PINE_LEAVES.getDefaultState(),
                1, 2, 1f
            )
        );
        CLEARING_FALLEN_PINE = register("clearing_fallen_pine", WamFeatures.FALLEN_LOG,
            new FallenLogFeatureConfig(
                WamBlocks.PINE_LOG,
                WamBlocks.GROUND_PINE_LOG,
                UniformIntProvider.create(2, 6)
            )
        );
    }

    // Fells
    public static final RegistryEntry<ConfiguredFeature<MeadowFeatureConfig, ?>> FELL_VEGETATION;
    public static final RegistryEntry<ConfiguredFeature<SingleStateFeatureConfig, ?>> FELL_BOULDER;
    public static final RegistryEntry<ConfiguredFeature<FellPondFeatureConfig, ?>> FELL_POND;
    public static final RegistryEntry<ConfiguredFeature<ShrubFeatureConfig, ?>> FELL_BIRCH_SHRUB;

    static {
        FELL_VEGETATION = register("fell_vegetation", WamFeatures.MEADOW,
            new MeadowFeatureConfig(
                new WeightedBlockStateProvider(DataPool.<BlockState>builder().add(Blocks.GRASS.getDefaultState(), 1)),
                0.3f
            )
        );
        FELL_BOULDER = register("fell_boulder", Feature.FOREST_ROCK,
            new SingleStateFeatureConfig(Blocks.COBBLESTONE.getDefaultState())
        );
        FELL_POND = register("fell_pond", WamFeatures.FELL_POND,
            new FellPondFeatureConfig.Builder()
                .radius(UniformIntProvider.create(2, 5))
                .depth(UniformIntProvider.create(1, 4))
                .fillWith(BlockStateProvider.of(Blocks.WATER))
                .border(BlockStateProvider.of(Blocks.STONE))
                .bottomBlock(
                    new WeightedBlockStateProvider(
                        DataPool.<BlockState>builder()
                            .add(Blocks.EMERALD_ORE.getDefaultState(), 1)
                            .add(Blocks.GOLD_ORE.getDefaultState(), 1)
                    ),
                    0.08f
                )
                .build()
        );
        FELL_BIRCH_SHRUB = register("fell_birch_shrub", WamFeatures.SHRUB,
            new ShrubFeatureConfig(
                Blocks.BIRCH_LOG.getDefaultState(),
                Blocks.BIRCH_LEAVES.getDefaultState(),
                1, 1, 0.7f
            )
        );
    }

    public static void register() {
    }

    private static void register(String id, ConfiguredFeature<?, ?> feature) {
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, WoodsAndMires.id(id), feature);
    }

    private static <FC extends FeatureConfig, F extends Feature<FC>> RegistryEntry<ConfiguredFeature<FC, ?>> register(String id, F feature, FC config) {
        return ConfiguredFeatures.register(WoodsAndMires.ID + ':' + id, feature, config);
    }

    private static RegistryEntry<ConfiguredFeature<DefaultFeatureConfig, ?>> register(String id, Feature<DefaultFeatureConfig> feature) {
        return ConfiguredFeatures.register(WoodsAndMires.ID + ':' + id, feature);
    }

    private static RegistryEntry<PlacedFeature> createFlowerPatchFeature(Block block) {
        return PlacedFeatures.createEntry(Feature.RANDOM_PATCH, VegetationConfiguredFeatures.createRandomPatchFeatureConfig(BlockStateProvider.of(block), 64));
    }
}
