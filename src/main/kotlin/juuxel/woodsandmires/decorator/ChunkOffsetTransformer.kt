package juuxel.woodsandmires.decorator

import net.minecraft.class_5444
import net.minecraft.util.math.BlockPos
import java.util.Random
import java.util.stream.Stream

class ChunkOffsetTransformer : DecoratorTransformer {
    override fun transform(context: class_5444, random: Random, positions: Stream<BlockPos>): Stream<BlockPos> =
        positions.map { it.add(random.nextInt(16), 0, random.nextInt(16)) }
}
