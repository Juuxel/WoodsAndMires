package juuxel.woodsandmires.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.world.gen.feature.FeatureConfig;

public final class ShrubFeatureConfig implements FeatureConfig {
    public static final Codec<ShrubFeatureConfig> CODEC = RecordCodecBuilder.create(
        instance -> instance.group(
            BlockState.CODEC.fieldOf("log").forGetter(ShrubFeatureConfig::getLog),
            BlockState.CODEC.fieldOf("leaves").forGetter(ShrubFeatureConfig::getLeaves),
            Codec.INT.fieldOf("base_height").forGetter(ShrubFeatureConfig::getBaseHeight),
            Codec.INT.fieldOf("extra_height").forGetter(ShrubFeatureConfig::getExtraHeight),
            Codec.FLOAT.fieldOf("extra_height_chance").forGetter(ShrubFeatureConfig::getExtraHeightChance)
        ).apply(instance, ShrubFeatureConfig::new)
    );

    final BlockState log;
    final BlockState leaves;
    final int baseHeight;
    final int extraHeight;
    final float extraHeightChance;

    public ShrubFeatureConfig(BlockState log, BlockState leaves, int baseHeight, int extraHeight, float extraHeightChance) {
        this.log = log;
        this.leaves = leaves;
        this.baseHeight = baseHeight;
        this.extraHeight = extraHeight;
        this.extraHeightChance = extraHeightChance;
    }

    public BlockState getLog() {
        return log;
    }

    public BlockState getLeaves() {
        return leaves;
    }

    public int getBaseHeight() {
        return baseHeight;
    }

    public int getExtraHeight() {
        return extraHeight;
    }

    public float getExtraHeightChance() {
        return extraHeightChance;
    }
}
