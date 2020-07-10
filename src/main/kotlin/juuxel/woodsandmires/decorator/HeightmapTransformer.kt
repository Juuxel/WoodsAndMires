package juuxel.woodsandmires.decorator

import net.minecraft.class_5444
import net.minecraft.util.math.BlockPos
import net.minecraft.world.Heightmap
import java.util.Random
import java.util.stream.Stream

class HeightmapTransformer(private val type: Heightmap.Type) : DecoratorTransformer {
    override fun transform(
        context: class_5444,
        random: Random,
        positions: Stream<BlockPos>
    ): Stream<BlockPos> = positions.map { BlockPos(it.x, context.method_30460(type, it.x, it.z), it.z) }
}
