package juuxel.woodsandmires.biome

import juuxel.woodsandmires.decorator.DecoratorTransformer
import juuxel.woodsandmires.decorator.transform
import juuxel.woodsandmires.feature.PineShrubFeatureConfig
import juuxel.woodsandmires.feature.WamConfiguredFeatures
import juuxel.woodsandmires.feature.WamFeatures
import net.minecraft.class_5464
import net.minecraft.world.gen.GenerationStep
import net.minecraft.world.gen.decorator.ChanceDecoratorConfig
import net.minecraft.world.gen.decorator.CountExtraChanceDecoratorConfig
import net.minecraft.world.gen.decorator.Decorator
import net.minecraft.world.gen.decorator.DecoratorConfig
import net.minecraft.world.gen.feature.DefaultBiomeFeatures
import net.minecraft.world.gen.feature.Feature

class PineForestClearingBiome(config: Settings.() -> Unit) : AbstractPineForestBiome(config) {
    init {
        DefaultBiomeFeatures.addMossyRocks(this)
        DefaultBiomeFeatures.addPlainsFeatures(this)
        DefaultBiomeFeatures.addExtraDefaultFlowers(this)

        addFeature(
            GenerationStep.Feature.VEGETAL_DECORATION,
            WamConfiguredFeatures.TALL_PINE_SHRUB
                .method_30374(
                    Decorator.COUNT_EXTRA.configure(CountExtraChanceDecoratorConfig(4, 0.3f, 3))
                        .transform(
                            DecoratorTransformer.CHUNK_OFFSET,
                            DecoratorTransformer.MOTION_BLOCKING_HEIGHTMAP
                        )
                )
        )

        addFeature(
            GenerationStep.Feature.VEGETAL_DECORATION,
            WamConfiguredFeatures.PINE_SNAG
                .method_30374(
                    Decorator.CHANCE.configure(ChanceDecoratorConfig(2))
                        .transform(
                            DecoratorTransformer.CHUNK_OFFSET,
                            DecoratorTransformer.TOP_SOLID_HEIGHTMAP
                        )
                )
        )

        addFeature(
            GenerationStep.Feature.VEGETAL_DECORATION,
            class_5464.field_26094
                .method_30374(
                    Decorator.CHANCE.configure(ChanceDecoratorConfig(3))
                        .transform(
                            DecoratorTransformer.CHUNK_OFFSET,
                            DecoratorTransformer.TOP_SOLID_HEIGHTMAP
                        )
                )
        )

        addFeature(
            GenerationStep.Feature.TOP_LAYER_MODIFICATION,
            WamConfiguredFeatures.CLEARING_MEADOW
                .method_30374(Decorator.NOPE.configure(DecoratorConfig.DEFAULT))
        )

        addFeature(
            GenerationStep.Feature.VEGETAL_DECORATION,
            WamConfiguredFeatures.PLAINS_FLOWERS
                .method_30374(
                    Decorator.CHANCE.configure(ChanceDecoratorConfig(4))
                        .transform(
                            DecoratorTransformer.CHUNK_OFFSET,
                            DecoratorTransformer.TOP_SOLID_HEIGHTMAP
                        )
                )
        )
    }
}
