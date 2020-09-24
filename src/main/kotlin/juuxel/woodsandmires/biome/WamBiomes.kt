@file:Suppress("SameParameterValue", "deprecation")

package juuxel.woodsandmires.biome

import com.mojang.serialization.Lifecycle
import juuxel.woodsandmires.WoodsAndMires
import juuxel.woodsandmires.feature.WamConfiguredFeatures
import juuxel.woodsandmires.mixin.DefaultBiomeCreatorAccessor
import net.fabricmc.fabric.api.biome.v1.OverworldBiomes
import net.fabricmc.fabric.api.biome.v1.OverworldClimate
import net.fabricmc.fabric.impl.biome.InternalBiomeUtils
import net.minecraft.block.Blocks
import net.minecraft.entity.EntityType
import net.minecraft.entity.SpawnGroup
import net.minecraft.sound.BiomeMoodSound
import net.minecraft.util.registry.BuiltinRegistries
import net.minecraft.util.registry.MutableRegistry
import net.minecraft.util.registry.Registry
import net.minecraft.util.registry.RegistryKey
import net.minecraft.world.biome.Biome
import net.minecraft.world.biome.BiomeEffects
import net.minecraft.world.biome.GenerationSettings
import net.minecraft.world.biome.SpawnSettings
import net.minecraft.world.gen.GenerationStep
import net.minecraft.world.gen.decorator.CountExtraDecoratorConfig
import net.minecraft.world.gen.decorator.Decorator
import net.minecraft.world.gen.decorator.DecoratorConfig
import net.minecraft.world.gen.feature.ConfiguredFeatures
import net.minecraft.world.gen.feature.DefaultBiomeFeatures
import net.minecraft.world.gen.feature.Feature
import net.minecraft.world.gen.feature.SingleStateFeatureConfig
import net.minecraft.world.gen.surfacebuilder.ConfiguredSurfaceBuilders

object WamBiomes {
    val PINE_FOREST = key("pine_forest")
    val PINE_FOREST_HILLS = key("pine_forest_hills")
    val PINE_FOREST_CLEARING = key("pine_forest_clearing")
    val PINE_MIRE = key("pine_mire")
    val KETTLE_POND = key("kettle_pond")

    fun init() {
        register(PINE_FOREST, pineForest(depth = 0.1f, scale = 0.2f))
        register(PINE_FOREST_HILLS, pineForest(depth = 0.45f, scale = 0.3f))
        register(PINE_FOREST_CLEARING, pineForestClearing(depth = 0.1f, scale = 0.2f))
        register(PINE_MIRE, pineMire(depth = 0f, scale = -0.1f))
        register(KETTLE_POND, kettlePond(depth = -0.3f, scale = 0f))

        OverworldBiomes.addContinentalBiome(PINE_FOREST, OverworldClimate.COOL, 1.0)
        OverworldBiomes.addHillsBiome(PINE_FOREST, PINE_FOREST_HILLS, 1.0)
        OverworldBiomes.addBiomeVariant(PINE_FOREST, PINE_FOREST_HILLS, 0.3)
        OverworldBiomes.addContinentalBiome(PINE_MIRE, OverworldClimate.TEMPERATE, 1.0)
    }

    private fun key(id: String): RegistryKey<Biome> = RegistryKey.of(Registry.BIOME_KEY, WoodsAndMires.id(id))

    private fun register(key: RegistryKey<Biome>, biome: Biome) {
        (BuiltinRegistries.BIOME as MutableRegistry<Biome>).add(key, biome, Lifecycle.stable())

        // Ensures that the biome is stored in the internal raw ID map of BuiltinBiomes.
        // This wouldn't usually be needed, but some of my biomes don't go through OverworldBiomes at all,
        // which means this won't get called.
        InternalBiomeUtils.ensureIdMapping(key)
    }

    private fun getSkyColor(temperature: Float): Int = DefaultBiomeCreatorAccessor.callGetSkyColor(temperature)

