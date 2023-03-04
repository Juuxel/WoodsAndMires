package juuxel.woodsandmires.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.Block;
import net.minecraft.util.math.floatprovider.FloatProvider;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;

public record FallenLogFeatureConfig(Block mainLog, Block agedLog, FloatProvider agedHeightFraction,
                                     IntProvider length, BlockStateProvider topDecoration) implements FeatureConfig {
    public static final Codec<FallenLogFeatureConfig> CODEC = RecordCodecBuilder.create(
        instance -> instance.group(
            Registry.BLOCK.getCodec().fieldOf("main_log").forGetter(FallenLogFeatureConfig::mainLog),
            Registry.BLOCK.getCodec().fieldOf("aged_log").forGetter(FallenLogFeatureConfig::agedLog),
            FloatProvider.VALUE_CODEC.fieldOf("aged_height_fraction").forGetter(FallenLogFeatureConfig::agedHeightFraction),
            IntProvider.POSITIVE_CODEC.fieldOf("length").forGetter(FallenLogFeatureConfig::length),
            BlockStateProvider.TYPE_CODEC.fieldOf("top_decoration").forGetter(FallenLogFeatureConfig::topDecoration)
        ).apply(instance, FallenLogFeatureConfig::new)
    );
}
