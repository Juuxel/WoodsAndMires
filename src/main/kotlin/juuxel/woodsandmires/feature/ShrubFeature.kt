package juuxel.woodsandmires.feature

import com.mojang.serialization.Codec
import juuxel.woodsandmires.block.ShrubLogBlock
import net.minecraft.block.LeavesBlock
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.world.StructureWorldAccess
import net.minecraft.world.gen.chunk.ChunkGenerator
import net.minecraft.world.gen.feature.Feature
import java.util.Random

class ShrubFeature(configCodec: Codec<ShrubFeatureConfig>) : Feature<ShrubFeatureConfig>(configCodec) {
    override fun generate(
        world: StructureWorldAccess, generator: ChunkGenerator,
        random: Random, pos: BlockPos, config: ShrubFeatureConfig
    ): Boolean {
        val below = pos.down()
        if (!isSoil(world, below) || !world.getBlockState(below).isSideSolidFullSquare(world, below, Direction.UP)) {
            return false
        }

        val mut = BlockPos.Mutable()
        mut.set(pos)

        val log = config.log
        val logWithLeaves =
            if (log.block is ShrubLogBlock) log.with(ShrubLogBlock.HAS_LEAVES, true)
            else log
        val leaves = config.leaves.with(LeavesBlock.DISTANCE, 1)
        val extraHeight =
            if (random.nextFloat() < config.extraHeightChance) random.nextInt(config.extraHeight + 1)
            else 0

        val height = config.baseHeight + extraHeight

        for (y in 1..height) {
            if (y > 1 || height == 1) {
                world.setBlockState(mut, logWithLeaves, 2)

                for (direction in Direction.Type.HORIZONTAL) {
                    mut.move(direction)
                    world.setBlockState(mut, leaves, 2)
                    mut.move(direction.opposite)
                }
            } else {
                world.setBlockState(mut, log, 2)
            }

            mut.move(Direction.UP)
        }

        world.setBlockState(mut, leaves, 2)

        return true
    }
}
