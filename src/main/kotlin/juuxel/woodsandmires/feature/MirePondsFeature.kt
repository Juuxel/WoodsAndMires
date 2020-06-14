package juuxel.woodsandmires.feature

import com.mojang.serialization.Codec
import net.minecraft.block.Blocks
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.world.Heightmap
import net.minecraft.world.ServerWorldAccess
import net.minecraft.world.gen.StructureAccessor
import net.minecraft.world.gen.chunk.ChunkGenerator
import net.minecraft.world.gen.feature.DefaultFeatureConfig
import net.minecraft.world.gen.feature.Feature
import java.util.Random

class MirePondsFeature(configCodec: Codec<DefaultFeatureConfig>) : Feature<DefaultFeatureConfig>(configCodec) {
    override fun generate(
        world: ServerWorldAccess, structureAccessor: StructureAccessor, generator: ChunkGenerator,
        random: Random, pos: BlockPos, config: DefaultFeatureConfig
    ): Boolean {
        val water = Blocks.WATER.defaultState
        val mut = BlockPos.Mutable()
        var generated = false

        for (x in 0..15) {
            for (z in 0..15) {
                if (random.nextBoolean()) continue

                val xo = pos.x + x
                val zo = pos.z + z
                mut.set(xo, world.getTopY(Heightmap.Type.MOTION_BLOCKING, xo, zo) - 1, zo)

                if (method_27368(world, mut) && isSolidOrWaterAround(world, mut)) {
                    world.setBlockState(mut, water, 2)
                    generated = true

                    if (random.nextInt(3) == 0) {
                        mut.move(0, -1, 0)
                        if (method_27368(world, mut) && isSolidOrWaterAround(world, mut)) {
                            world.setBlockState(mut, water, 2)
                        }
                    }
                }
            }
        }

        return generated
    }

    private fun isSolidOrWaterAround(world: ServerWorldAccess, pos: BlockPos.Mutable): Boolean =
        Direction.values().all { direction ->
            if (direction == Direction.UP) return@all true

            pos.move(direction)
            val state = world.getBlockState(pos)
            pos.move(direction.opposite)

            state.isOf(Blocks.WATER) || state.isSolidBlock(world, pos)
        }
}