    private fun pineForest(depth: Float, scale: Float, generationSettingsConfig: GenerationSettings.Builder.() -> Unit): Biome {
        val generationSettings = generationSettings {
            surfaceBuilder(ConfiguredSurfaceBuilders.GRASS)

            DefaultBiomeFeatures.addDefaultUndergroundStructures(this)
            DefaultBiomeFeatures.addLandCarvers(this)
            DefaultBiomeFeatures.addDefaultLakes(this)
            DefaultBiomeFeatures.addDungeons(this)
            DefaultBiomeFeatures.addForestFlowers(this)
            DefaultBiomeFeatures.addMineables(this)
            DefaultBiomeFeatures.addDefaultOres(this)
            DefaultBiomeFeatures.addDefaultDisks(this)
            DefaultBiomeFeatures.addDefaultFlowers(this)
            DefaultBiomeFeatures.addForestGrass(this)
            DefaultBiomeFeatures.addDefaultMushrooms(this)
            DefaultBiomeFeatures.addDefaultVegetation(this)
            DefaultBiomeFeatures.addSprings(this)
            DefaultBiomeFeatures.addFrozenTopLayer(this)
            DefaultBiomeFeatures.addSweetBerryBushes(this)
            DefaultBiomeFeatures.addLargeFerns(this)

            // Stone boulders
            feature(
                GenerationStep.Feature.LOCAL_MODIFICATIONS,
                Feature.FOREST_ROCK.configure(SingleStateFeatureConfig(Blocks.STONE.defaultState))
                    .decorate(ConfiguredFeatures.Decorators.SQUARE_TOP_SOLID_HEIGHTMAP.applyChance(16))
            )

            generationSettingsConfig()
        }

        val spawnSettings = spawnSettings {
            DefaultBiomeFeatures.addFarmAnimals(this)
            DefaultBiomeFeatures.addBatsAndMonsters(this)

            spawn(SpawnGroup.CREATURE, SpawnSettings.SpawnEntry(EntityType.WOLF, 5, 4, 4))
            spawn(SpawnGroup.CREATURE, SpawnSettings.SpawnEntry(EntityType.FOX, 4, 2, 4))
        }

        return Biome.Builder()
            .category(Biome.Category.FOREST)
            .effects(
                BiomeEffects.Builder()
                    .waterColor(0x3F76E4)
                    .waterFogColor(0x050533)
                    .fogColor(0xC0D8FF)
                    .foliageColor(0x43C44F)
                    .skyColor(getSkyColor(0.4f))
                    .moodSound(BiomeMoodSound.CAVE)
                    .build()
            )
            .precipitation(Biome.Precipitation.RAIN)
            .downfall(0.6f)
            .temperature(0.4f)
            .depth(depth)
            .scale(scale)
            .generationSettings(generationSettings)
            .spawnSettings(spawnSettings)
            .build()
    }

    private fun pineForest(depth: Float, scale: Float): Biome = pineForest(depth, scale) {
        feature(
            GenerationStep.Feature.VEGETAL_DECORATION,
            WamConfiguredFeatures.PINE
                .decorate(ConfiguredFeatures.Decorators.SQUARE_HEIGHTMAP.repeat(10))
        )
    }

    private fun pineForestClearing(depth: Float, scale: Float): Biome = pineForest(depth, scale) {
        DefaultBiomeFeatures.addMossyRocks(this)
        DefaultBiomeFeatures.addPlainsFeatures(this)
        DefaultBiomeFeatures.addExtraDefaultFlowers(this)

        feature(
            GenerationStep.Feature.VEGETAL_DECORATION,
            WamConfiguredFeatures.TALL_PINE_SHRUB
                .decorate(
                    ConfiguredFeatures.Decorators.SQUARE_HEIGHTMAP
                        .decorate(Decorator.COUNT_EXTRA.configure(CountExtraDecoratorConfig(4, 0.3f, 3)))
                )
        )

        feature(
            GenerationStep.Feature.VEGETAL_DECORATION,
            WamConfiguredFeatures.PINE_SNAG
                .decorate(ConfiguredFeatures.Decorators.SQUARE_TOP_SOLID_HEIGHTMAP.applyChance(2))
        )

        feature(
            GenerationStep.Feature.VEGETAL_DECORATION,
            ConfiguredFeatures.BIRCH_BEES_005
                .decorate(ConfiguredFeatures.Decorators.SQUARE_TOP_SOLID_HEIGHTMAP.applyChance(3))
        )

        feature(
            GenerationStep.Feature.TOP_LAYER_MODIFICATION,
            WamConfiguredFeatures.CLEARING_MEADOW
                .decorate(Decorator.NOPE.configure(DecoratorConfig.DEFAULT))
        )

        feature(
            GenerationStep.Feature.VEGETAL_DECORATION,
            WamConfiguredFeatures.PLAINS_FLOWERS
                .decorate(ConfiguredFeatures.Decorators.SQUARE_TOP_SOLID_HEIGHTMAP.applyChance(4))
        )
    }

