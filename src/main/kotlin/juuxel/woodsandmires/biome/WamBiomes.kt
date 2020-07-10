package juuxel.woodsandmires.biome

import juuxel.woodsandmires.WoodsAndMires
import net.fabricmc.fabric.api.biomes.v1.OverworldBiomes
import net.fabricmc.fabric.api.biomes.v1.OverworldClimate
import net.minecraft.util.registry.BuiltinRegistries
import net.minecraft.util.registry.Registry
import net.minecraft.world.biome.Biome

object WamBiomes {
    @JvmField val PINE_FOREST: Biome = PineForestBiome { depth(0.1f).scale(0.2f) }
    @JvmField val PINE_FOREST_HILLS: Biome = PineForestBiome { depth(0.45f).scale(0.3f) }
    @JvmField val PINE_FOREST_CLEARING: Biome = PineForestClearingBiome { depth(0.1f).scale(0.2f) }
    @JvmField val PINE_MIRE: Biome = PineMireBiome { depth(0f).scale(-0.1f) }
    @JvmField val KETTLE_POND: Biome = KettlePondBiome { depth(-0.3f).scale(0f) }

    fun init() {
        register("pine_forest", PINE_FOREST)
        register("pine_forest_hills", PINE_FOREST_HILLS)
        register("pine_forest_clearing", PINE_FOREST_CLEARING)
        register("pine_mire", PINE_MIRE)
        register("kettle_pond", KETTLE_POND)

        OverworldBiomes.addContinentalBiome(PINE_FOREST, OverworldClimate.COOL, 1.0)
        OverworldBiomes.addHillsBiome(PINE_FOREST, PINE_FOREST_HILLS, 1.0)
        OverworldBiomes.addBiomeVariant(PINE_FOREST, PINE_FOREST_HILLS, 0.3)
        OverworldBiomes.addContinentalBiome(PINE_MIRE, OverworldClimate.TEMPERATE, 1.0)
    }

    private fun register(id: String, biome: Biome) {
        Registry.register(BuiltinRegistries.BIOME, WoodsAndMires.id(id), biome)
    }
}
