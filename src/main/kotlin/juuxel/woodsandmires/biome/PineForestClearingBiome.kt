package juuxel.woodsandmires.biome

import juuxel.woodsandmires.feature.WamConfiguredFeatures
import net.minecraft.class_5464
import net.minecraft.world.gen.GenerationStep
import net.minecraft.world.gen.decorator.ChanceDecoratorConfig
import net.minecraft.world.gen.decorator.CountExtraChanceDecoratorConfig
import net.minecraft.world.gen.decorator.Decorator
import net.minecraft.world.gen.decorator.DecoratorConfig
import net.minecraft.world.gen.feature.DefaultBiomeFeatures

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
                        .method_30371()
                        .method_30374(Decorator.HEIGHTMAP.configure(DecoratorConfig.DEFAULT))
                )
        )

        addFeature(
            GenerationStep.Feature.VEGETAL_DECORATION,
            WamConfiguredFeatures.PINE_SNAG
                .method_30374(
                    Decorator.CHANCE.configure(ChanceDecoratorConfig(2))
                        .method_30371()
                        .method_30374(Decorator.TOP_SOLID_HEIGHTMAP.configure(DecoratorConfig.DEFAULT))
                )
        )

        addFeature(
            GenerationStep.Feature.VEGETAL_DECORATION,
            class_5464.field_26094
                .method_30374(
                    Decorator.CHANCE.configure(ChanceDecoratorConfig(3))
                        .method_30371()
                        .method_30374(Decorator.TOP_SOLID_HEIGHTMAP.configure(DecoratorConfig.DEFAULT))
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
                        .method_30371()
                        .method_30374(Decorator.TOP_SOLID_HEIGHTMAP.configure(DecoratorConfig.DEFAULT))
                )
        )
    }
}
