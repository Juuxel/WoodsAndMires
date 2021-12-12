package juuxel.woodsandmires.block;

import juuxel.woodsandmires.feature.WamConfiguredFeatures;
import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.world.gen.feature.ConfiguredFeature;

import java.util.Random;

public class PineSaplingGenerator extends SaplingGenerator {
    @Override
    protected ConfiguredFeature<?, ?> getTreeFeature(Random random, boolean bees) {
        return WamConfiguredFeatures.PINE_FROM_SAPLING;
    }
}
