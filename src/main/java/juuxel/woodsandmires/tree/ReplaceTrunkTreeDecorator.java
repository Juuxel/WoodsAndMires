package juuxel.woodsandmires.tree;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.treedecorator.TreeDecorator;
import net.minecraft.world.gen.treedecorator.TreeDecoratorType;

public final class ReplaceTrunkTreeDecorator extends TreeDecorator {
    public static final Codec<ReplaceTrunkTreeDecorator> CODEC =
        RecordCodecBuilder.create(builder ->
            builder.group(
                BlockStateProvider.TYPE_CODEC.fieldOf("trunk").forGetter(ReplaceTrunkTreeDecorator::getTrunk)
            ).apply(builder, ReplaceTrunkTreeDecorator::new)
        );
    private final BlockStateProvider trunk;

    public ReplaceTrunkTreeDecorator(BlockStateProvider trunk) {
        this.trunk = trunk;
    }

    public BlockStateProvider getTrunk() {
        return trunk;
    }

    @Override
    protected TreeDecoratorType<?> getType() {
        return WamTreeDecorators.REPLACE_TRUNK;
    }

    @Override
    public void generate(Generator generator) {
        for (BlockPos pos : generator.getLogPositions()) {
            // Don't replace the dirt underneath the trunk
            if (generator.getWorld().testBlockState(pos, Feature::isSoil)) continue;

            generator.replace(pos, trunk.getBlockState(generator.getRandom(), pos));
        }
    }
}
