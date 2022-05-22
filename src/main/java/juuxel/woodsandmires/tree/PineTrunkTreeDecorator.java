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
import net.minecraft.util.registry.Registry;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.gen.treedecorator.TreeDecorator;
import net.minecraft.world.gen.treedecorator.TreeDecoratorType;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.function.BiConsumer;

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
    public void generate(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, Random random, List<BlockPos> logPositions, List<BlockPos> leavesPositions) {
        var sortedLogPositions = new ArrayList<>(logPositions);
        sortedLogPositions.sort(Comparator.comparing(BlockPos::getY));

        IntSortedSet heights = new IntRBTreeSet();
        for (BlockPos pos : sortedLogPositions) {
            heights.add(pos.getY());
        }
        int midY = (int) MathHelper.lerp(getRandomHeightPoint(random), heights.firstInt(), heights.lastInt());

        for (BlockPos pos : sortedLogPositions) {
            if (pos.getY() > midY) {
                break;
            }

            BlockState state = log.getDefaultState().with(GroundLogBlock.MID, pos.getY() == midY);
            replacer.accept(pos, state);
        }
    }

    public static float getRandomHeightPoint(Random random) {
        return (MAX_HEIGHT_POINT - MIN_HEIGHT_POINT) * random.nextFloat() + MIN_HEIGHT_POINT;
    }
}
