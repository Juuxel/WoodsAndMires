package juuxel.woodsandmires.tree;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.util.collection.DataPool;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.gen.treedecorator.TreeDecorator;
import net.minecraft.world.gen.treedecorator.TreeDecoratorType;

import java.util.List;
import java.util.Random;
import java.util.function.BiConsumer;

public final class PoolTreeDecorator extends TreeDecorator {
    public static final Codec<PoolTreeDecorator> CODEC = RecordCodecBuilder.create(builder ->
        builder.group(
            DataPool.createCodec(TreeDecorator.TYPE_CODEC).fieldOf("decorators")
                .forGetter(PoolTreeDecorator::getDecorators)
        ).apply(builder, PoolTreeDecorator::new)
    );
    private final DataPool<TreeDecorator> decorators;

    public PoolTreeDecorator(DataPool<TreeDecorator> decorators) {
        if (decorators.isEmpty()) {
            throw new IllegalArgumentException("Cannot create PoolTreeDecorator with an empty pool!");
        }

        this.decorators = decorators;
    }

    public DataPool<TreeDecorator> getDecorators() {
        return decorators;
    }

    @Override
    protected TreeDecoratorType<?> getType() {
        return WamTreeDecorators.POOL;
    }

    @Override
    public void generate(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, Random random, List<BlockPos> logPositions, List<BlockPos> leavesPositions) {
        decorators.getDataOrEmpty(random)
            .orElseThrow(IllegalStateException::new)
            .generate(world, replacer, random, logPositions, leavesPositions);
    }
}
