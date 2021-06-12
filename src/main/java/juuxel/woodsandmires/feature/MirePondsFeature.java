package juuxel.woodsandmires.feature;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

public class MirePondsFeature extends Feature<DefaultFeatureConfig> {
    private static final Direction[] NON_UP_DIRECTIONS = new Direction[] {
        Direction.NORTH,
        Direction.EAST,
        Direction.SOUTH,
        Direction.WEST,
        Direction.DOWN
    };

    public MirePondsFeature(Codec<DefaultFeatureConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
        var world = context.getWorld();
        var pos = context.getOrigin();
        var config = context.getConfig();
        var random = context.getRandom();

        BlockState water = Blocks.WATER.getDefaultState();
        BlockPos.Mutable mut = new BlockPos.Mutable();
        boolean generated = false;

        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                // Leave holes in the ponds
                if (random.nextBoolean()) continue;

                int xo = pos.getX() + x;
                int zo = pos.getZ() + z;
                int y = world.getTopY(Heightmap.Type.MOTION_BLOCKING, xo, zo) - 1;
                mut.set(xo, y, zo);

                if (isSoil(world, mut) && isSolidOrWaterAround(world, mut)) {
                    setBlockState(world, mut, water);
                    generated = true;

                    if (random.nextInt(3) == 0) {
                        mut.move(Direction.DOWN);

                        if (world.getBlockState(mut).isSolidBlock(world, mut) & isSolidOrWaterAround(world, mut)) {
                            setBlockState(world, mut, water);
                        }
                    }
                }
            }
        }

        return generated;
    }

    private static boolean isSolidOrWaterAround(StructureWorldAccess world, BlockPos.Mutable pos) {
        for (Direction direction : NON_UP_DIRECTIONS) {
            pos.move(direction);
            BlockState state = world.getBlockState(pos);
            pos.move(direction.getOpposite());

            if (!state.isOf(Blocks.WATER) && !state.isSolidBlock(world, pos)) {
                return false;
            }
        }

        return true;
    }
}
