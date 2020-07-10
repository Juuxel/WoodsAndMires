package juuxel.woodsandmires.biome

import juuxel.woodsandmires.decorator.DecoratorTransformer
import juuxel.woodsandmires.decorator.transform
import juuxel.woodsandmires.feature.WamConfiguredFeatures
import net.minecraft.world.gen.GenerationStep
import net.minecraft.world.gen.decorator.CountExtraChanceDecoratorConfig
import net.minecraft.world.gen.decorator.Decorator

class PineForestBiome(config: Settings.() -> Unit) : AbstractPineForestBiome(config) {
    init {
        addFeature(
            GenerationStep.Feature.VEGETAL_DECORATION,
            WamConfiguredFeatures.PINE
                .method_30374(
                    Decorator.COUNT_EXTRA.configure(CountExtraChanceDecoratorConfig(10, 0.1f, 1))
                        .transform(
                            DecoratorTransformer.CHUNK_OFFSET,
                            DecoratorTransformer.MOTION_BLOCKING_HEIGHTMAP
                        )
                )
        )
    }
}
