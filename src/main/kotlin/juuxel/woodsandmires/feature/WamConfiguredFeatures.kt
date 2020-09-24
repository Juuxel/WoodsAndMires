package juuxel.woodsandmires.feature

import juuxel.woodsandmires.WoodsAndMires
import juuxel.woodsandmires.block.WamBlocks
import net.minecraft.block.Blocks
import net.minecraft.util.registry.BuiltinRegistries
import net.minecraft.util.registry.Registry
import net.minecraft.world.gen.UniformIntDistribution
import net.minecraft.world.gen.decorator.CountExtraDecoratorConfig
import net.minecraft.world.gen.decorator.Decorator
import net.minecraft.world.gen.decorator.DecoratorConfig
import net.minecraft.world.gen.feature.ConfiguredFeature
import net.minecraft.world.gen.feature.ConfiguredFeatures
import net.minecraft.world.gen.feature.Feature
import net.minecraft.world.gen.feature.FeatureConfig
import net.minecraft.world.gen.feature.RandomPatchFeatureConfig
import net.minecraft.world.gen.feature.SimpleRandomFeatureConfig
import net.minecraft.world.gen.feature.SingleStateFeatureConfig
import net.minecraft.world.gen.feature.TreeFeatureConfig
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize
import net.minecraft.world.gen.foliage.BlobFoliagePlacer
import net.minecraft.world.gen.foliage.PineFoliagePlacer
import net.minecraft.world.gen.placer.DoublePlantPlacer
import net.minecraft.world.gen.placer.SimpleBlockPlacer
import net.minecraft.world.gen.stateprovider.SimpleBlockStateProvider
import net.minecraft.world.gen.stateprovider.WeightedBlockStateProvider
import net.minecraft.world.gen.tree.AlterGroundTreeDecorator
import net.minecraft.world.gen.trunk.ForkingTrunkPlacer
import net.minecraft.world.gen.trunk.StraightTrunkPlacer
import java.util.function.Supplier

object WamConfiguredFeatures {
    private object Undecorated {
        // Flowers
        val FIREWEED: ConfiguredFeature<*, *> = Feature.RANDOM_PATCH.configure(
            RandomPatchFeatureConfig.Builder(
                SimpleBlockStateProvider(WamBlocks.FIREWEED.defaultState),
                DoublePlantPlacer()
            ).tries(64).cannotProject().build()
        )
        val TANSY: ConfiguredFeature<*, *> = Feature.FLOWER.configure(
            RandomPatchFeatureConfig.Builder(
                SimpleBlockStateProvider(WamBlocks.TANSY.defaultState),
                SimpleBlockPlacer()
            ).tries(64).cannotProject().build()
        )

        // Pine shrubs
        val SHORT_PINE_SHRUB: ConfiguredFeature<*, *> =
            WamFeatures.PINE_SHRUB.configure(PineShrubFeatureConfig(1, 2, 0.6f))

        // Pine trees
        val PINE: ConfiguredFeature<TreeFeatureConfig, *> = Feature.TREE.configure(
            TreeFeatureConfig.Builder(
                SimpleBlockStateProvider(WamBlocks.PINE_LOG.defaultState),
                SimpleBlockStateProvider(WamBlocks.PINE_LEAVES.defaultState),
                PineFoliagePlacer(
                    UniformIntDistribution.of(1),
                    UniformIntDistribution.of(1),
                    UniformIntDistribution.of(4, 1)
                ),
                StraightTrunkPlacer(6, 4, 0),
                TwoLayersFeatureSize(2, 0, 2)
            )
                .ignoreVines()
                .decorators(
                    listOf(
                        AlterGroundTreeDecorator(
                            WeightedBlockStateProvider()
                                .addState(Blocks.GRASS_BLOCK.defaultState, 1)
                                .addState(Blocks.PODZOL.defaultState, 1)
                        )
                    )
                )
                .build()
        )

