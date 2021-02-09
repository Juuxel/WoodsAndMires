package juuxel.woodsandmires.feature;

import com.google.common.collect.ImmutableList;
import juuxel.woodsandmires.WoodsAndMires;
import juuxel.woodsandmires.block.WamBlocks;
import net.minecraft.block.Blocks;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.UniformIntDistribution;
import net.minecraft.world.gen.decorator.ChanceDecoratorConfig;
import net.minecraft.world.gen.decorator.CountExtraDecoratorConfig;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.decorator.DecoratorConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.ConfiguredFeatures;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.RandomPatchFeatureConfig;
import net.minecraft.world.gen.feature.SimpleRandomFeatureConfig;
import net.minecraft.world.gen.feature.SingleStateFeatureConfig;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.foliage.BlobFoliagePlacer;
import net.minecraft.world.gen.foliage.PineFoliagePlacer;
import net.minecraft.world.gen.placer.DoublePlantPlacer;
import net.minecraft.world.gen.placer.SimpleBlockPlacer;
import net.minecraft.world.gen.stateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.stateprovider.WeightedBlockStateProvider;
import net.minecraft.world.gen.tree.AlterGroundTreeDecorator;
import net.minecraft.world.gen.trunk.ForkingTrunkPlacer;
import net.minecraft.world.gen.trunk.StraightTrunkPlacer;

import java.util.Collections;

public final class WamConfiguredFeatures {
    // Pine forest
    public static final ConfiguredFeature<?, ?> FOREST_PINE;
    public static final ConfiguredFeature<?, ?> PINE_FOREST_BOULDER;

    static {
        FOREST_PINE = Templates.PINE.decorate(ConfiguredFeatures.Decorators.SQUARE_HEIGHTMAP.repeat(10));
        PINE_FOREST_BOULDER = Feature.FOREST_ROCK
            .configure(new SingleStateFeatureConfig(Blocks.STONE.getDefaultState()))
            .decorate(ConfiguredFeatures.Decorators.SQUARE_TOP_SOLID_HEIGHTMAP.applyChance(16));
    }

    // Mire
    public static final ConfiguredFeature<?, ?> MIRE_PONDS;
    public static final ConfiguredFeature<?, ?> MIRE_FLOWERS;
    public static final ConfiguredFeature<?, ?> MIRE_MEADOW;
    public static final ConfiguredFeature<?, ?> MIRE_PINE_SNAG;
    public static final ConfiguredFeature<?, ?> MIRE_PINE_SHRUB;

    static {
        MIRE_PONDS = WamFeatures.MIRE_PONDS.configure(FeatureConfig.DEFAULT).decorate(Decorator.NOPE.configure(DecoratorConfig.DEFAULT));

        MIRE_FLOWERS = Feature.FLOWER.configure(
            new RandomPatchFeatureConfig.Builder(
                new WeightedBlockStateProvider()
                    .addState(Blocks.BLUE_ORCHID.getDefaultState(), 1)
                    .addState(WamBlocks.TANSY.getDefaultState(), 1),
                new SimpleBlockPlacer()
            ).tries(64).cannotProject().build()
        ).decorate(
            ConfiguredFeatures.Decorators.SPREAD_32_ABOVE
                .decorate(ConfiguredFeatures.Decorators.SQUARE_HEIGHTMAP)
                .repeat(3)
        );

        MIRE_MEADOW = WamFeatures.MEADOW.configure(
            new MeadowFeatureConfig(
                new WeightedBlockStateProvider()
                    .addState(Blocks.GRASS.getDefaultState(), 5)
                    .addState(Blocks.FERN.getDefaultState(), 1),
                0.5f
            )
        ).decorate(Decorator.NOPE.configure(DecoratorConfig.DEFAULT));

        MIRE_PINE_SNAG = Templates.PINE_SNAG.decorate(ConfiguredFeatures.Decorators.SQUARE_TOP_SOLID_HEIGHTMAP.applyChance(6));
        MIRE_PINE_SHRUB = Templates.SHORT_PINE_SHRUB
            .decorate(ConfiguredFeatures.Decorators.SQUARE_HEIGHTMAP
                .decorate(Decorator.COUNT_EXTRA.configure(new CountExtraDecoratorConfig(3, 0.3f, 3))));
    }

    // Kettle pond
    public static final ConfiguredFeature<?, ?> KETTLE_POND_PINE_SHRUB;

