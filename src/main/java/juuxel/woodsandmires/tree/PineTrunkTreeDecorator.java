package juuxel.woodsandmires.tree;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.ints.IntRBTreeSet;
import it.unimi.dsi.fastutil.ints.IntSortedSet;
import juuxel.woodsandmires.block.GroundLogBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.treedecorator.TreeDecorator;
import net.minecraft.world.gen.treedecorator.TreeDecoratorType;

public final class PineTrunkTreeDecorator extends TreeDecorator {
    public static final Codec<PineTrunkTreeDecorator> CODEC = RecordCodecBuilder.create(
        instance -> instance.group(
            Registry.BLOCK.getCodec().fieldOf("log").forGetter(PineTrunkTreeDecorator::getLog)
        ).apply(instance, PineTrunkTreeDecorator::new)
    );
    private static final float MIN_HEIGHT_POINT = 0.3f;
    private static final float MAX_HEIGHT_POINT = 0.65f;
    private final Block log;

    public PineTrunkTreeDecorator(Block log) {
        this.log = log;
    }

    public Block getLog() {
        return log;
    }

    @Override
    protected TreeDecoratorType<?> getType() {
        return WamTreeDecorators.PINE_TRUNK;
    }

    @Override
    public void generate(Generator generator) {
        IntSortedSet heights = new IntRBTreeSet();
        for (BlockPos pos : generator.getLogPositions()) {
            heights.add(pos.getY());
        }
        int midY = (int) MathHelper.lerp(getRandomHeightPoint(generator.getRandom()), heights.firstInt(), heights.lastInt());

        for (BlockPos pos : generator.getLogPositions()) {
            if (pos.getY() > midY) {
                break;
            }

            BlockState state = log.getDefaultState().with(GroundLogBlock.MID, pos.getY() == midY);
            generator.replace(pos, state);
        }
    }

    public static float getRandomHeightPoint(Random random) {
        return (MAX_HEIGHT_POINT - MIN_HEIGHT_POINT) * random.nextFloat() + MIN_HEIGHT_POINT;
    }
}
