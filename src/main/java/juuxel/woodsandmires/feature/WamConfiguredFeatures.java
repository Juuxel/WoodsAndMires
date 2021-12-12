package juuxel.woodsandmires.feature;

import juuxel.woodsandmires.WoodsAndMires;
import juuxel.woodsandmires.block.WamBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.collection.DataPool;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.LakeFeature;
import net.minecraft.world.gen.feature.PlacedFeature;
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
import net.minecraft.world.gen.trunk.StraightTrunkPlacer;

import java.util.Collections;
import java.util.List;

public final class WamConfiguredFeatures {
    // General
    public static final ConfiguredFeature<?, ?> SHORT_PINE_SHRUB;
    public static final ConfiguredFeature<TreeFeatureConfig, ?> PINE;
    public static final ConfiguredFeature<TreeFeatureConfig, ?> PINE_SNAG;
    public static final ConfiguredFeature<?, ?> PLAINS_FLOWERS;
    public static final ConfiguredFeature<TreeFeatureConfig, ?> PINE_FROM_SAPLING;
    public static final ConfiguredFeature<?, ?> PINE_FOREST_BOULDER;

    static {
        SHORT_PINE_SHRUB = WamFeatures.SHRUB.configure(
            new ShrubFeatureConfig(
                WamBlocks.PINE_LOG.getDefaultState(),
                WamBlocks.PINE_LEAVES.getDefaultState(),
                1, 2, 0.6f
            )
        );
        PINE = Feature.TREE.configure(
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
                    Collections.singletonList(
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
        PINE_SNAG = Feature.TREE.configure(
            new TreeFeatureConfig.Builder(
                BlockStateProvider.of(WamBlocks.PINE_SNAG_LOG.getDefaultState()),
                new ForkingTrunkPlacer(4, 4, 0),
                BlockStateProvider.of(Blocks.AIR.getDefaultState()),
                new BlobFoliagePlacer(ConstantIntProvider.create(0), ConstantIntProvider.create(0), 0),
                new TwoLayersFeatureSize(2, 0, 2)
            )
                .ignoreVines()
                .decorators(Collections.singletonList(new BranchTreeDecorator(WamBlocks.PINE_SNAG_BRANCH, 0.2f)))
                .build()
        );
        PLAINS_FLOWERS = Feature.SIMPLE_RANDOM_SELECTOR.configure(
            new SimpleRandomFeatureConfig(
                List.of(
                    () -> createFlowerPatchFeature(WamBlocks.FIREWEED),
                    () -> createFlowerPatchFeature(WamBlocks.TANSY)
                )
            )
        );
        PINE_FROM_SAPLING = Feature.TREE.configure(
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
        PINE_FOREST_BOULDER = Feature.FOREST_ROCK.configure(
            new SingleStateFeatureConfig(Blocks.STONE.getDefaultState())
        );
    }

    // Mire
    public static final ConfiguredFeature<?, ?> MIRE_PONDS;
    public static final ConfiguredFeature<?, ?> MIRE_FLOWERS;
    public static final ConfiguredFeature<?, ?> MIRE_MEADOW;

    static {
        MIRE_PONDS = WamFeatures.MIRE_PONDS.configure(FeatureConfig.DEFAULT);
        MIRE_FLOWERS = Feature.FLOWER.configure(
            VegetationConfiguredFeatures.createRandomPatchFeatureConfig(
                new WeightedBlockStateProvider(
                    DataPool.<BlockState>builder()
                        .add(Blocks.BLUE_ORCHID.getDefaultState(), 1)
                        .add(WamBlocks.TANSY.getDefaultState(), 1)
                ),
                64
            )
        );
        MIRE_MEADOW = WamFeatures.MEADOW.configure(
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
    public static final ConfiguredFeature<?, ?> CLEARING_MEADOW;
    public static final ConfiguredFeature<?, ?> CLEARING_PINE_SHRUB;

    static {
        CLEARING_MEADOW = WamFeatures.MEADOW.configure(
            new MeadowFeatureConfig(
                new WeightedBlockStateProvider(
                    DataPool.<BlockState>builder()
                        .add(Blocks.GRASS.getDefaultState(), 5)
                        .add(Blocks.FERN.getDefaultState(), 1)
                ),
                0.25f
            )
        );
        CLEARING_PINE_SHRUB = WamFeatures.SHRUB.configure(
            new ShrubFeatureConfig(
                WamBlocks.PINE_LOG.getDefaultState(),
                WamBlocks.PINE_LEAVES.getDefaultState(),
                1, 2, 1f
            )
        );
    }

    // Fells
    public static final ConfiguredFeature<?, ?> FELL_VEGETATION;
    public static final ConfiguredFeature<?, ?> FELL_BOULDER;
    public static final ConfiguredFeature<?, ?> FELL_LAKE;
    public static final ConfiguredFeature<?, ?> FELL_BIRCH_SHRUB;

    static {
        FELL_VEGETATION = WamFeatures.MEADOW.configure(
            new MeadowFeatureConfig(
                new WeightedBlockStateProvider(DataPool.<BlockState>builder().add(Blocks.GRASS.getDefaultState(), 1)),
                0.3f
            )
        );
        FELL_BOULDER = Feature.FOREST_ROCK.configure(
            new SingleStateFeatureConfig(Blocks.COBBLESTONE.getDefaultState())
        );
        FELL_LAKE = Feature.LAKE.configure(
            new LakeFeature.Config(
                BlockStateProvider.of(Blocks.WATER),
                new WeightedBlockStateProvider(
                    DataPool.<BlockState>builder()
                        .add(Blocks.STONE.getDefaultState(), 58)
                        .add(Blocks.EMERALD_ORE.getDefaultState(), 1)
                        .add(Blocks.GOLD_ORE.getDefaultState(), 1)
                )
            )
        );
        FELL_BIRCH_SHRUB = WamFeatures.SHRUB.configure(
            new ShrubFeatureConfig(
                Blocks.BIRCH_LOG.getDefaultState(),
                Blocks.BIRCH_LEAVES.getDefaultState(),
                1, 1, 0.7f
            )
        );
    }

    public static void register() {
        register("short_pine_shrub", SHORT_PINE_SHRUB);
        register("pine", PINE);
        register("pine_snag", PINE_SNAG);
        register("plains_flowers", PLAINS_FLOWERS);
        register("pine_from_sapling", PINE_FROM_SAPLING);
        register("pine_forest_boulder", PINE_FOREST_BOULDER);
        register("mire_ponds", MIRE_PONDS);
        register("mire_flowers", MIRE_FLOWERS);
        register("mire_meadow", MIRE_MEADOW);
        register("clearing_meadow", CLEARING_MEADOW);
        register("clearing_pine_shrub", CLEARING_PINE_SHRUB);
        register("fell_vegetation", FELL_VEGETATION);
        register("fell_boulder", FELL_BOULDER);
        register("fell_lake", FELL_LAKE);
        register("fell_birch_shrub", FELL_BIRCH_SHRUB);
    }

    private static void register(String id, ConfiguredFeature<?, ?> feature) {
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, WoodsAndMires.id(id), feature);
    }

    private static PlacedFeature createFlowerPatchFeature(Block block) {
        return Feature.RANDOM_PATCH.configure(
            VegetationConfiguredFeatures.createRandomPatchFeatureConfig(BlockStateProvider.of(block), 64)
        ).withPlacement();
    }
}
