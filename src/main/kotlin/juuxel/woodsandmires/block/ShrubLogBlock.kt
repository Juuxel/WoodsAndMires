@file:Suppress("DEPRECATION")
package juuxel.woodsandmires.block

import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.PillarBlock
import net.minecraft.block.ShapeContext
import net.minecraft.block.Waterloggable
import net.minecraft.fluid.FluidState
import net.minecraft.fluid.Fluids
import net.minecraft.item.ItemPlacementContext
import net.minecraft.state.StateManager
import net.minecraft.state.property.BooleanProperty
import net.minecraft.state.property.Properties
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.util.shape.VoxelShape
import net.minecraft.util.shape.VoxelShapes
import net.minecraft.world.BlockView

class ShrubLogBlock(settings: Settings) : PillarBlock(settings), Waterloggable {
    init {
        defaultState = defaultState.with(HAS_LEAVES, false).with(WATERLOGGED, false)
    }

    override fun getPlacementState(ctx: ItemPlacementContext): BlockState =
        super.getPlacementState(ctx)!!.with(WATERLOGGED, ctx.world.getFluidState(ctx.blockPos).fluid === Fluids.WATER)

    override fun getFluidState(state: BlockState): FluidState =
        if (state[WATERLOGGED]) Fluids.WATER.getStill(false)
        else super.getFluidState(state)

    override fun getOutlineShape(
        state: BlockState,
        world: BlockView,
        pos: BlockPos,
        context: ShapeContext
    ): VoxelShape {
        if (state[HAS_LEAVES]) return VoxelShapes.fullCube()

        return when (state[AXIS]!!) {
            Direction.Axis.X -> X_SHAPE
            Direction.Axis.Y -> Y_SHAPE
            Direction.Axis.Z -> Z_SHAPE
        }
    }

    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>) {
        super.appendProperties(builder)
        builder.add(HAS_LEAVES, WATERLOGGED)
    }

    companion object {
        private val X_SHAPE: VoxelShape = createCuboidShape(0.0, 2.0, 2.0, 16.0, 14.0, 14.0)
        private val Y_SHAPE: VoxelShape = createCuboidShape(2.0, 0.0, 2.0, 14.0, 16.0, 14.0)
        private val Z_SHAPE: VoxelShape = createCuboidShape(2.0, 2.0, 0.0, 14.0, 14.0, 16.0)

        val HAS_LEAVES: BooleanProperty = BooleanProperty.of("has_leaves")
        val WATERLOGGED: BooleanProperty = Properties.WATERLOGGED
    }
}