        val PINE_SNAG: ConfiguredFeature<TreeFeatureConfig, *> = Feature.TREE.configure(
            TreeFeatureConfig.Builder(
                SimpleBlockStateProvider(WamBlocks.PINE_SNAG_LOG.defaultState),
                SimpleBlockStateProvider(Blocks.AIR.defaultState),
                BlobFoliagePlacer(UniformIntDistribution.of(0), UniformIntDistribution.of(0), 0),
                ForkingTrunkPlacer(4, 4, 0),
                TwoLayersFeatureSize(2, 0, 2)
            ).ignoreVines().decorators(listOf(BranchTreeDecorator(WamBlocks.PINE_SNAG_BRANCH, 0.2f))).build()
        )

        val PLAINS_FLOWERS: ConfiguredFeature<*, *> = Feature.SIMPLE_RANDOM_SELECTOR.configure(
            SimpleRandomFeatureConfig(listOf(FIREWEED.supply(), TANSY.supply()))
        )
    }

    // Generic and *UNDECORATED*
    val PINE_FROM_SAPLING: ConfiguredFeature<TreeFeatureConfig, *> = Feature.TREE.configure(
        TreeFeatureConfig.Builder(
            SimpleBlockStateProvider(WamBlocks.PINE_LOG.defaultState),
            SimpleBlockStateProvider(WamBlocks.PINE_LEAVES.defaultState),
            PineFoliagePlacer(
                UniformIntDistribution.of(1),
                UniformIntDistribution.of(1),
                UniformIntDistribution.of(4, 1)
            ),
            StraightTrunkPlacer(6, 4, 0),
            TwoLayersFeatureSize(2, 0, 2)
        ).ignoreVines().build()
    )

    // @formatter:off
    // Pine forest
    val FOREST_PINE: ConfiguredFeature<*, *> = Undecorated.PINE.decorate(ConfiguredFeatures.Decorators.SQUARE_HEIGHTMAP.repeat(10))
    val PINE_FOREST_BOULDER: ConfiguredFeature<*, *> = Feature.FOREST_ROCK.configure(
        SingleStateFeatureConfig(Blocks.STONE.defaultState)
    ).decorate(ConfiguredFeatures.Decorators.SQUARE_TOP_SOLID_HEIGHTMAP.applyChance(16))

    // Mire
    val MIRE_PONDS: ConfiguredFeature<*, *> = WamFeatures.MIRE_PONDS.configure(FeatureConfig.DEFAULT).decorate(Decorator.NOPE.configure(DecoratorConfig.DEFAULT))
    val MIRE_FLOWERS: ConfiguredFeature<*, *> = Feature.FLOWER.configure(
        RandomPatchFeatureConfig.Builder(
            WeightedBlockStateProvider()
                .addState(Blocks.BLUE_ORCHID.defaultState, 1)
                .addState(WamBlocks.TANSY.defaultState, 1),
            SimpleBlockPlacer()
        ).tries(64).cannotProject().build()
    ).decorate(
        ConfiguredFeatures.Decorators.SPREAD_32_ABOVE
            .decorate(ConfiguredFeatures.Decorators.SQUARE_HEIGHTMAP)
            .repeat(3)
    )

    val MIRE_MEADOW: ConfiguredFeature<*, *> = WamFeatures.MEADOW.configure(
        MeadowFeatureConfig(
            WeightedBlockStateProvider()
                .addState(Blocks.GRASS.defaultState, 5)
                .addState(Blocks.FERN.defaultState, 1),
            0.5f
        )
    ).decorate(Decorator.NOPE.configure(DecoratorConfig.DEFAULT))

    val MIRE_PINE_SNAG: ConfiguredFeature<*, *> = Undecorated.PINE_SNAG.decorate(ConfiguredFeatures.Decorators.SQUARE_TOP_SOLID_HEIGHTMAP.applyChance(6))
    val MIRE_PINE_SHRUB: ConfiguredFeature<*, *> = Undecorated.SHORT_PINE_SHRUB.decorate(ConfiguredFeatures.Decorators.SQUARE_HEIGHTMAP.decorate(Decorator.COUNT_EXTRA.configure(CountExtraDecoratorConfig(3, 0.3f, 3))))

