package juuxel.woodsandmires.feature

import juuxel.woodsandmires.WoodsAndMires
import juuxel.woodsandmires.mixin.TreeDecoratorTypeAccessor
import net.minecraft.util.registry.Registry
import net.minecraft.world.biome.Biome
import net.minecraft.world.gen.GenerationStep
import net.minecraft.world.gen.decorator.ChanceDecoratorConfig
import net.minecraft.world.gen.decorator.Decorator
import net.minecraft.world.gen.decorator.DecoratorConfig
import net.minecraft.world.gen.feature.DefaultFeatureConfig
import net.minecraft.world.gen.feature.Feature
import net.minecraft.world.gen.tree.TreeDecoratorType

object WamFeatures {
    val BRANCH_TREE_DECORATOR: TreeDecoratorType<BranchTreeDecorator> =
        TreeDecoratorTypeAccessor.construct(BranchTreeDecorator.CODEC)

    val PINE_SHRUB: Feature<PineShrubFeatureConfig> = PineShrubFeature(PineShrubFeatureConfig.CODEC)
    val MIRE_PONDS: Feature<DefaultFeatureConfig> = MirePondsFeature(DefaultFeatureConfig.CODEC)
    val MEADOW: Feature<MeadowFeatureConfig> = MeadowFeature(MeadowFeatureConfig.CODEC)

    fun init() {
        register("pine_shrub", PINE_SHRUB)
        register("mire_ponds", MIRE_PONDS)
        register("meadow", MEADOW)

        Registry.register(Registry.TREE_DECORATOR_TYPE, WoodsAndMires.id("branch"), BRANCH_TREE_DECORATOR)
    }

    private fun register(id: String, feature: Feature<*>) {
        Registry.register(Registry.FEATURE, WoodsAndMires.id(id), feature)
    }

    fun addFlowers(biome: Biome) {
        biome.addFeature(
            GenerationStep.Feature.VEGETAL_DECORATION,
            WamConfiguredFeatures.PLAINS_FLOWERS.decorate(
                Decorator.CHANCE.configure(ChanceDecoratorConfig(20))
                    .spreadHorizontally()
                    .decorate(Decorator.TOP_SOLID_HEIGHTMAP.configure(DecoratorConfig.DEFAULT))
            )
        )
    }
}
