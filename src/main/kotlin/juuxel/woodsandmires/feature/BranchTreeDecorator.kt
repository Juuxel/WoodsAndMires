package juuxel.woodsandmires.feature

import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import java.util.Random
import juuxel.woodsandmires.block.BranchBlock
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.util.math.BlockBox
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.util.registry.Registry
import net.minecraft.world.ServerWorldAccess
import net.minecraft.world.WorldAccess
import net.minecraft.world.gen.decorator.TreeDecorator

class BranchTreeDecorator(private val block: Block, private val chance: Float) : TreeDecorator() {
    override fun getType() = WamFeatures.BRANCH_TREE_DECORATOR

    override fun generate(
        world: ServerWorldAccess,
        random: Random,
        logPositions: List<BlockPos>,
        leavesPositions: List<BlockPos>,
        positions: MutableSet<BlockPos>,
        box: BlockBox
    ) {
        val mut = BlockPos.Mutable()

        for (pos in logPositions) {
            mut.set(pos)
            for (side in Direction.Type.HORIZONTAL) {
                mut.move(side)
                if (random.nextFloat() < chance) {
                    val thinState = block.defaultState.with(BranchBlock.AXIS, side.axis)
                        .with(BranchBlock.STYLE, BranchBlock.Style.THIN)

                    if (random.nextBoolean()) {
                        val thickState = thinState.with(BranchBlock.STYLE, BranchBlock.Style.THICK)
                        setIfAir(world, mut, thickState, positions, box)
                    } else {
                        setIfAir(world, mut, thinState, positions, box)
                    }
                }
                mut.move(side.opposite)
            }
        }
    }

    private fun setIfAir(world: WorldAccess, pos: BlockPos, state: BlockState, posSet: Set<BlockPos>, box: BlockBox) {
        if (world.isAir(pos)) {
            setBlockStateAndEncompassPosition(world, pos, state, posSet, box)
        }
    }

    companion object {
        val CODEC: Codec<BranchTreeDecorator> = RecordCodecBuilder.create { instance ->
            instance.group(
                Registry.BLOCK.fieldOf("block").forGetter { it.block },
                Codec.FLOAT.fieldOf("chance").forGetter { it.chance }
            ).apply(instance, ::BranchTreeDecorator)
        }
    }
}
