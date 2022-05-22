package juuxel.woodsandmires.feature;

import com.mojang.serialization.Codec;
import juuxel.woodsandmires.block.GroundLogBlock;
import juuxel.woodsandmires.tree.PineTrunkTreeDecorator;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.PillarBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

public class FallenLogFeature extends Feature<FallenLogFeatureConfig> {
    public FallenLogFeature(Codec<FallenLogFeatureConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean generate(FeatureContext<FallenLogFeatureConfig> context) {
        BlockPos.Mutable mut = context.getOrigin().mutableCopy();
        if (!canPlace(context.getWorld(), mut)) {
            return false;
        }

        var config = context.getConfig();
        var random = context.getRandom();
        Direction.Axis axis = Direction.Type.HORIZONTAL.randomAxis(random);
        // We need to correct it so that the "mid" texture will align correctly.
        Direction direction = axis == Direction.Axis.X ? Direction.WEST : Direction.SOUTH;
        int length = config.length().get(random);
        int mid = (int) (PineTrunkTreeDecorator.getRandomHeightPoint(random) * length);

        if (random.nextInt(5) == 0) {
            for (int i = 0; i < length; i++) {
                if (!canPlace(context.getWorld(), mut)) {
                    break;
                }

                Block block = i < mid ? config.mainLog() : config.groundLog();
                BlockState state = block.getDefaultState().with(PillarBlock.AXIS, axis);

                if (i == mid) {
                    state = state.with(GroundLogBlock.MID, true);
                }

                setBlockState(context.getWorld(), mut, state);
                mut.move(direction);
            }
        } else {
            Block block = random.nextBoolean() ? config.mainLog() : config.groundLog();
            BlockState state = block.getDefaultState().with(PillarBlock.AXIS, axis);

            for (int i = 0; i < length; i++) {
                if (!canPlace(context.getWorld(), mut)) {
                    break;
                }

                setBlockState(context.getWorld(), mut, state);
                mut.move(direction);
            }
        }

        return true;
    }

    private static boolean canPlace(TestableWorld world, BlockPos.Mutable mut) {
        if (!isAir(world, mut)) return false;
        mut.move(0, -1, 0);
        boolean result = isSoil(world, mut);
        mut.move(0, 1, 0);
        return result;
    }
}
