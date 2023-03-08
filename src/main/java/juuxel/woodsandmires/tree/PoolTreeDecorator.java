package juuxel.woodsandmires.tree;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.collection.DataPool;
import net.minecraft.world.gen.treedecorator.TreeDecorator;
import net.minecraft.world.gen.treedecorator.TreeDecoratorType;

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
    public void generate(Generator generator) {
        decorators.getDataOrEmpty(generator.getRandom())
            .orElseThrow(IllegalStateException::new)
            .generate(generator);
    }
}