    // Kettle pond
    val KETTLE_POND_PINE_SHRUB: ConfiguredFeature<*, *> = Undecorated.SHORT_PINE_SHRUB.decorate(ConfiguredFeatures.Decorators.SQUARE_HEIGHTMAP.decorate(Decorator.COUNT_EXTRA.configure(CountExtraDecoratorConfig(3, 0.3f, 3))))

    // Clearings and plains
    val CLEARING_MEADOW: ConfiguredFeature<*, *> = WamFeatures.MEADOW.configure(
        MeadowFeatureConfig(
            WeightedBlockStateProvider()
                .addState(Blocks.GRASS.defaultState, 5)
                .addState(Blocks.FERN.defaultState, 1),
            0.25f
        )
    ).decorate(Decorator.NOPE.configure(DecoratorConfig.DEFAULT))
    val CLEARING_BIRCH: ConfiguredFeature<*, *> = ConfiguredFeatures.BIRCH_BEES_005.decorate(ConfiguredFeatures.Decorators.SQUARE_TOP_SOLID_HEIGHTMAP.applyChance(3))
    val CLEARING_FLOWERS: ConfiguredFeature<*, *> = Undecorated.PLAINS_FLOWERS.decorate(ConfiguredFeatures.Decorators.SQUARE_TOP_SOLID_HEIGHTMAP.applyChance(4))
    val CLEARING_SNAG: ConfiguredFeature<*, *> = Undecorated.PINE_SNAG.decorate(ConfiguredFeatures.Decorators.SQUARE_TOP_SOLID_HEIGHTMAP.applyChance(2))
    val CLEARING_PINE_SHRUB: ConfiguredFeature<*, *> = WamFeatures.PINE_SHRUB.configure(PineShrubFeatureConfig(1, 2, 1f))
        .decorate(
            ConfiguredFeatures.Decorators.SQUARE_HEIGHTMAP
                .decorate(Decorator.COUNT_EXTRA.configure(CountExtraDecoratorConfig(4, 0.3f, 3)))
        )
    val PLAINS_FLOWERS: ConfiguredFeature<*, *> = Undecorated.PLAINS_FLOWERS.decorate(ConfiguredFeatures.Decorators.SQUARE_TOP_SOLID_HEIGHTMAP.applyChance(20))

    // @formatter:on

    fun init() {
        register("pine_from_sapling", PINE_FROM_SAPLING)
        register("forest_pine", FOREST_PINE)
        register("pine_forest_boulder", PINE_FOREST_BOULDER)
        register("mire_ponds", MIRE_PONDS)
        register("mire_flowers", MIRE_FLOWERS)
        register("mire_meadow", MIRE_MEADOW)
        register("mire_pine_snag", MIRE_PINE_SNAG)
        register("mire_pine_shrub", MIRE_PINE_SHRUB)
        register("kettle_pond_pine_shrub", KETTLE_POND_PINE_SHRUB)
        register("clearing_meadow", CLEARING_MEADOW)
        register("clearing_birch", CLEARING_BIRCH)
        register("clearing_flowers", CLEARING_FLOWERS)
        register("clearing_snag", CLEARING_SNAG)
        register("clearing_pine_shrub", CLEARING_PINE_SHRUB)
        register("plains_flowers", PLAINS_FLOWERS)
    }

    private fun register(id: String, feature: ConfiguredFeature<*, *>) {
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, WoodsAndMires.id(id), feature)
    }

    /** Converts `this` feature to a supplier of itself. */
    private fun ConfiguredFeature<*, *>.supply(): Supplier<ConfiguredFeature<*, *>> = Supplier { this }
}
