package juuxel.woodsandmires.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.Waterloggable;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public class BranchBlock extends Block implements Waterloggable {
    public static final EnumProperty<Direction.Axis> AXIS = Properties.HORIZONTAL_AXIS;
    public static final EnumProperty<Style> STYLE = EnumProperty.of("style", Style.class);
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
    
    private static final VoxelShape THIN_X_SHAPE = createCuboidShape(0, 6, 6, 16, 10, 10);
    private static final VoxelShape THICK_X_SHAPE = createCuboidShape(0, 4, 4, 16, 12, 12);
    private static final VoxelShape THIN_Z_SHAPE = createCuboidShape(6, 6, 0, 10, 10, 16);
    private static final VoxelShape THICK_Z_SHAPE = createCuboidShape(4, 4, 0, 12, 12, 16);

    public BranchBlock(Settings settings) {
        super(settings);
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
        switch (state.get(AXIS)) {
            case X:
                return state.get(STYLE) == Style.THIN ? THIN_X_SHAPE : THICK_X_SHAPE;
            case Z:
                return state.get(STYLE) == Style.THIN ? THIN_Z_SHAPE : THICK_Z_SHAPE;
            default:
                return VoxelShapes.fullCube();
        }
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(AXIS, STYLE, WATERLOGGED);
    }

    public enum Style implements StringIdentifiable {
        THIN("thin"), THICK("thick");

        private final String id;

        Style(String id) {
            this.id = id;
        }

        @Override
        public String asString() {
            return id;
        }
    }
}
