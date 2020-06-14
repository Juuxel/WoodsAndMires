package juuxel.woodsandmires.biome

import juuxel.woodsandmires.feature.MeadowFeatureConfig
import juuxel.woodsandmires.feature.PineShrubFeatureConfig
import juuxel.woodsandmires.feature.WamFeatures
import net.minecraft.block.Blocks
import net.minecraft.world.gen.GenerationStep
import net.minecraft.world.gen.decorator.CountExtraChanceDecoratorConfig
import net.minecraft.world.gen.decorator.Decorator
import net.minecraft.world.gen.decorator.DecoratorConfig
import net.minecraft.world.gen.feature.DefaultBiomeFeatures
import net.minecraft.world.gen.feature.FeatureConfig
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

        val stateProvider = WeightedBlockStateProvider()
        stateProvider.addState(Blocks.GRASS.defaultState, 5)
        stateProvider.addState(Blocks.FERN.defaultState, 1)

        addFeature(
            GenerationStep.Feature.TOP_LAYER_MODIFICATION,
            WamFeatures.MEADOW.configure(MeadowFeatureConfig(stateProvider, 0.25f))
                .createDecoratedFeature(Decorator.NOPE.configure(DecoratorConfig.DEFAULT))
        )
    }
}
