package juuxel.woodsandmires.data.builtin;

import juuxel.woodsandmires.WoodsAndMires;
import juuxel.woodsandmires.block.WamBlocks;
import juuxel.woodsandmires.feature.FallenLogFeatureConfig;
import juuxel.woodsandmires.feature.FellPondFeatureConfig;
import juuxel.woodsandmires.feature.FrozenTreasureFeatureConfig;
import juuxel.woodsandmires.feature.MeadowFeatureConfig;
import juuxel.woodsandmires.feature.ShrubFeatureConfig;
import juuxel.woodsandmires.feature.WamConfiguredFeatureKeys;
import juuxel.woodsandmires.feature.WamFeatures;
import juuxel.woodsandmires.tree.BranchTreeDecorator;
import juuxel.woodsandmires.tree.AgedTrunkTreeDecorator;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DataPool;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.VerticalSurfaceType;
import net.minecraft.util.math.floatprovider.UniformFloatProvider;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.gen.blockpredicate.BlockPredicate;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.ConfiguredFeatures;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.feature.PlacedFeatures;
import net.minecraft.world.gen.feature.RandomFeatureConfig;
import net.minecraft.world.gen.feature.RandomFeatureEntry;
import net.minecraft.world.gen.feature.SimpleBlockFeatureConfig;
import net.minecraft.world.gen.feature.SimpleRandomFeatureConfig;
import net.minecraft.world.gen.feature.SingleStateFeatureConfig;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.feature.TreePlacedFeatures;
import net.minecraft.world.gen.feature.VegetationConfiguredFeatures;
import net.minecraft.world.gen.feature.VegetationPatchFeatureConfig;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.foliage.BlobFoliagePlacer;
import net.minecraft.world.gen.foliage.PineFoliagePlacer;
import net.minecraft.world.gen.placementmodifier.BlockFilterPlacementModifier;
import net.minecraft.world.gen.placementmodifier.EnvironmentScanPlacementModifier;
import net.minecraft.world.gen.placementmodifier.PlacementModifier;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.stateprovider.WeightedBlockStateProvider;
import net.minecraft.world.gen.treedecorator.AlterGroundTreeDecorator;
import net.minecraft.world.gen.treedecorator.TreeDecorator;
import net.minecraft.world.gen.trunk.ForkingTrunkPlacer;
import net.minecraft.world.gen.trunk.GiantTrunkPlacer;
import net.minecraft.world.gen.trunk.StraightTrunkPlacer;

import java.util.ArrayList;
import java.util.List;

public final class WamConfiguredFeatures {
    private final Registerable<ConfiguredFeature<?, ?>> registerable;
    private final RegistryEntryLookup<PlacedFeature> placedFeatures;

    // Individual features
    private RegistryEntry<ConfiguredFeature<?, ?>> noPodzolPine;

    private WamConfiguredFeatures(Registerable<ConfiguredFeature<?, ?>> registerable) {
        this.registerable = registerable;
        this.placedFeatures = registerable.getRegistryLookup(RegistryKeys.PLACED_FEATURE);
    }

