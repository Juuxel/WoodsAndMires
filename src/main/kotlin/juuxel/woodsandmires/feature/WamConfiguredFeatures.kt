package juuxel.woodsandmires.feature

import juuxel.woodsandmires.WoodsAndMires
import juuxel.woodsandmires.block.WamBlocks
import net.minecraft.block.Blocks
import net.minecraft.util.registry.BuiltinRegistries
import net.minecraft.util.registry.Registry
import net.minecraft.world.gen.feature.ConfiguredFeature
import net.minecraft.world.gen.feature.Feature
import net.minecraft.world.gen.feature.FeatureConfig
import net.minecraft.world.gen.feature.RandomPatchFeatureConfig
import net.minecraft.world.gen.feature.SimpleRandomFeatureConfig
import net.minecraft.world.gen.placer.SimpleBlockPlacer
import net.minecraft.world.gen.stateprovider.WeightedBlockStateProvider
import java.util.function.Supplier

object WamConfiguredFeatures {
    // @formatter:off
    val FIREWEED = register("fireweed", Feature.RANDOM_PATCH, WamFeatures.FIREWEED_CONFIG)
    val SHORT_PINE_SHRUB = register("short_pine_shrub", WamFeatures.PINE_SHRUB, PineShrubFeatureConfig(1, 2, 0.6f))
    val TALL_PINE_SHRUB = register("tall_pine_shrub", WamFeatures.PINE_SHRUB, PineShrubFeatureConfig(1, 2, 1f))
    val PINE = register("pine", Feature.TREE, WamFeatures.PINE_TREE_CONFIG)
    val PINE_SNAG = register("pine_snag", Feature.TREE, WamFeatures.PINE_SNAG_CONFIG)
    val PINE_SAPLING = register("pine_sapling", Feature.TREE, WamFeatures.PINE_SAPLING_TREE_CONFIG)
    val MIRE_PONDS = register("mire_ponds", WamFeatures.MIRE_PONDS, FeatureConfig.DEFAULT)
    val MIRE_FLOWERS = register(
        "mire_flowers", Feature.FLOWER,
        RandomPatchFeatureConfig.Builder(
            WeightedBlockStateProvider()
                .addState(Blocks.BLUE_ORCHID.defaultState, 1)
                .addState(WamBlocks.TANSY.defaultState, 1),
            SimpleBlockPlacer()
        ).tries(64).cannotProject().build()
    )
    val MIRE_MEADOW = register(
        "mire_meadow", WamFeatures.MEADOW,
        MeadowFeatureConfig(
            WeightedBlockStateProvider()
                .addState(Blocks.GRASS.defaultState, 5)
                .addState(Blocks.FERN.defaultState, 1),
            0.5f
        )
    )
    val CLEARING_MEADOW = register(
        "clearing_meadow", WamFeatures.MEADOW,
        MeadowFeatureConfig(
            WeightedBlockStateProvider()
                .addState(Blocks.GRASS.defaultState, 5)
                .addState(Blocks.FERN.defaultState, 1),
            0.25f
        )
    )
    val PLAINS_FLOWERS = register(
        "plains_flowers", Feature.SIMPLE_RANDOM_SELECTOR,
        SimpleRandomFeatureConfig(
            listOf(
                FIREWEED.supply(),
                Supplier<ConfiguredFeature<*, *>> { Feature.FLOWER.configure(WamFeatures.TANSY_CONFIG) }
            )
        )
    )
    // @formatter:on

    fun init() {
        // NO-OP
    }

    private fun <FC : FeatureConfig> register(
        id: String,
        feature: Feature<FC>, featureConfig: FC
    ): ConfiguredFeature<FC, *> = Registry.register(
        BuiltinRegistries.CONFIGURED_FEATURE,
        WoodsAndMires.id(id),
        feature.configure(featureConfig)
    )

    private fun ConfiguredFeature<*, *>.supply(): Supplier<ConfiguredFeature<*, *>> = Supplier { this }
}
