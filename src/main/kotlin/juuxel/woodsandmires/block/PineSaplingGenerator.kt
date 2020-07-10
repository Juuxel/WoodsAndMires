package juuxel.woodsandmires.block

import juuxel.woodsandmires.feature.WamConfiguredFeatures
import net.minecraft.block.sapling.SaplingGenerator
import net.minecraft.world.gen.feature.ConfiguredFeature
import net.minecraft.world.gen.feature.TreeFeatureConfig
import java.util.Random

object PineSaplingGenerator : SaplingGenerator() {
    override fun createTreeFeature(random: Random, flowerRelated: Boolean): ConfiguredFeature<TreeFeatureConfig, *> =
        WamConfiguredFeatures.PINE_FROM_SAPLING
}
