package juuxel.woodsandmires.block;

import juuxel.woodsandmires.feature.WamConfiguredFeatures;
import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;

import java.util.Random;

public class PineSaplingGenerator extends SaplingGenerator {
    @Override
    protected ConfiguredFeature<TreeFeatureConfig, ?> createTreeFeature(Random random, boolean bl) {
        return WamConfiguredFeatures.Undecorated.PINE_FROM_SAPLING;
    }
}
