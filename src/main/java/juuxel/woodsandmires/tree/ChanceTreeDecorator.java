package juuxel.woodsandmires.tree;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.gen.treedecorator.TreeDecorator;
import net.minecraft.world.gen.treedecorator.TreeDecoratorType;

import java.util.List;
import java.util.Random;
import java.util.function.BiConsumer;

public final class ChanceTreeDecorator extends TreeDecorator {
    public static final Codec<ChanceTreeDecorator> CODEC =
        RecordCodecBuilder.create(builder ->
            builder.group(
                TreeDecorator.TYPE_CODEC.fieldOf("parent").forGetter(ChanceTreeDecorator::getParent),
                Codec.doubleRange(0, 1).fieldOf("chance").forGetter(ChanceTreeDecorator::getChance)
            ).apply(builder, ChanceTreeDecorator::new)
        );

    private final TreeDecorator parent;
    private final double chance;

    public ChanceTreeDecorator(TreeDecorator parent, double chance) {
        this.parent = parent;
        this.chance = chance;
    }

    public TreeDecorator getParent() {
        return parent;
    }

    public double getChance() {
        return chance;
    }

    @Override
    protected TreeDecoratorType<?> getType() {
        return WamTreeDecorators.CHANCE;
    }

    @Override
    public void generate(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, Random random, List<BlockPos> logPositions, List<BlockPos> leavesPositions) {
        if (random.nextDouble() <= chance) {
            parent.generate(world, replacer, random, logPositions, leavesPositions);
        }
    }
}
