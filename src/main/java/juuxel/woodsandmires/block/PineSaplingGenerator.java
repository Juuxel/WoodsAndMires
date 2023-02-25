package juuxel.woodsandmires.block;

import juuxel.woodsandmires.feature.WamConfiguredFeatureKeys;
import net.minecraft.block.BlockState;
import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.ConfiguredFeature;

import java.util.Random;

public class PineSaplingGenerator extends SaplingGenerator {
    private Registry<ConfiguredFeature<?, ?>> configuredFeatureRegistry;

    @Override
    protected RegistryEntry<? extends ConfiguredFeature<?, ?>> getTreeFeature(Random random, boolean bees) {
        return configuredFeatureRegistry.entryOf(WamConfiguredFeatureKeys.PINE_FROM_SAPLING);
    }

    @Override
    public synchronized boolean generate(ServerWorld world, ChunkGenerator chunkGenerator, BlockPos pos, BlockState state, Random random) {
        configuredFeatureRegistry = world.getRegistryManager().get(Registry.CONFIGURED_FEATURE_KEY);
        boolean result = super.generate(world, chunkGenerator, pos, state, random);
        configuredFeatureRegistry = null;
        return result;
    }
}
