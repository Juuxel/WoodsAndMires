package juuxel.woodsandmires.decorator

import juuxel.woodsandmires.util.mapNotNull
import net.minecraft.class_5444
import net.minecraft.util.math.BlockPos
import java.util.Random
import java.util.stream.Stream

class HeightTransformer(private val scale: Int = 1, private val offset: Int = 0): DecoratorTransformer {
    override fun transform(context: class_5444, random: Random, positions: Stream<BlockPos>): Stream<BlockPos> =
        positions.mapNotNull {
            val yp = it.y * scale + offset

            if (yp > 0) BlockPos(it.x, random.nextInt(yp), it.z)
            else null
        }
}
