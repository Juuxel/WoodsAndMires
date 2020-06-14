package juuxel.woodsandmires.block

import net.minecraft.block.BlockState
import net.minecraft.block.FlowerBlock
import net.minecraft.block.ShapeContext
import net.minecraft.entity.effect.StatusEffect
import net.minecraft.util.math.BlockPos
import net.minecraft.util.shape.VoxelShape
import net.minecraft.util.shape.VoxelShapes
import net.minecraft.world.BlockView

class BigFlowerBlock(
    suspiciousStewEffect: StatusEffect,
    effectDuration: Int,
    settings: Settings
) : FlowerBlock(suspiciousStewEffect, effectDuration, settings) {
    override fun getOutlineShape(
        state: BlockState, world: BlockView, pos: BlockPos, context: ShapeContext
    ): VoxelShape {
        val offset = state.getModelOffset(world, pos)
        return VoxelShapes.fullCube().offset(offset.x, offset.y, offset.z)
    }
}
