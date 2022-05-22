package juuxel.woodsandmires.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;

import java.util.Objects;

public record FellPondFeatureConfig(IntProvider radius, IntProvider depth, BlockStateProvider fillBlock,
                                    BlockStateProvider border,
                                    BlockStateProvider bottomBlock, float bottomReplaceChance) implements FeatureConfig {
    public static final Codec<FellPondFeatureConfig> CODEC = RecordCodecBuilder.create(
        instance -> instance.group(
            IntProvider.POSITIVE_CODEC.fieldOf("radius").forGetter(FellPondFeatureConfig::radius),
            IntProvider.POSITIVE_CODEC.fieldOf("depth").forGetter(FellPondFeatureConfig::depth),
            BlockStateProvider.TYPE_CODEC.fieldOf("fill_block").forGetter(FellPondFeatureConfig::fillBlock),
            BlockStateProvider.TYPE_CODEC.fieldOf("border").forGetter(FellPondFeatureConfig::border),
            BlockStateProvider.TYPE_CODEC.fieldOf("bottom_block").forGetter(FellPondFeatureConfig::bottomBlock),
            Codec.FLOAT.fieldOf("bottom_replace_chance").forGetter(FellPondFeatureConfig::bottomReplaceChance)
        ).apply(instance, FellPondFeatureConfig::new)
    );

    public static final class Builder {
        private IntProvider radius;
        private IntProvider depth;
        private BlockStateProvider fillBlock;
        private BlockStateProvider border;
        private BlockStateProvider bottomBlock;
        private Float bottomReplaceChance;

        public Builder radius(IntProvider radius) {
            this.radius = radius;
            return this;
        }

        public Builder depth(IntProvider depth) {
            this.depth = depth;
            return this;
        }

        public Builder fillWith(BlockStateProvider fillBlock) {
            this.fillBlock = fillBlock;
            return this;
        }

        public Builder border(BlockStateProvider border) {
            this.border = border;
            return this;
        }

        public Builder bottomBlock(BlockStateProvider bottomBlock, float bottomReplaceChance) {
            this.bottomBlock = bottomBlock;
            this.bottomReplaceChance = bottomReplaceChance;
            return this;
        }

        public FellPondFeatureConfig build() {
            return new FellPondFeatureConfig(
                Objects.requireNonNull(radius, "radius"),
                Objects.requireNonNull(depth, "depth"),
                Objects.requireNonNull(fillBlock, "fillBlock"),
                Objects.requireNonNull(border, "border"),
                Objects.requireNonNull(bottomBlock, "bottomBlock"),
                Objects.requireNonNull(bottomReplaceChance, "bottomReplaceChance")
            );
        }
    }
}
