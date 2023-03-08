package juuxel.woodsandmires.tree;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.gen.treedecorator.TreeDecorator;
import net.minecraft.world.gen.treedecorator.TreeDecoratorType;

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
    public void generate(Generator generator) {
        if (generator.getRandom().nextDouble() <= chance) {
            parent.generate(generator);
        }
    }
}
