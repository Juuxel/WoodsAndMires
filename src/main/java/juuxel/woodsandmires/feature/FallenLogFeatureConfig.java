package juuxel.woodsandmires.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.Block;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;

public record FallenLogFeatureConfig(Block mainLog, Block groundLog, IntProvider length) implements FeatureConfig {
    public static final Codec<FallenLogFeatureConfig> CODEC = RecordCodecBuilder.create(
        instance -> instance.group(
            Registry.BLOCK.getCodec().fieldOf("main_log").forGetter(FallenLogFeatureConfig::mainLog),
            Registry.BLOCK.getCodec().fieldOf("ground_log").forGetter(FallenLogFeatureConfig::groundLog),
            IntProvider.POSITIVE_CODEC.fieldOf("length").forGetter(FallenLogFeatureConfig::length)
        ).apply(instance, FallenLogFeatureConfig::new)
    );
}
