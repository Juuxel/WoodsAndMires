package juuxel.woodsandmires.feature

import com.mojang.serialization.Codec
import java.util.Random
import juuxel.woodsandmires.block.WamBlocks
import net.minecraft.block.LeavesBlock
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.world.ServerWorldAccess
import net.minecraft.world.gen.StructureAccessor
import net.minecraft.world.gen.chunk.ChunkGenerator
import net.minecraft.world.gen.feature.DefaultFeatureConfig
import net.minecraft.world.gen.feature.Feature

class PineShrubFeature(configCodec: Codec<DefaultFeatureConfig>) : Feature<DefaultFeatureConfig>(configCodec) {
    override fun generate(
        world: ServerWorldAccess, structureAccessor: StructureAccessor, generator: ChunkGenerator,
        random: Random, pos: BlockPos, config: DefaultFeatureConfig
    ): Boolean {
        val below = pos.down()
        if (!world.getBlockState(below).isSideSolidFullSquare(world, below, Direction.UP)) {
            return false
        }

        val mut = BlockPos.Mutable()
        mut.set(pos)

        val log = WamBlocks.PINE_LOG.defaultState // TODO: Use wood once we have that
        val leaves = WamBlocks.PINE_LEAVES.defaultState.with(LeavesBlock.DISTANCE, 1)

        world.setBlockState(mut, log, 2)

        if (random.nextInt(4) == 0) {
            mut.move(Direction.UP)
            world.setBlockState(mut, log, 2)
        }

        for (direction in Direction.values()) {
            if (direction != Direction.DOWN) {
                mut.move(direction)
                world.setBlockState(mut, leaves, 2)
                mut.move(direction.opposite)
            }
        }

        return true
    }
}
