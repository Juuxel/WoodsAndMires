package juuxel.woodsandmires.block;

import juuxel.woodsandmires.feature.WamConfiguredFeatureKeys;
import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.gen.feature.ConfiguredFeature;

public class PineSaplingGenerator extends SaplingGenerator {
    @Override
    protected RegistryKey<ConfiguredFeature<?, ?>> getTreeFeature(Random random, boolean bees) {
        return WamConfiguredFeatureKeys.PINE_FROM_SAPLING;
    }
}