    private void registerGeneral() {
        register(WamConfiguredFeatureKeys.SHORT_PINE_SHRUB, WamFeatures.SHRUB,
            new ShrubFeatureConfig(
                WamBlocks.PINE_LOG.getDefaultState(),
                WamBlocks.PINE_LEAVES.getDefaultState(),
                1, 2, 0.6f
            )
        );
        var thinPineShrub = register(WamConfiguredFeatureKeys.THIN_PINE_SHRUB, WamFeatures.SHRUB,
            new ShrubFeatureConfig(
                WamBlocks.PINE_SHRUB_LOG.getDefaultState(),
                WamBlocks.PINE_LEAVES.getDefaultState(),
                1, 2, 0.8f
            )
        );
        var pine = register(WamConfiguredFeatureKeys.PINE, Feature.TREE, pineTree(1, 1));
        var giantPine = register(WamConfiguredFeatureKeys.GIANT_PINE, Feature.TREE,
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
                        new AgedTrunkTreeDecorator(WamBlocks.AGED_PINE_LOG, UniformFloatProvider.create(0.5f, 0.85f)),
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
        var pineSnag = register(WamConfiguredFeatureKeys.PINE_SNAG, Feature.TREE,
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
        register(WamConfiguredFeatureKeys.PLAINS_FLOWERS, Feature.SIMPLE_RANDOM_SELECTOR,
            new SimpleRandomFeatureConfig(
                RegistryEntryList.of(
                    createFlowerPatchFeature(WamBlocks.FIREWEED),
                    createFlowerPatchFeature(WamBlocks.TANSY)
                )
            )
        );
        register(WamConfiguredFeatureKeys.PINE_FROM_SAPLING, Feature.TREE,
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
        register(WamConfiguredFeatureKeys.PINE_FOREST_BOULDER, Feature.FOREST_ROCK,
            new SingleStateFeatureConfig(Blocks.STONE.getDefaultState())
        );
        register(WamConfiguredFeatureKeys.FOREST_TANSY, Feature.RANDOM_PATCH,
            ConfiguredFeatures.createRandomPatchFeatureConfig(
                Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(BlockStateProvider.of(WamBlocks.TANSY))
            )
        );
        register(WamConfiguredFeatureKeys.HEATHER_PATCH, Feature.RANDOM_PATCH,
            ConfiguredFeatures.createRandomPatchFeatureConfig(
                Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(BlockStateProvider.of(WamBlocks.HEATHER))
            )
        );
        var lessPodzolPine = register(WamConfiguredFeatureKeys.LESS_PODZOL_PINE, Feature.TREE, pineTree(3, 1));
        noPodzolPine = register(WamConfiguredFeatureKeys.NO_PODZOL_PINE, Feature.TREE, pineTree(1, 0));
        register(WamConfiguredFeatureKeys.LUSH_PINE_FOREST_TREES, Feature.RANDOM_SELECTOR,
            new RandomFeatureConfig(
                List.of(
                    new RandomFeatureEntry(
                        PlacedFeatures.createEntry(
                            thinPineShrub,
                            PlacedFeatures.wouldSurvive(WamBlocks.PINE_SAPLING)
                        ),
                        0.12f
                    ),
                    new RandomFeatureEntry(placedFeatures.getOrThrow(TreePlacedFeatures.BIRCH_BEES_0002), 0.1f),
                    new RandomFeatureEntry(placedFeatures.getOrThrow(TreePlacedFeatures.OAK_BEES_0002), 0.1f),
                    new RandomFeatureEntry(placedFeatures.getOrThrow(TreePlacedFeatures.FANCY_OAK_BEES_0002), 0.1f)
                ),
                PlacedFeatures.createEntry(lessPodzolPine, PlacedFeatures.wouldSurvive(WamBlocks.PINE_SAPLING))
            )
        );
        register(WamConfiguredFeatureKeys.FALLEN_PINE, WamFeatures.FALLEN_LOG,
            new FallenLogFeatureConfig(
                WamBlocks.PINE_LOG,
                WamBlocks.AGED_PINE_LOG,
                UniformFloatProvider.create(-0.2f, 1.2f),
                UniformIntProvider.create(2, 6),
                new WeightedBlockStateProvider(
                    DataPool.<BlockState>builder()
                        .add(Blocks.AIR.getDefaultState(), 3)
                        .add(Blocks.BROWN_MUSHROOM.getDefaultState(), 1)
                        .add(Blocks.RED_MUSHROOM.getDefaultState(), 1)
                        .build()
                )
            )
        );
        register(WamConfiguredFeatureKeys.OLD_GROWTH_PINE_FOREST_TREES, Feature.RANDOM_SELECTOR,
            new RandomFeatureConfig(
                List.of(
                    new RandomFeatureEntry(
                        PlacedFeatures.createEntry(
                            pine,
                            PlacedFeatures.wouldSurvive(WamBlocks.PINE_SAPLING)
                        ),
                        0.15f
                    ),
                    new RandomFeatureEntry(
                        PlacedFeatures.createEntry(
                            pineSnag,
                            PlacedFeatures.wouldSurvive(WamBlocks.PINE_SAPLING)
                        ),
                        0.1f
                    )
                ),
                PlacedFeatures.createEntry(giantPine, PlacedFeatures.wouldSurvive(WamBlocks.PINE_SAPLING))
            )
        );
    }

    private static TreeFeatureConfig pineTree(int grassWeight, int podzolWeight) {
        List<TreeDecorator> decorators = new ArrayList<>();
        decorators.add(new AgedTrunkTreeDecorator(WamBlocks.AGED_PINE_LOG, UniformFloatProvider.create(0.3f, 0.65f)));

        if (podzolWeight > 0) {
            decorators.add(
                new AlterGroundTreeDecorator(
                    new WeightedBlockStateProvider(
                        DataPool.<BlockState>builder()
                            .add(Blocks.GRASS_BLOCK.getDefaultState(), grassWeight)
                            .add(Blocks.PODZOL.getDefaultState(), podzolWeight)
                    )
                )
            );
        }

        return new TreeFeatureConfig.Builder(
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
            .decorators(List.copyOf(decorators))
            .build();
    }

    private void registerMires() {
        register(WamConfiguredFeatureKeys.MIRE_PONDS, WamFeatures.MIRE_PONDS);
        register(WamConfiguredFeatureKeys.MIRE_FLOWERS, Feature.FLOWER,
            VegetationConfiguredFeatures.createRandomPatchFeatureConfig(
                new WeightedBlockStateProvider(
                    DataPool.<BlockState>builder()
                        .add(Blocks.BLUE_ORCHID.getDefaultState(), 1)
                        .add(WamBlocks.TANSY.getDefaultState(), 1)
                ),
                64
            )
        );
        register(WamConfiguredFeatureKeys.MIRE_MEADOW, WamFeatures.MEADOW,
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

    // Fells
    public static final Identifier FROZEN_TREASURE_LOOT_TABLE = WoodsAndMires.id("chests/frozen_treasure");

    private void registerFells() {
        register(WamConfiguredFeatureKeys.FELL_VEGETATION, WamFeatures.MEADOW,
            new MeadowFeatureConfig(
                BlockStateProvider.of(Blocks.GRASS),
                0.3f
            )
        );
        register(WamConfiguredFeatureKeys.FELL_BOULDER, Feature.FOREST_ROCK,
            new SingleStateFeatureConfig(Blocks.COBBLESTONE.getDefaultState())
        );
        register(WamConfiguredFeatureKeys.FELL_POND, WamFeatures.FELL_POND,
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
        register(WamConfiguredFeatureKeys.FELL_BIRCH_SHRUB, WamFeatures.SHRUB,
            new ShrubFeatureConfig(
                Blocks.BIRCH_LOG.getDefaultState(),
                Blocks.BIRCH_LEAVES.getDefaultState(),
                1, 1, 0.7f
            )
        );
        register(WamConfiguredFeatureKeys.FELL_LICHEN, Feature.RANDOM_PATCH,
            ConfiguredFeatures.createRandomPatchFeatureConfig(
                Feature.SIMPLE_BLOCK,
                new SimpleBlockFeatureConfig(BlockStateProvider.of(WamBlocks.FELL_LICHEN)),
                List.of(Blocks.STONE)
            )
        );
        var fellMossPatchVegetation = register(WamConfiguredFeatureKeys.FELL_MOSS_PATCH_VEGETATION, Feature.SIMPLE_BLOCK,
            new SimpleBlockFeatureConfig(
                new WeightedBlockStateProvider(
                    new DataPool.Builder<BlockState>()
                        .add(Blocks.MOSS_CARPET.getDefaultState(), 5)
                        .add(WamBlocks.FELL_LICHEN.getDefaultState(), 2)
                        .add(WamBlocks.HEATHER.getDefaultState(), 1)
                )
            )
        );
        register(WamConfiguredFeatureKeys.FELL_MOSS_PATCH, Feature.VEGETATION_PATCH,
            new VegetationPatchFeatureConfig(
                BlockTags.MOSS_REPLACEABLE,
                BlockStateProvider.of(Blocks.MOSS_BLOCK),
                PlacedFeatures.createEntry(fellMossPatchVegetation),
                VerticalSurfaceType.FLOOR,
                ConstantIntProvider.create(1),
                0f,
                5,
                0.75f,
                UniformIntProvider.create(2, 4),
                0.3f
            )
        );
        register(WamConfiguredFeatureKeys.FROZEN_TREASURE, WamFeatures.FROZEN_TREASURE,
            new FrozenTreasureFeatureConfig(
                new WeightedBlockStateProvider(
                    DataPool.<BlockState>builder()
                        .add(Blocks.PACKED_ICE.getDefaultState(), 3)
                        .add(Blocks.BLUE_ICE.getDefaultState(), 1)
                ),
                UniformIntProvider.create(5, 7),
                ConstantIntProvider.create(2),
                FROZEN_TREASURE_LOOT_TABLE
            )
        );
    }

    private void registerGroves() {
        PlacementModifier[] onSnowModifiers = new PlacementModifier[] {
            EnvironmentScanPlacementModifier.of(
                Direction.UP,
                BlockPredicate.not(BlockPredicate.matchingBlocks(Blocks.POWDER_SNOW)),
                8
            ),
            BlockFilterPlacementModifier.of(
                BlockPredicate.matchingBlocks(Direction.DOWN.getVector(),
                    Blocks.SNOW_BLOCK, Blocks.POWDER_SNOW)
            )
        };

        register(WamConfiguredFeatureKeys.PINY_GROVE_TREES, Feature.RANDOM_SELECTOR,
            new RandomFeatureConfig(
                List.of(
                    new RandomFeatureEntry(placedFeatures.getOrThrow(TreePlacedFeatures.PINE_ON_SNOW), 0.1f),
                    new RandomFeatureEntry(placedFeatures.getOrThrow(TreePlacedFeatures.SPRUCE_ON_SNOW), 0.1f)
                ),
                PlacedFeatures.createEntry(noPodzolPine, onSnowModifiers)
            )
        );
    }

    public static void register(Registerable<ConfiguredFeature<?, ?>> registerable) {
        WamConfiguredFeatures configuredFeatures = new WamConfiguredFeatures(registerable);
        configuredFeatures.registerGeneral();
        configuredFeatures.registerMires();
        configuredFeatures.registerFells();
        configuredFeatures.registerGroves();
    }

    private <FC extends FeatureConfig, F extends Feature<FC>> RegistryEntry<ConfiguredFeature<?, ?>> register(RegistryKey<ConfiguredFeature<?, ?>> key, F feature, FC config) {
        return registerable.register(key, new ConfiguredFeature<>(feature, config));
    }

    private RegistryEntry<ConfiguredFeature<?, ?>> register(RegistryKey<ConfiguredFeature<?, ?>> key, Feature<DefaultFeatureConfig> feature) {
        return register(key, feature, FeatureConfig.DEFAULT);
    }

    private static RegistryEntry<PlacedFeature> createFlowerPatchFeature(Block block) {
        return PlacedFeatures.createEntry(Feature.RANDOM_PATCH, VegetationConfiguredFeatures.createRandomPatchFeatureConfig(BlockStateProvider.of(block), 64));
    }
}
