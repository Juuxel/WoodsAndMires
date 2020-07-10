package juuxel.woodsandmires.biome

import net.minecraft.world.biome.Biome
import net.minecraft.world.biome.layer.util.LayerRandomnessSource

// TODO: Move to raw IDs
object BiomeTransformations {
    fun transformMediumSubBiome(random: LayerRandomnessSource, biome: Biome): Biome =
        when {
            // Clearings
            biome === WamBiomes.PINE_FOREST && random.nextInt(3) == 0 -> WamBiomes.PINE_FOREST_CLEARING

            else -> biome
        }

    fun transformLargeSubBiome(random: LayerRandomnessSource, biome: Biome): Biome =
        when {
            // Wetland kettles
            biome === WamBiomes.PINE_FOREST_HILLS && random.nextInt(3) == 0 -> WamBiomes.PINE_MIRE

            else -> biome
        }

    fun transformSmallSubBiome(random: LayerRandomnessSource, biome: Biome): Biome =
        when {
            // Kettle ponds
            biome === WamBiomes.PINE_FOREST_HILLS && random.nextInt(3) == 0 -> WamBiomes.KETTLE_POND

            else -> biome
        }
}
