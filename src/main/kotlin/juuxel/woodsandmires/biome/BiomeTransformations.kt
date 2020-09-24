package juuxel.woodsandmires.biome

import net.minecraft.util.registry.BuiltinRegistries
import net.minecraft.util.registry.RegistryKey
import net.minecraft.world.biome.Biome
import net.minecraft.world.biome.layer.util.LayerRandomnessSource

object BiomeTransformations {
    private fun getRawId(key: RegistryKey<Biome>): Int =
        BuiltinRegistries.BIOME.getRawId(BuiltinRegistries.BIOME.getOrThrow(key))

    private val PINE_FOREST_ID = getRawId(WamBiomes.PINE_FOREST)
    private val PINE_FOREST_CLEARING_ID = getRawId(WamBiomes.PINE_FOREST_CLEARING)
    private val PINE_FOREST_HILLS_ID = getRawId(WamBiomes.PINE_FOREST_HILLS)
    private val PINE_MIRE_ID = getRawId(WamBiomes.PINE_MIRE)
    private val KETTLE_POND_ID = getRawId(WamBiomes.KETTLE_POND)

    fun transformMediumSubBiome(random: LayerRandomnessSource, biome: Int): Int =
        when {
            // Clearings
            biome == PINE_FOREST_ID && random.nextInt(3) == 0 -> PINE_FOREST_CLEARING_ID

            else -> biome
        }

    fun transformLargeSubBiome(random: LayerRandomnessSource, biome: Int): Int =
        when {
            // Wetland kettles
            biome == PINE_FOREST_HILLS_ID && random.nextInt(3) == 0 -> PINE_MIRE_ID

            else -> biome
        }

    fun transformSmallSubBiome(random: LayerRandomnessSource, biome: Int): Int =
        when {
            // Kettle ponds
            biome == PINE_FOREST_HILLS_ID && random.nextInt(3) == 0 -> KETTLE_POND_ID

            else -> biome
        }
}
