package juuxel.woodsandmires.feature

import java.util.function.Supplier
import juuxel.woodsandmires.WoodsAndMires
import juuxel.woodsandmires.block.WamBlocks
import net.minecraft.block.Blocks
import net.minecraft.class_5428
import net.minecraft.util.registry.BuiltinRegistries
import net.minecraft.util.registry.Registry
import net.minecraft.world.gen.decorator.AlterGroundTreeDecorator
import net.minecraft.world.gen.feature.ConfiguredFeature
import net.minecraft.world.gen.feature.Feature
import net.minecraft.world.gen.feature.FeatureConfig
import net.minecraft.world.gen.feature.RandomPatchFeatureConfig
import net.minecraft.world.gen.feature.SimpleRandomFeatureConfig
import net.minecraft.world.gen.feature.TreeFeatureConfig
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize
import net.minecraft.world.gen.foliage.BlobFoliagePlacer
import net.minecraft.world.gen.foliage.PineFoliagePlacer
import net.minecraft.world.gen.placer.DoublePlantPlacer
import net.minecraft.world.gen.placer.SimpleBlockPlacer
import net.minecraft.world.gen.stateprovider.SimpleBlockStateProvider
import net.minecraft.world.gen.stateprovider.WeightedBlockStateProvider
import net.minecraft.world.gen.trunk.ForkingTrunkPlacer
import net.minecraft.world.gen.trunk.StraightTrunkPlacer

object WamConfiguredFeatures {
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
    val SHORT_PINE_SHRUB: ConfiguredFeature<*, *> = WamFeatures.PINE_SHRUB.configure(PineShrubFeatureConfig(1, 2, 0.6f))
    val TALL_PINE_SHRUB: ConfiguredFeature<*, *> = WamFeatures.PINE_SHRUB.configure(PineShrubFeatureConfig(1, 2, 1f))

    // Pine trees
    val PINE: ConfiguredFeature<TreeFeatureConfig, *> = Feature.TREE.configure(
        TreeFeatureConfig.Builder(
            SimpleBlockStateProvider(WamBlocks.PINE_LOG.defaultState),
            SimpleBlockStateProvider(WamBlocks.PINE_LEAVES.defaultState),
            PineFoliagePlacer(class_5428.method_30314(1), class_5428.method_30314(1), class_5428.method_30315(4, 1)),
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
            BlobFoliagePlacer(class_5428.method_30314(0), class_5428.method_30314(0), 0),
            ForkingTrunkPlacer(4, 4, 0),
            TwoLayersFeatureSize(2, 0, 2)
        ).ignoreVines().decorators(listOf(BranchTreeDecorator(WamBlocks.PINE_SNAG_BRANCH, 0.2f))).build()
    )
    val PINE_FROM_SAPLING: ConfiguredFeature<TreeFeatureConfig, *> = Feature.TREE.configure(
        TreeFeatureConfig.Builder(
            SimpleBlockStateProvider(WamBlocks.PINE_LOG.defaultState),
            SimpleBlockStateProvider(WamBlocks.PINE_LEAVES.defaultState),
            PineFoliagePlacer(class_5428.method_30314(1), class_5428.method_30314(1), class_5428.method_30315(4, 1)),
            StraightTrunkPlacer(6, 4, 0),
            TwoLayersFeatureSize(2, 0, 2)
        ).ignoreVines().build()
    )

    // Mire-specific features
    val MIRE_PONDS: ConfiguredFeature<*, *> = WamFeatures.MIRE_PONDS.configure(FeatureConfig.DEFAULT)
    val MIRE_FLOWERS: ConfiguredFeature<*, *> = Feature.FLOWER.configure(
        RandomPatchFeatureConfig.Builder(
            WeightedBlockStateProvider()
                .addState(Blocks.BLUE_ORCHID.defaultState, 1)
                .addState(WamBlocks.TANSY.defaultState, 1),
            SimpleBlockPlacer()
        ).tries(64).cannotProject().build()
    )
    val MIRE_MEADOW: ConfiguredFeature<*, *> = WamFeatures.MEADOW.configure(
        MeadowFeatureConfig(
            WeightedBlockStateProvider()
                .addState(Blocks.GRASS.defaultState, 5)
                .addState(Blocks.FERN.defaultState, 1),
            0.5f
        )
    )

    // Clearings and plains
    val CLEARING_MEADOW: ConfiguredFeature<*, *> = WamFeatures.MEADOW.configure(
        MeadowFeatureConfig(
            WeightedBlockStateProvider()
                .addState(Blocks.GRASS.defaultState, 5)
                .addState(Blocks.FERN.defaultState, 1),
            0.25f
        )
    )
    val PLAINS_FLOWERS: ConfiguredFeature<*, *> = Feature.SIMPLE_RANDOM_SELECTOR.configure(
        SimpleRandomFeatureConfig(listOf(FIREWEED.supply(), TANSY.supply()))
    )

    fun init() {
        register("fireweed", FIREWEED)
        register("tansy", TANSY)
        register("short_pine_shrub", SHORT_PINE_SHRUB)
        register("tall_pine_shrub", TALL_PINE_SHRUB)
        register("pine", PINE)
        register("pine_snag", PINE_SNAG)
        register("pine_from_sapling", PINE_FROM_SAPLING)
        register("mire_ponds", MIRE_PONDS)
        register("mire_flowers", MIRE_FLOWERS)
        register("mire_meadow", MIRE_MEADOW)
        register("clearing_meadow", CLEARING_MEADOW)
        register("plains_flowers", PLAINS_FLOWERS)
    }

    private fun register(id: String, feature: ConfiguredFeature<*, *>) {
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, WoodsAndMires.id(id), feature)
    }

    /** Converts `this` feature to a supplier of itself. */
    private fun ConfiguredFeature<*, *>.supply(): Supplier<ConfiguredFeature<*, *>> = Supplier { this }
}
