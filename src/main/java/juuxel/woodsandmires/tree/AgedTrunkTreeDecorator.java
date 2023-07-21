package juuxel.woodsandmires.tree;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.ints.IntRBTreeSet;
import it.unimi.dsi.fastutil.ints.IntSortedSet;
import juuxel.woodsandmires.block.AgedLogBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.registry.Registries;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.floatprovider.FloatProvider;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.treedecorator.TreeDecorator;
import net.minecraft.world.gen.treedecorator.TreeDecoratorType;

public final class AgedTrunkTreeDecorator extends TreeDecorator {
    public static final Codec<AgedTrunkTreeDecorator> CODEC = RecordCodecBuilder.create(
        instance -> instance.group(
            Registries.BLOCK.getCodec().fieldOf("log").forGetter(AgedTrunkTreeDecorator::getLog),
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
    public void generate(Generator generator) {
        IntSortedSet heights = new IntRBTreeSet();
        for (BlockPos pos : generator.getLogPositions()) {
            heights.add(pos.getY());
        }
        float heightPoint = agedHeightFraction.get(generator.getRandom());
        int midY = (int) MathHelper.lerp(heightPoint, heights.firstInt(), heights.lastInt());

        for (BlockPos pos : generator.getLogPositions()) {
            if (pos.getY() > midY) {
                break;
            } else if (generator.getWorld().testBlockState(pos, Feature::isSoil)) {
                // Don't replace the dirt underneath the trunk
                continue;
            }

            BlockState state = log.getDefaultState().with(AgedLogBlock.MID, pos.getY() == midY);
            generator.replace(pos, state);
        }
    }
}
