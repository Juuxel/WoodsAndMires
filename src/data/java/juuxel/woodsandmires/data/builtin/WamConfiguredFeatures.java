package juuxel.woodsandmires.data.builtin;

import juuxel.woodsandmires.WoodsAndMires;
import juuxel.woodsandmires.block.WamBlocks;
import juuxel.woodsandmires.feature.FallenLogFeatureConfig;
import juuxel.woodsandmires.feature.FellPondFeatureConfig;
import juuxel.woodsandmires.feature.FrozenTreasureFeatureConfig;
import juuxel.woodsandmires.feature.MeadowFeatureConfig;
import juuxel.woodsandmires.feature.ShrubFeatureConfig;
import juuxel.woodsandmires.feature.WamFeatures;
import juuxel.woodsandmires.tree.BranchTreeDecorator;
import juuxel.woodsandmires.tree.AgedTrunkTreeDecorator;
import juuxel.woodsandmires.tree.ChanceTreeDecorator;
import juuxel.woodsandmires.tree.PoolTreeDecorator;
import juuxel.woodsandmires.tree.ReplaceTrunkTreeDecorator;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DataPool;
import net.minecraft.util.math.VerticalSurfaceType;
import net.minecraft.util.math.floatprovider.UniformFloatProvider;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryEntryList;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.ConfiguredFeatures;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.feature.PlacedFeatures;
import net.minecraft.world.gen.feature.RandomFeatureConfig;
import net.minecraft.world.gen.feature.RandomFeatureEntry;
import net.minecraft.world.gen.feature.RandomPatchFeatureConfig;
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
    public static final RegistryCollector<RegistryEntry<? extends ConfiguredFeature<?, ?>>> CONFIGURED_FEATURES = new RegistryCollector<>();

    // General
    public static final RegistryEntry<ConfiguredFeature<ShrubFeatureConfig, ?>> SHORT_PINE_SHRUB;
    public static final RegistryEntry<ConfiguredFeature<ShrubFeatureConfig, ?>> THIN_PINE_SHRUB;
    public static final RegistryEntry<ConfiguredFeature<TreeFeatureConfig, ?>> PINE;
    public static final RegistryEntry<ConfiguredFeature<TreeFeatureConfig, ?>> GIANT_PINE;
    public static final RegistryEntry<ConfiguredFeature<TreeFeatureConfig, ?>> PINE_SNAG;
    public static final RegistryEntry<ConfiguredFeature<SimpleRandomFeatureConfig, ?>> PLAINS_FLOWERS;
    public static final RegistryEntry<ConfiguredFeature<TreeFeatureConfig, ?>> PINE_FROM_SAPLING;
    public static final RegistryEntry<ConfiguredFeature<SingleStateFeatureConfig, ?>> PINE_FOREST_BOULDER;
    public static final RegistryEntry<ConfiguredFeature<RandomPatchFeatureConfig, ?>> FOREST_TANSY;
    public static final RegistryEntry<ConfiguredFeature<RandomPatchFeatureConfig, ?>> HEATHER_PATCH;
    public static final RegistryEntry<ConfiguredFeature<TreeFeatureConfig, ?>> LESS_PODZOL_PINE;
    public static final RegistryEntry<ConfiguredFeature<TreeFeatureConfig, ?>> NO_PODZOL_PINE;
    public static final RegistryEntry<ConfiguredFeature<RandomFeatureConfig, ?>> LUSH_PINE_FOREST_TREES;
    public static final RegistryEntry<ConfiguredFeature<FallenLogFeatureConfig, ?>> FALLEN_PINE;
    public static final RegistryEntry<ConfiguredFeature<RandomFeatureConfig, ?>> OLD_GROWTH_PINE_FOREST_TREES;

    static {
        SHORT_PINE_SHRUB = register("short_pine_shrub", WamFeatures.SHRUB,
            new ShrubFeatureConfig(
                WamBlocks.PINE_LOG.getDefaultState(),
                WamBlocks.PINE_LEAVES.getDefaultState(),
                1, 2, 0.6f
            )
        );
        THIN_PINE_SHRUB = register("thin_pine_shrub", WamFeatures.SHRUB,
            new ShrubFeatureConfig(
                WamBlocks.PINE_SHRUB_LOG.getDefaultState(),
                WamBlocks.PINE_LEAVES.getDefaultState(),
                1, 2, 0.8f
            )
        );
        PINE = register("pine", Feature.TREE, pineTree(1, 1));
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
            ).ignoreVines().decorators(List.of(createPineTrunkDecorator())).build()
        );
        PINE_FOREST_BOULDER = register("pine_forest_boulder", Feature.FOREST_ROCK,
            new SingleStateFeatureConfig(Blocks.STONE.getDefaultState())
        );
        FOREST_TANSY = register("forest_tansy", Feature.RANDOM_PATCH,
            ConfiguredFeatures.createRandomPatchFeatureConfig(
                Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(BlockStateProvider.of(WamBlocks.TANSY))
            )
        );
        HEATHER_PATCH = register("heather_patch", Feature.RANDOM_PATCH,
            ConfiguredFeatures.createRandomPatchFeatureConfig(
                Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(BlockStateProvider.of(WamBlocks.HEATHER))
            )
        );
        LESS_PODZOL_PINE = register("less_podzol_pine", Feature.TREE, pineTree(3, 1));
        NO_PODZOL_PINE = register("no_podzol_pine", Feature.TREE, pineTree(1, 0));
        LUSH_PINE_FOREST_TREES = register("lush_pine_forest_trees", Feature.RANDOM_SELECTOR,
            new RandomFeatureConfig(
                List.of(
                    new RandomFeatureEntry(
                        PlacedFeatures.createEntry(
                            THIN_PINE_SHRUB,
                            PlacedFeatures.wouldSurvive(WamBlocks.PINE_SAPLING)
                        ),
                        0.12f
                    ),
                    new RandomFeatureEntry(TreePlacedFeatures.BIRCH_BEES_0002, 0.1f),
                    new RandomFeatureEntry(TreePlacedFeatures.OAK_BEES_0002, 0.1f),
                    new RandomFeatureEntry(TreePlacedFeatures.FANCY_OAK_BEES_0002, 0.1f)
                ),
                PlacedFeatures.createEntry(LESS_PODZOL_PINE, PlacedFeatures.wouldSurvive(WamBlocks.PINE_SAPLING))
            )
        );
        FALLEN_PINE = register("fallen_pine", WamFeatures.FALLEN_LOG,
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
        OLD_GROWTH_PINE_FOREST_TREES = register("old_growth_pine_forest_trees", Feature.RANDOM_SELECTOR,
            new RandomFeatureConfig(
                List.of(
                    new RandomFeatureEntry(
                        PlacedFeatures.createEntry(
                            PINE,
                            PlacedFeatures.wouldSurvive(WamBlocks.PINE_SAPLING)
                        ),
                        0.15f
                    ),
                    new RandomFeatureEntry(
                        PlacedFeatures.createEntry(
                            PINE_SNAG,
                            PlacedFeatures.wouldSurvive(WamBlocks.PINE_SAPLING)
                        ),
                        0.1f
                    )
                ),
                PlacedFeatures.createEntry(GIANT_PINE, PlacedFeatures.wouldSurvive(WamBlocks.PINE_SAPLING))
            )
        );
    }

    private static TreeDecorator createPineTrunkDecorator() {
        DataPool.Builder<TreeDecorator> trunkDecorators = DataPool.builder();
        trunkDecorators.add(
            new ChanceTreeDecorator(
                new AgedTrunkTreeDecorator(WamBlocks.AGED_PINE_LOG, UniformFloatProvider.create(0.3f, 0.65f)),
                0.95
            ),
            14
        );
        trunkDecorators.add(new ReplaceTrunkTreeDecorator(BlockStateProvider.of(WamBlocks.AGED_PINE_LOG)), 1);
        return new PoolTreeDecorator(trunkDecorators.build());
    }

    private static TreeFeatureConfig pineTree(int grassWeight, int podzolWeight) {
        List<TreeDecorator> decorators = new ArrayList<>();
        decorators.add(createPineTrunkDecorator());

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

    // Fells
    public static final RegistryEntry<ConfiguredFeature<MeadowFeatureConfig, ?>> FELL_VEGETATION;
    public static final RegistryEntry<ConfiguredFeature<SingleStateFeatureConfig, ?>> FELL_BOULDER;
    public static final RegistryEntry<ConfiguredFeature<FellPondFeatureConfig, ?>> FELL_POND;
    public static final RegistryEntry<ConfiguredFeature<ShrubFeatureConfig, ?>> FELL_BIRCH_SHRUB;
    public static final RegistryEntry<ConfiguredFeature<RandomPatchFeatureConfig, ?>> FELL_LICHEN;
    public static final RegistryEntry<ConfiguredFeature<SimpleBlockFeatureConfig, ?>> FELL_MOSS_PATCH_VEGETATION;
    public static final RegistryEntry<ConfiguredFeature<VegetationPatchFeatureConfig, ?>> FELL_MOSS_PATCH;
    public static final Identifier FROZEN_TREASURE_LOOT_TABLE = WoodsAndMires.id("chests/frozen_treasure");
    public static final RegistryEntry<ConfiguredFeature<FrozenTreasureFeatureConfig, ?>> FROZEN_TREASURE;

    static {
        FELL_VEGETATION = register("fell_vegetation", WamFeatures.MEADOW,
            new MeadowFeatureConfig(
                BlockStateProvider.of(Blocks.GRASS),
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
        FELL_LICHEN = register("fell_lichen", Feature.RANDOM_PATCH,
            ConfiguredFeatures.createRandomPatchFeatureConfig(
                Feature.SIMPLE_BLOCK,
                new SimpleBlockFeatureConfig(BlockStateProvider.of(WamBlocks.FELL_LICHEN)),
                List.of(Blocks.STONE)
            )
        );
        FELL_MOSS_PATCH_VEGETATION = register("fell_moss_patch_vegetation", Feature.SIMPLE_BLOCK,
            new SimpleBlockFeatureConfig(
                new WeightedBlockStateProvider(
                    new DataPool.Builder<BlockState>()
                        .add(Blocks.MOSS_CARPET.getDefaultState(), 5)
                        .add(WamBlocks.FELL_LICHEN.getDefaultState(), 2)
                        .add(WamBlocks.HEATHER.getDefaultState(), 1)
                )
            )
        );
        FELL_MOSS_PATCH = register("fell_moss_patch", Feature.VEGETATION_PATCH,
            new VegetationPatchFeatureConfig(
                BlockTags.MOSS_REPLACEABLE,
                BlockStateProvider.of(Blocks.MOSS_BLOCK),
                PlacedFeatures.createEntry(FELL_MOSS_PATCH_VEGETATION),
                VerticalSurfaceType.FLOOR,
                ConstantIntProvider.create(1),
                0f,
                5,
                0.75f,
                UniformIntProvider.create(2, 4),
                0.3f
            )
        );
        FROZEN_TREASURE = register("frozen_treasure", WamFeatures.FROZEN_TREASURE,
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

    // Groves
    public static final RegistryEntry<ConfiguredFeature<RandomFeatureConfig, ?>> PINY_GROVE_TREES;

    static {
        PINY_GROVE_TREES = register("piny_grove_trees", Feature.RANDOM_SELECTOR,
            new RandomFeatureConfig(
                List.of(
                    new RandomFeatureEntry(TreePlacedFeatures.PINE_ON_SNOW, 0.1f),
                    new RandomFeatureEntry(TreePlacedFeatures.SPRUCE_ON_SNOW, 0.1f)
                ),
                PlacedFeatures.createEntry(NO_PODZOL_PINE, TreePlacedFeatures.ON_SNOW_MODIFIERS.toArray(PlacementModifier[]::new))
            )
        );
    }

    public static void register() {
    }

    private static <FC extends FeatureConfig, F extends Feature<FC>> RegistryEntry<ConfiguredFeature<FC, ?>> register(String id, F feature, FC config) {
        return CONFIGURED_FEATURES.add(ConfiguredFeatures.register(WoodsAndMires.ID + ':' + id, feature, config));
    }

    private static RegistryEntry<ConfiguredFeature<DefaultFeatureConfig, ?>> register(String id, Feature<DefaultFeatureConfig> feature) {
        return CONFIGURED_FEATURES.add(ConfiguredFeatures.register(WoodsAndMires.ID + ':' + id, feature));
    }

    private static RegistryEntry<PlacedFeature> createFlowerPatchFeature(Block block) {
        return PlacedFeatures.createEntry(Feature.RANDOM_PATCH, VegetationConfiguredFeatures.createRandomPatchFeatureConfig(BlockStateProvider.of(block), 64));
    }
}
