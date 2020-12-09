package juuxel.woodsandmires.feature

import juuxel.woodsandmires.WoodsAndMires
import juuxel.woodsandmires.mixin.TreeDecoratorTypeAccessor
import net.minecraft.util.registry.Registry
import net.minecraft.world.biome.GenerationSettings
import net.minecraft.world.gen.GenerationStep
import net.minecraft.world.gen.feature.DefaultFeatureConfig
import net.minecraft.world.gen.feature.Feature
import net.minecraft.world.gen.feature.SingleStateFeatureConfig
import net.minecraft.world.gen.tree.TreeDecoratorType

object WamFeatures {
    val BRANCH_TREE_DECORATOR: TreeDecoratorType<BranchTreeDecorator> =
        TreeDecoratorTypeAccessor.construct(BranchTreeDecorator.CODEC)

    val SHRUB: Feature<ShrubFeatureConfig> = ShrubFeature(ShrubFeatureConfig.CODEC)
    val MIRE_PONDS: Feature<DefaultFeatureConfig> = MirePondsFeature(DefaultFeatureConfig.CODEC)
    val MEADOW: Feature<MeadowFeatureConfig> = MeadowFeature(MeadowFeatureConfig.CODEC)
    val FELL_LAKE: Feature<SingleStateFeatureConfig> = FellLakeFeature(SingleStateFeatureConfig.CODEC)

    fun init() {
        register("shrub", SHRUB)
        register("mire_ponds", MIRE_PONDS)
        register("meadow", MEADOW)
        register("fell_lake", FELL_LAKE)

        Registry.register(Registry.TREE_DECORATOR_TYPE, WoodsAndMires.id("branch"), BRANCH_TREE_DECORATOR)
    }

    private fun register(id: String, feature: Feature<*>) {
        Registry.register(Registry.FEATURE, WoodsAndMires.id(id), feature)
    }

    fun addFlowers(builder: GenerationSettings.Builder) {
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, WamConfiguredFeatures.PLAINS_FLOWERS)
    }
}
