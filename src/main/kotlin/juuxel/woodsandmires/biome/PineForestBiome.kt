package juuxel.woodsandmires.biome

import juuxel.woodsandmires.feature.WamFeatures
import net.minecraft.world.gen.GenerationStep
import net.minecraft.world.gen.decorator.CountExtraChanceDecoratorConfig
import net.minecraft.world.gen.decorator.Decorator
import net.minecraft.world.gen.feature.Feature

class PineForestBiome(config: Settings.() -> Unit) : AbstractPineForestBiome(config) {
    init {
        addFeature(
            GenerationStep.Feature.VEGETAL_DECORATION,
            Feature.TREE.configure(WamFeatures.PINE_TREE_CONFIG)
                .createDecoratedFeature(
                    Decorator.COUNT_EXTRA_HEIGHTMAP.configure(CountExtraChanceDecoratorConfig(10, 0.1f, 1))
                )
        )
    }
}
