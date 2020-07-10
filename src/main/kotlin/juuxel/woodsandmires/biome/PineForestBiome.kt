package juuxel.woodsandmires.biome

import juuxel.woodsandmires.feature.WamConfiguredFeatures
import net.minecraft.world.gen.GenerationStep
import net.minecraft.world.gen.decorator.CountExtraChanceDecoratorConfig
import net.minecraft.world.gen.decorator.Decorator
import net.minecraft.world.gen.decorator.DecoratorConfig

class PineForestBiome(config: Settings.() -> Unit) : AbstractPineForestBiome(config) {
    init {
        addFeature(
            GenerationStep.Feature.VEGETAL_DECORATION,
            WamConfiguredFeatures.PINE
                .method_30374(
                    Decorator.COUNT_EXTRA.configure(CountExtraChanceDecoratorConfig(10, 0.1f, 1))
                        .method_30371()
                        .method_30374(Decorator.HEIGHTMAP.configure(DecoratorConfig.DEFAULT))
                )
        )
    }
}
