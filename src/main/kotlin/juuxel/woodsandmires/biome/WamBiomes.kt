package juuxel.woodsandmires.biome

import juuxel.woodsandmires.WoodsAndMires
import net.fabricmc.fabric.api.biomes.v1.OverworldBiomes
import net.fabricmc.fabric.api.biomes.v1.OverworldClimate
import net.minecraft.util.registry.Registry
import net.minecraft.world.biome.Biome

object WamBiomes {
    val PINE_FOREST: Biome = PineForestBiome { depth(0.1f).scale(0.2f) }
    val PINE_FOREST_HILLS: Biome = PineForestBiome { depth(0.45f).scale(0.3f) }

    fun init() {
        register("pine_forest", PINE_FOREST)
        register("pine_forest_hills", PINE_FOREST_HILLS)

        OverworldBiomes.addContinentalBiome(PINE_FOREST, OverworldClimate.COOL, 1.0)
        OverworldBiomes.addHillsBiome(PINE_FOREST, PINE_FOREST_HILLS, 1.0)
    }

    private fun register(id: String, biome: Biome) {
        Registry.register(Registry.BIOME, WoodsAndMires.id(id), biome)
    }
}