    static {
        KETTLE_POND_PINE_SHRUB = Templates.SHORT_PINE_SHRUB
            .decorate(ConfiguredFeatures.Decorators.SQUARE_HEIGHTMAP
                .decorate(Decorator.COUNT_EXTRA
                    .configure(new CountExtraDecoratorConfig(3, 0.3f, 3))));
    }

    // Clearings and plains
    public static final ConfiguredFeature<?, ?> CLEARING_MEADOW;
    public static final ConfiguredFeature<?, ?> CLEARING_BIRCH;
    public static final ConfiguredFeature<?, ?> CLEARING_FLOWERS;
    public static final ConfiguredFeature<?, ?> CLEARING_SNAG;
    public static final ConfiguredFeature<?, ?> CLEARING_PINE_SHRUB;
    public static final ConfiguredFeature<?, ?> PLAINS_FLOWERS;

    static {
        CLEARING_MEADOW = WamFeatures.MEADOW.configure(
            new MeadowFeatureConfig(
                new WeightedBlockStateProvider()
                    .addState(Blocks.GRASS.getDefaultState(), 5)
                    .addState(Blocks.FERN.getDefaultState(), 1),
                0.25f
            )
        ).decorate(Decorator.NOPE.configure(DecoratorConfig.DEFAULT));

        CLEARING_BIRCH = ConfiguredFeatures.BIRCH_BEES_005.decorate(ConfiguredFeatures.Decorators.SQUARE_TOP_SOLID_HEIGHTMAP.applyChance(3));
        CLEARING_FLOWERS = Templates.PLAINS_FLOWERS.decorate(ConfiguredFeatures.Decorators.SQUARE_TOP_SOLID_HEIGHTMAP.applyChance(4));
        CLEARING_SNAG = Templates.PINE_SNAG.decorate(ConfiguredFeatures.Decorators.SQUARE_TOP_SOLID_HEIGHTMAP.applyChance(2));
        CLEARING_PINE_SHRUB = WamFeatures.SHRUB.configure(
            new ShrubFeatureConfig(
                WamBlocks.PINE_LOG.getDefaultState(),
                WamBlocks.PINE_LEAVES.getDefaultState(),
                1, 2, 1f
            )
        ).decorate(
            ConfiguredFeatures.Decorators.SQUARE_HEIGHTMAP
                .decorate(Decorator.COUNT_EXTRA.configure(new CountExtraDecoratorConfig(4, 0.3f, 3)))
        );
        PLAINS_FLOWERS = Templates.PLAINS_FLOWERS.decorate(ConfiguredFeatures.Decorators.SQUARE_TOP_SOLID_HEIGHTMAP.applyChance(20));
    }

    public static final ConfiguredFeature<?, ?> FELL_VEGETATION;
    public static final ConfiguredFeature<?, ?> FELL_BOULDER;
    public static final ConfiguredFeature<?, ?> FELL_LAKE;
    public static final ConfiguredFeature<?, ?> FELL_BIRCH_SHRUB;

    static {
        FELL_VEGETATION = WamFeatures.MEADOW.configure(
            new MeadowFeatureConfig(
                new WeightedBlockStateProvider().addState(Blocks.GRASS.getDefaultState(), 1),
                0.3f
            )
        ).decorate(Decorator.NOPE.configure(DecoratorConfig.DEFAULT));

        FELL_BOULDER = Feature.FOREST_ROCK.configure(new SingleStateFeatureConfig(Blocks.COBBLESTONE.getDefaultState()))
            .decorate(ConfiguredFeatures.Decorators.SQUARE_TOP_SOLID_HEIGHTMAP.applyChance(16));

        FELL_LAKE = WamFeatures.FELL_LAKE.configure(new SingleStateFeatureConfig(Blocks.WATER.getDefaultState()))
            .decorate(Decorator.WATER_LAKE.configure(new ChanceDecoratorConfig(4)));

        FELL_BIRCH_SHRUB = WamFeatures.SHRUB.configure(
            new ShrubFeatureConfig(
                Blocks.BIRCH_LOG.getDefaultState(),
                Blocks.BIRCH_LEAVES.getDefaultState(),
                1, 1, 0.7f
            )
        ).decorate(
            ConfiguredFeatures.Decorators.SQUARE_HEIGHTMAP
                .decorate(Decorator.COUNT_EXTRA.configure(new CountExtraDecoratorConfig(1, 0.3f, 2)).applyChance(3))
        );
    }