    private fun pineMire(depth: Float, scale: Float): Biome {
        val generationSettings = generationSettings {
            surfaceBuilder(ConfiguredSurfaceBuilders.SWAMP)

            feature(
                GenerationStep.Feature.VEGETAL_DECORATION,
                WamConfiguredFeatures.SHORT_PINE_SHRUB
                    .decorate(
                        ConfiguredFeatures.Decorators.SQUARE_HEIGHTMAP
                            .decorate(Decorator.COUNT_EXTRA.configure(CountExtraDecoratorConfig(3, 0.3f, 3)))
                    )
            )

            feature(
                GenerationStep.Feature.LAKES,
                WamConfiguredFeatures.MIRE_PONDS
                    .decorate(Decorator.NOPE.configure(DecoratorConfig.DEFAULT))
            )

            feature(
                GenerationStep.Feature.TOP_LAYER_MODIFICATION,
                WamConfiguredFeatures.MIRE_MEADOW
                    .decorate(Decorator.NOPE.configure(DecoratorConfig.DEFAULT))
            )

            feature(
                GenerationStep.Feature.VEGETAL_DECORATION,
                WamConfiguredFeatures.MIRE_FLOWERS
                    .decorate(
                        ConfiguredFeatures.Decorators.SPREAD_32_ABOVE
                            .decorate(ConfiguredFeatures.Decorators.SQUARE_HEIGHTMAP)
                            .repeat(3)
                    )
            )

            feature(
                GenerationStep.Feature.VEGETAL_DECORATION,
                ConfiguredFeatures.PATCH_WATERLILLY
                    .decorate(ConfiguredFeatures.Decorators.SQUARE_HEIGHTMAP_SPREAD_DOUBLE.repeat(4))
            )

            feature(
                GenerationStep.Feature.VEGETAL_DECORATION,
                WamConfiguredFeatures.PINE_SNAG
                    .decorate(ConfiguredFeatures.Decorators.SQUARE_TOP_SOLID_HEIGHTMAP.applyChance(6))
            )
        }

        val spawnSettings = spawnSettings {
            DefaultBiomeFeatures.addFarmAnimals(this)
            DefaultBiomeFeatures.addBatsAndMonsters(this)
        }

        return Biome.Builder()
            .category(Biome.Category.SWAMP)
            .effects(
                BiomeEffects.Builder()
                    .waterColor(0x7B6D1B)
                    .waterFogColor(0x050533)
                    .fogColor(0xC0D8FF)
                    .moodSound(BiomeMoodSound.CAVE)
                    .foliageColor(0xBFA243)
                    .grassColor(0xADA24C)
                    .skyColor(getSkyColor(0.6f))
                    .build()
            )
            .precipitation(Biome.Precipitation.RAIN)
            .downfall(0.9f)
            .temperature(0.6f)
            .depth(depth)
            .scale(scale)
            .generationSettings(generationSettings)
            .spawnSettings(spawnSettings)
            .build()
    }

    private fun kettlePond(depth: Float, scale: Float): Biome {
        val generationSettings = generationSettings {
            surfaceBuilder(ConfiguredSurfaceBuilders.GRASS)

            DefaultBiomeFeatures.addLandCarvers(this)
            DefaultBiomeFeatures.addDefaultLakes(this)
            DefaultBiomeFeatures.addDungeons(this)
            DefaultBiomeFeatures.addMineables(this)
            DefaultBiomeFeatures.addDefaultOres(this)
            DefaultBiomeFeatures.addDefaultDisks(this)
            DefaultBiomeFeatures.addDefaultFlowers(this)
            DefaultBiomeFeatures.addForestGrass(this)
            DefaultBiomeFeatures.addDefaultMushrooms(this)
            DefaultBiomeFeatures.addDefaultVegetation(this)
            DefaultBiomeFeatures.addSprings(this)
            DefaultBiomeFeatures.addFrozenTopLayer(this)

            feature(
                GenerationStep.Feature.VEGETAL_DECORATION,
                WamConfiguredFeatures.SHORT_PINE_SHRUB
                    .decorate(
                        ConfiguredFeatures.Decorators.SQUARE_HEIGHTMAP
                            .decorate(Decorator.COUNT_EXTRA.configure(CountExtraDecoratorConfig(3, 0.3f, 3)))
                    )
            )
        }

        val spawnSettings = spawnSettings {
            DefaultBiomeFeatures.addFarmAnimals(this)
            DefaultBiomeFeatures.addBatsAndMonsters(this)
            spawn(SpawnGroup.WATER_CREATURE, SpawnSettings.SpawnEntry(EntityType.SQUID, 2, 1, 4))
            spawn(SpawnGroup.WATER_CREATURE, SpawnSettings.SpawnEntry(EntityType.SALMON, 5, 1, 5))
        }

        return Biome.Builder()
            .category(Biome.Category.RIVER)
            .effects(
                BiomeEffects.Builder()
                    .waterColor(0x3F7699)
                    .waterFogColor(0x050533)
                    .fogColor(0xC0D8FF)
                    .foliageColor(0x43C44F)
                    .skyColor(getSkyColor(0.4f))
                    .moodSound(BiomeMoodSound.CAVE)
                    .build()
            )
            .precipitation(Biome.Precipitation.RAIN)
            .downfall(0.8f)
            .temperature(0.4f)
            .depth(depth)
            .scale(scale)
            .generationSettings(generationSettings)
            .spawnSettings(spawnSettings)
            .build()
    }

    private inline fun generationSettings(fn: GenerationSettings.Builder.() -> Unit): GenerationSettings =
        GenerationSettings.Builder().apply(fn).build()

    private inline fun spawnSettings(fn: SpawnSettings.Builder.() -> Unit): SpawnSettings =
        SpawnSettings.Builder().apply(fn).build()
}
