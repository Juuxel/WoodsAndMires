package juuxel.woodsandmires.biome

import juuxel.woodsandmires.feature.MeadowFeatureConfig
import juuxel.woodsandmires.feature.PineShrubFeatureConfig
import juuxel.woodsandmires.feature.WamFeatures
import net.minecraft.block.Blocks
import net.minecraft.world.gen.GenerationStep
import net.minecraft.world.gen.decorator.ChanceDecoratorConfig
import net.minecraft.world.gen.decorator.ChanceTopSolidHeightmapDecorator
import net.minecraft.world.gen.decorator.CountExtraChanceDecoratorConfig
import net.minecraft.world.gen.decorator.Decorator
import net.minecraft.world.gen.decorator.DecoratorConfig
import net.minecraft.world.gen.feature.DefaultBiomeFeatures
import net.minecraft.world.gen.feature.Feature
import net.minecraft.world.gen.stateprovider.WeightedBlockStateProvider

class PineForestClearingBiome(config: Settings.() -> Unit) : AbstractPineForestBiome(config) {
    init {
        DefaultBiomeFeatures.addMossyRocks(this)
        DefaultBiomeFeatures.addPlainsFeatures(this)
        DefaultBiomeFeatures.addExtraDefaultFlowers(this)

        addFeature(
            GenerationStep.Feature.VEGETAL_DECORATION,
            WamFeatures.PINE_SHRUB.configure(PineShrubFeatureConfig(1, 2, 1f))
                .createDecoratedFeature(
                    Decorator.COUNT_EXTRA_HEIGHTMAP.configure(CountExtraChanceDecoratorConfig(4, 0.3f, 3))
                )
        )

        addFeature(
            GenerationStep.Feature.VEGETAL_DECORATION,
            Feature.TREE.configure(WamFeatures.PINE_SNAG_CONFIG)
                .createDecoratedFeature(Decorator.CHANCE_TOP_SOLID_HEIGHTMAP.configure(ChanceDecoratorConfig(2)))
        )

        addFeature(
            GenerationStep.Feature.VEGETAL_DECORATION,
            Feature.TREE.configure(DefaultBiomeFeatures.BIRCH_TREE_WITH_RARE_BEEHIVES_CONFIG)
                .createDecoratedFeature(
                    Decorator.CHANCE_TOP_SOLID_HEIGHTMAP.configure(ChanceDecoratorConfig(3))
                )
        )

        val stateProvider = WeightedBlockStateProvider()
        stateProvider.addState(Blocks.GRASS.defaultState, 5)
        stateProvider.addState(Blocks.FERN.defaultState, 1)

        addFeature(
            GenerationStep.Feature.TOP_LAYER_MODIFICATION,
            WamFeatures.MEADOW.configure(MeadowFeatureConfig(stateProvider, 0.25f))
                .createDecoratedFeature(Decorator.NOPE.configure(DecoratorConfig.DEFAULT))
        )

        addFeature(
            GenerationStep.Feature.VEGETAL_DECORATION,
            Feature.RANDOM_PATCH.configure(WamFeatures.FIREWEED_CONFIG)
                .createDecoratedFeature(Decorator.CHANCE_TOP_SOLID_HEIGHTMAP.configure(ChanceDecoratorConfig(5)))
        )
    }
}
