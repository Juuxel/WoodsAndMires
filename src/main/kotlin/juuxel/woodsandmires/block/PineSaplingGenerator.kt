package juuxel.woodsandmires.block

import java.util.Random
import juuxel.woodsandmires.feature.WamFeatures
import net.minecraft.block.sapling.SaplingGenerator
import net.minecraft.world.gen.feature.ConfiguredFeature
import net.minecraft.world.gen.feature.TreeFeature
import net.minecraft.world.gen.feature.TreeFeatureConfig

object PineSaplingGenerator : SaplingGenerator() {
    override fun createTreeFeature(random: Random, flowerRelated: Boolean): ConfiguredFeature<TreeFeatureConfig, *> =
        TreeFeature(TreeFeatureConfig.CODEC).configure(WamFeatures.PINE_SAPLING_TREE_CONFIG)
}
