package juuxel.woodsandmires.decorator

import com.mojang.serialization.Codec
import com.mojang.serialization.Lifecycle
import juuxel.woodsandmires.WoodsAndMires
import net.minecraft.class_5444
import net.minecraft.util.math.BlockPos
import net.minecraft.util.registry.Registry
import net.minecraft.util.registry.RegistryKey
import net.minecraft.util.registry.SimpleRegistry
import net.minecraft.world.Heightmap
import java.util.Random
import java.util.stream.Stream

/**
 * A function that transforms the contents of a decorator's position stream.
 */
interface DecoratorTransformer {
    /**
     * Transforms the [positions] with the [context].
     */
    fun transform(context: class_5444, random: Random, positions: Stream<BlockPos>): Stream<BlockPos>

    companion object {
        /** The decorator transformer registry. */
        val REGISTRY: Registry<DecoratorTransformer> = SimpleRegistry(
            RegistryKey.ofRegistry(WoodsAndMires.id("decorator_transformers")), Lifecycle.stable()
        )
        /** The codec for decorator transformers. */
        val CODEC: Codec<DecoratorTransformer> = REGISTRY

        val CHUNK_OFFSET: DecoratorTransformer = ChunkOffsetTransformer()
        val WORLD_SURFACE_HEIGHTMAP: DecoratorTransformer = HeightmapTransformer(Heightmap.Type.WORLD_SURFACE_WG)
        val TOP_SOLID_HEIGHTMAP: DecoratorTransformer = HeightmapTransformer(Heightmap.Type.OCEAN_FLOOR_WG)
        val MOTION_BLOCKING_HEIGHTMAP: DecoratorTransformer = HeightmapTransformer(Heightmap.Type.MOTION_BLOCKING)
        val HEIGHT_OFFSET_32: DecoratorTransformer = HeightTransformer(offset = 32)
        val HEIGHT_SCALE_DOUBLE: DecoratorTransformer = HeightTransformer(scale = 2)

        fun init() {
            register("chunk_offset", CHUNK_OFFSET)
            register("world_surface_heightmap", WORLD_SURFACE_HEIGHTMAP)
            register("top_solid_heightmap", TOP_SOLID_HEIGHTMAP)
            register("motion_blocking_heightmap", MOTION_BLOCKING_HEIGHTMAP)
            register("height_offset_32", HEIGHT_OFFSET_32)
            register("height_scale_double", HEIGHT_SCALE_DOUBLE)
        }

        private fun register(id: String, transformer: DecoratorTransformer) {
            Registry.register(REGISTRY, WoodsAndMires.id(id), transformer)
        }
    }
}
