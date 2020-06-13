package juuxel.woodsandmires.feature

import juuxel.woodsandmires.block.WamBlocks
import net.minecraft.world.gen.feature.TreeFeatureConfig
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize
import net.minecraft.world.gen.foliage.PineFoliagePlacer
import net.minecraft.world.gen.stateprovider.SimpleBlockStateProvider
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
            .build()
}