    private WamConfiguredFeatures() {
    }

    public static void init() {
        register("pine_from_sapling", Undecorated.PINE_FROM_SAPLING);
        register("forest_pine", FOREST_PINE);
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
        register("fell_lake", FELL_LAKE);
        register("fell_birch_shrub", FELL_BIRCH_SHRUB);
    }

    private static void register(String id, ConfiguredFeature<?, ?> feature) {
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, WoodsAndMires.id(id), feature);
    }

    public static final class Undecorated {
        public static final ConfiguredFeature<TreeFeatureConfig, ?> PINE_FROM_SAPLING = Feature.TREE.configure(
            new TreeFeatureConfig.Builder(
                new SimpleBlockStateProvider(WamBlocks.PINE_LOG.getDefaultState()),
                new SimpleBlockStateProvider(WamBlocks.PINE_LEAVES.getDefaultState()),
                new PineFoliagePlacer(
                    UniformIntDistribution.of(1),
                    UniformIntDistribution.of(1),
                    UniformIntDistribution.of(4, 1)
                ),
                new StraightTrunkPlacer(6, 4, 0),
                new TwoLayersFeatureSize(2, 0, 2)
            ).ignoreVines().build()
        );

        private Undecorated() {
        }
    }

    private static final class Templates {
        static final ConfiguredFeature<?, ?> FIREWEED = Feature.RANDOM_PATCH.configure(
            new RandomPatchFeatureConfig.Builder(
                new SimpleBlockStateProvider(WamBlocks.FIREWEED.getDefaultState()),
                new DoublePlantPlacer()
            ).tries(64).cannotProject().build()
        );

        static final ConfiguredFeature<?, ?> TANSY = Feature.FLOWER.configure(
            new RandomPatchFeatureConfig.Builder(
                new SimpleBlockStateProvider(WamBlocks.TANSY.getDefaultState()),
                new SimpleBlockPlacer()
            ).tries(64).cannotProject().build()
        );

        static final ConfiguredFeature<?, ?> SHORT_PINE_SHRUB =
            WamFeatures.SHRUB.configure(
                new ShrubFeatureConfig(
                    WamBlocks.PINE_LOG.getDefaultState(),
                    WamBlocks.PINE_LEAVES.getDefaultState(),
                    1, 2, 0.6f
                )
            );

        static final ConfiguredFeature<TreeFeatureConfig, ?> PINE = Feature.TREE.configure(
            new TreeFeatureConfig.Builder(
                new SimpleBlockStateProvider(WamBlocks.PINE_LOG.getDefaultState()),
                new SimpleBlockStateProvider(WamBlocks.PINE_LEAVES.getDefaultState()),
                new PineFoliagePlacer(
                    UniformIntDistribution.of(1),
                    UniformIntDistribution.of(1),
                    UniformIntDistribution.of(4, 1)
                ),
                new StraightTrunkPlacer(6, 4, 0),
                new TwoLayersFeatureSize(2, 0, 2)
            )
                .ignoreVines()
                .decorators(
                    Collections.singletonList(
                        new AlterGroundTreeDecorator(
                            new WeightedBlockStateProvider()
                                .addState(Blocks.GRASS_BLOCK.getDefaultState(), 1)
                                .addState(Blocks.PODZOL.getDefaultState(), 1)
                        )
                    )
                )
                .build()
        );

        static final ConfiguredFeature<TreeFeatureConfig, ?> PINE_SNAG = Feature.TREE.configure(
            new TreeFeatureConfig.Builder(
                new SimpleBlockStateProvider(WamBlocks.PINE_SNAG_LOG.getDefaultState()),
                new SimpleBlockStateProvider(Blocks.AIR.getDefaultState()),
                new BlobFoliagePlacer(UniformIntDistribution.of(0), UniformIntDistribution.of(0), 0),
                new ForkingTrunkPlacer(4, 4, 0),
                new TwoLayersFeatureSize(2, 0, 2)
            )
                .ignoreVines()
                .decorators(Collections.singletonList(new BranchTreeDecorator(WamBlocks.PINE_SNAG_BRANCH, 0.2f)))
                .build()
        );

        static final ConfiguredFeature<?, ?> PLAINS_FLOWERS = Feature.SIMPLE_RANDOM_SELECTOR.configure(
            new SimpleRandomFeatureConfig(ImmutableList.of(() -> FIREWEED, () -> TANSY))
        );
    }
}
