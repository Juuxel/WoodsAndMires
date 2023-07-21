package juuxel.woodsandmires.tree;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.ints.IntRBTreeSet;
import it.unimi.dsi.fastutil.ints.IntSortedSet;
import juuxel.woodsandmires.block.AgedLogBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.floatprovider.FloatProvider;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.treedecorator.TreeDecorator;
import net.minecraft.world.gen.treedecorator.TreeDecoratorType;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.function.BiConsumer;

public final class AgedTrunkTreeDecorator extends TreeDecorator {
    public static final Codec<AgedTrunkTreeDecorator> CODEC = RecordCodecBuilder.create(
        instance -> instance.group(
            Registry.BLOCK.getCodec().fieldOf("log").forGetter(AgedTrunkTreeDecorator::getLog),
            FloatProvider.createValidatedCodec(0, 1).fieldOf("aged_height_fraction")
                .forGetter(AgedTrunkTreeDecorator::getAgedHeightFraction)
        ).apply(instance, AgedTrunkTreeDecorator::new)
    );
    private final Block log;
    private final FloatProvider agedHeightFraction;

    public AgedTrunkTreeDecorator(Block log, FloatProvider agedHeightFraction) {
        this.log = log;
        this.agedHeightFraction = agedHeightFraction;
    }

    public Block getLog() {
        return log;
    }

    public FloatProvider getAgedHeightFraction() {
        return agedHeightFraction;
    }

    @Override
    protected TreeDecoratorType<?> getType() {
        return WamTreeDecorators.AGED_TRUNK;
    }

    @Override
    public void generate(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, Random random, List<BlockPos> logPositions, List<BlockPos> leavesPositions) {
        var sortedLogPositions = new ArrayList<>(logPositions);
        sortedLogPositions.sort(Comparator.comparing(BlockPos::getY));

        IntSortedSet heights = new IntRBTreeSet();
        for (BlockPos pos : sortedLogPositions) {
            heights.add(pos.getY());
        }
        float heightPoint = agedHeightFraction.get(random);
        int midY = (int) MathHelper.lerp(heightPoint, heights.firstInt(), heights.lastInt());

        for (BlockPos pos : sortedLogPositions) {
            if (pos.getY() > midY) {
                break;
            } else if (world.testBlockState(pos, Feature::isSoil)) {
                // Don't replace the dirt underneath the trunk
                continue;
            }

            BlockState state = log.getDefaultState().with(AgedLogBlock.MID, pos.getY() == midY);
            replacer.accept(pos, state);
        }
    }
}
