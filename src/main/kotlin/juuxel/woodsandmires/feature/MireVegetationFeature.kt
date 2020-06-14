package juuxel.woodsandmires.feature

import com.mojang.serialization.Codec
import net.minecraft.block.Blocks
import net.minecraft.util.math.BlockPos
import net.minecraft.world.Heightmap
import net.minecraft.world.ServerWorldAccess
import net.minecraft.world.gen.StructureAccessor
import net.minecraft.world.gen.chunk.ChunkGenerator
import net.minecraft.world.gen.feature.DefaultFeatureConfig
import net.minecraft.world.gen.feature.Feature
import java.util.Random

class MireVegetationFeature(configCodec: Codec<DefaultFeatureConfig>) : Feature<DefaultFeatureConfig>(configCodec) {
    override fun generate(
        world: ServerWorldAccess, structureAccessor: StructureAccessor, generator: ChunkGenerator,
        random: Random, pos: BlockPos, config: DefaultFeatureConfig
    ): Boolean {
        val grass = Blocks.GRASS.defaultState
        val mut = BlockPos.Mutable()
        var generated = false

        for (x in 0..15) {
            for (z in 0..15) {
                if (random.nextBoolean()) continue

                val xo = pos.x + x
                val zo = pos.z + z
                mut.set(xo, world.getTopY(Heightmap.Type.MOTION_BLOCKING, xo, zo), zo)

                if (world.isAir(mut) && grass.canPlaceAt(world, mut)) {
                    world.setBlockState(mut, grass, 2)
                    generated = true
                }
            }
        }

        return generated
    }
}
