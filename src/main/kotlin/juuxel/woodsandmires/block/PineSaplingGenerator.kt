package juuxel.woodsandmires.block

import java.util.Random
import juuxel.woodsandmires.feature.WamConfiguredFeatures
import net.minecraft.block.sapling.SaplingGenerator
import net.minecraft.world.gen.feature.ConfiguredFeature
import net.minecraft.world.gen.feature.TreeFeatureConfig

object PineSaplingGenerator : SaplingGenerator() {
    override fun createTreeFeature(random: Random, flowerRelated: Boolean): ConfiguredFeature<TreeFeatureConfig, *> =
        WamConfiguredFeatures.PINE_FROM_SAPLING
}
