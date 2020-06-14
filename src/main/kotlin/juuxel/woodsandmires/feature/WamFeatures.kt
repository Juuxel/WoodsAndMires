package juuxel.woodsandmires.feature

import juuxel.woodsandmires.WoodsAndMires
import juuxel.woodsandmires.block.WamBlocks
import net.minecraft.block.Blocks
import net.minecraft.util.registry.Registry
import net.minecraft.world.gen.decorator.AlterGroundTreeDecorator
import net.minecraft.world.gen.feature.DefaultFeatureConfig
import net.minecraft.world.gen.feature.Feature
import net.minecraft.world.gen.feature.TreeFeatureConfig
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize
import net.minecraft.world.gen.foliage.PineFoliagePlacer
import net.minecraft.world.gen.stateprovider.SimpleBlockStateProvider
import net.minecraft.world.gen.stateprovider.WeightedBlockStateProvider
import net.minecraft.world.gen.trunk.StraightTrunkPlacer

object WamFeatures {
    val PINE_TREE_CONFIG: TreeFeatureConfig =
        TreeFeatureConfig.Builder(
            SimpleBlockStateProvider(WamBlocks.PINE_LOG.defaultState),
            SimpleBlockStateProvider(WamBlocks.PINE_LEAVES.defaultState),
            PineFoliagePlacer(1, 0, 1, 0, 4, 1),
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
            PineFoliagePlacer(1, 0, 1, 0, 4, 1),
            StraightTrunkPlacer(6, 4, 0),
            TwoLayersFeatureSize(2, 0, 2)
        )
            .ignoreVines()
            .build()

    val PINE_SHRUB: Feature<PineShrubFeatureConfig> = PineShrubFeature(PineShrubFeatureConfig.CODEC)
    val MIRE_PONDS: Feature<DefaultFeatureConfig> = MirePondsFeature(DefaultFeatureConfig.CODEC)
    val MEADOW: Feature<MeadowFeatureConfig> = MeadowFeature(MeadowFeatureConfig.CODEC)

    fun init() {
        register("pine_shrub", PINE_SHRUB)
        register("mire_ponds", MIRE_PONDS)
        register("meadow", MEADOW)
    }

    private fun register(id: String, feature: Feature<*>) {
        Registry.register(Registry.FEATURE, WoodsAndMires.id(id), feature)
    }
}
