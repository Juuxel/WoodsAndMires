package juuxel.woodsandmires.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.PillarBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.Waterloggable;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;

public class ShrubLogBlock extends PillarBlock implements Waterloggable {
    public static final BooleanProperty HAS_LEAVES = BooleanProperty.of("has_leaves");
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;

    private static final VoxelShape X_SHAPE = createCuboidShape(0, 2, 2, 16, 14, 14);
    private static final VoxelShape Y_SHAPE = createCuboidShape(2, 0, 2, 14, 16, 14);
    private static final VoxelShape Z_SHAPE = createCuboidShape(2, 2, 0, 14, 14, 16);

    public ShrubLogBlock(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(HAS_LEAVES, false).with(WATERLOGGED, false));
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        // noinspection ConstantConditions
        return super.getPlacementState(ctx)
            .with(WATERLOGGED, ctx.getWorld().getFluidState(ctx.getBlockPos()).getFluid() == Fluids.WATER);
    }

    @SuppressWarnings("deprecation")
    @Override
    public FluidState getFluidState(BlockState state) {
        if (state.get(WATERLOGGED)) {
            return Fluids.WATER.getStill(false);
        }

        return super.getFluidState(state);
    }

    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if (state.get(HAS_LEAVES)) {
            return VoxelShapes.fullCube();
        }

        switch (state.get(AXIS)) {
            case X:
                return X_SHAPE;
            case Y:
                return Y_SHAPE;
            case Z:
            default:
                return Z_SHAPE;
        }
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (state.get(WATERLOGGED)) {
            world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }

        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(HAS_LEAVES, WATERLOGGED);
    }
}
