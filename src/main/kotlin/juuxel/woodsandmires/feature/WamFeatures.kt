package juuxel.woodsandmires.feature

import juuxel.woodsandmires.WoodsAndMires
import juuxel.woodsandmires.block.WamBlocks
import juuxel.woodsandmires.mixin.TreeDecoratorTypeAccessor
import net.minecraft.block.Blocks
import net.minecraft.class_5428
import net.minecraft.util.registry.Registry
import net.minecraft.world.biome.Biome
import net.minecraft.world.gen.GenerationStep
import net.minecraft.world.gen.decorator.AlterGroundTreeDecorator
import net.minecraft.world.gen.decorator.ChanceDecoratorConfig
import net.minecraft.world.gen.decorator.Decorator
import net.minecraft.world.gen.decorator.DecoratorConfig
import net.minecraft.world.gen.decorator.TreeDecoratorType
import net.minecraft.world.gen.feature.DefaultFeatureConfig
import net.minecraft.world.gen.feature.Feature
import net.minecraft.world.gen.feature.RandomPatchFeatureConfig
import net.minecraft.world.gen.feature.TreeFeatureConfig
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize
import net.minecraft.world.gen.foliage.BlobFoliagePlacer
import net.minecraft.world.gen.foliage.PineFoliagePlacer
import net.minecraft.world.gen.placer.DoublePlantPlacer
import net.minecraft.world.gen.placer.SimpleBlockPlacer
import net.minecraft.world.gen.stateprovider.SimpleBlockStateProvider
import net.minecraft.world.gen.stateprovider.WeightedBlockStateProvider
import net.minecraft.world.gen.trunk.ForkingTrunkPlacer
import net.minecraft.world.gen.trunk.StraightTrunkPlacer

object WamFeatures {
    val BRANCH_TREE_DECORATOR: TreeDecoratorType<BranchTreeDecorator> =
        TreeDecoratorTypeAccessor.construct(BranchTreeDecorator.CODEC)

    val PINE_TREE_CONFIG: TreeFeatureConfig =
        TreeFeatureConfig.Builder(
            SimpleBlockStateProvider(WamBlocks.PINE_LOG.defaultState),
            SimpleBlockStateProvider(WamBlocks.PINE_LEAVES.defaultState),
            PineFoliagePlacer(class_5428.method_30314(1), class_5428.method_30314(1), class_5428.method_30315(4, 1)),
            StraightTrunkPlacer(6, 4, 0),
            TwoLayersFeatureSize(2, 0, 2)
        )
            .ignoreVines()
            .decorators(
                listOf(
                    AlterGroundTreeDecorator(
                        WeightedBlockStateProvider()
                            .addState(Blocks.GRASS_BLOCK.defaultState, 1)
                            .addState(Blocks.PODZOL.defaultState, 1)
                    )
                )
            )
            .build()

    val PINE_SAPLING_TREE_CONFIG: TreeFeatureConfig =
        TreeFeatureConfig.Builder(
            SimpleBlockStateProvider(WamBlocks.PINE_LOG.defaultState),
            SimpleBlockStateProvider(WamBlocks.PINE_LEAVES.defaultState),
            PineFoliagePlacer(class_5428.method_30314(1), class_5428.method_30314(1), class_5428.method_30315(4, 1)),
            StraightTrunkPlacer(6, 4, 0),
            TwoLayersFeatureSize(2, 0, 2)
        ).ignoreVines().build()

    val PINE_SNAG_CONFIG: TreeFeatureConfig =
        TreeFeatureConfig.Builder(
            SimpleBlockStateProvider(WamBlocks.PINE_SNAG_LOG.defaultState),
            SimpleBlockStateProvider(Blocks.AIR.defaultState),
            BlobFoliagePlacer(class_5428.method_30314(0), class_5428.method_30314(0), 0),
            ForkingTrunkPlacer(4, 4, 0),
            TwoLayersFeatureSize(2, 0, 2)
        ).ignoreVines().decorators(listOf(BranchTreeDecorator(WamBlocks.PINE_SNAG_BRANCH, 0.2f))).build()

    val FIREWEED_CONFIG: RandomPatchFeatureConfig =
        RandomPatchFeatureConfig.Builder(
            SimpleBlockStateProvider(WamBlocks.FIREWEED.defaultState),
            DoublePlantPlacer()
        ).tries(64).cannotProject().build()

    val TANSY_CONFIG: RandomPatchFeatureConfig =
        RandomPatchFeatureConfig.Builder(
            SimpleBlockStateProvider(WamBlocks.TANSY.defaultState),
            SimpleBlockPlacer()
        ).tries(64).cannotProject().build()

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
            WamConfiguredFeatures.PLAINS_FLOWERS.method_30374(
                Decorator.CHANCE.configure(ChanceDecoratorConfig(20))
                    .method_30371()
                    .method_30374(Decorator.TOP_SOLID_HEIGHTMAP.configure(DecoratorConfig.DEFAULT))
            )
        )
    }
}
