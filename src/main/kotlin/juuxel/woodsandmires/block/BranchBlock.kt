@file:Suppress("DEPRECATION")
package juuxel.woodsandmires.block

import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.ShapeContext
import net.minecraft.block.Waterloggable
import net.minecraft.fluid.FluidState
import net.minecraft.fluid.Fluids
import net.minecraft.state.StateManager
import net.minecraft.state.property.BooleanProperty
import net.minecraft.state.property.EnumProperty
import net.minecraft.state.property.Properties
import net.minecraft.util.StringIdentifiable
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.util.shape.VoxelShape
import net.minecraft.util.shape.VoxelShapes
import net.minecraft.world.BlockView

class BranchBlock(settings: Settings) : Block(settings), Waterloggable {
    init {
        defaultState = defaultState
            .with(AXIS, Direction.Axis.X)
            .with(STYLE, Style.THIN)
            .with(WATERLOGGED, false)
    }

    override fun getFluidState(state: BlockState): FluidState =
        if (state[WATERLOGGED]) Fluids.WATER.getStill(false)
        else super.getFluidState(state)

    override fun getOutlineShape(
        state: BlockState, world: BlockView, pos: BlockPos, context: ShapeContext
    ): VoxelShape {
        val axis = state[AXIS]
        val style = state[STYLE]

        return when {
            axis == Direction.Axis.X && style == Style.THIN -> THIN_X_SHAPE
            axis == Direction.Axis.X && style == Style.THICK -> THICK_X_SHAPE
            axis == Direction.Axis.Z && style == Style.THIN -> THIN_Z_SHAPE
            axis == Direction.Axis.Z && style == Style.THICK -> THICK_Z_SHAPE
            else -> VoxelShapes.empty()
        }
    }

    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>) {
        super.appendProperties(builder)
        builder.add(AXIS, STYLE, WATERLOGGED)
    }

    companion object {
        private val THIN_X_SHAPE: VoxelShape = createCuboidShape(0.0, 6.0, 6.0, 16.0, 10.0, 10.0)
        private val THICK_X_SHAPE: VoxelShape = createCuboidShape(0.0, 4.0, 4.0, 16.0, 12.0, 12.0)
        private val THIN_Z_SHAPE: VoxelShape = createCuboidShape(6.0, 6.0, 0.0, 10.0, 10.0, 16.0)
        private val THICK_Z_SHAPE: VoxelShape = createCuboidShape(4.0, 4.0, 0.0, 12.0, 12.0, 16.0)

        val AXIS: EnumProperty<Direction.Axis> = Properties.HORIZONTAL_AXIS
        val STYLE: EnumProperty<Style> = EnumProperty.of("style", Style::class.java)
        val WATERLOGGED: BooleanProperty = Properties.WATERLOGGED
    }

    enum class Style(private val id: String) : StringIdentifiable {
        THIN("thin"),
        THICK("thick");

        override fun asString() = id
    }
}
