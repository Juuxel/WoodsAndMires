@file:Suppress("SameParameterValue")

package juuxel.woodsandmires.biome

import juuxel.woodsandmires.WoodsAndMires
import juuxel.woodsandmires.feature.WamConfiguredFeatures
import juuxel.woodsandmires.mixin.DefaultBiomeCreatorAccessor
import net.fabricmc.fabric.api.biomes.v1.OverworldBiomes
import net.fabricmc.fabric.api.biomes.v1.OverworldClimate
import net.minecraft.block.Blocks
import net.minecraft.entity.EntityType
import net.minecraft.entity.SpawnGroup
import net.minecraft.sound.BiomeMoodSound
import net.minecraft.util.registry.BuiltinRegistries
import net.minecraft.util.registry.Registry
import net.minecraft.world.biome.Biome
import net.minecraft.world.biome.BiomeEffects
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
    // @formatter:off
    @JvmField val PINE_FOREST: Biome = pineForest(depth = 0.1f, scale = 0.2f)
    @JvmField val PINE_FOREST_HILLS: Biome = pineForest(depth = 0.45f, scale = 0.3f)
    @JvmField val PINE_FOREST_CLEARING: Biome = pineForestClearing(depth = 0.1f, scale = 0.2f)
    @JvmField val PINE_MIRE: Biome = pineMire(depth = 0f, scale = -0.1f)
    @JvmField val KETTLE_POND: Biome = kettlePond(depth = -0.3f, scale = 0f)
    // @formatter:on

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

    private fun getSkyColor(temperature: Float): Int = DefaultBiomeCreatorAccessor.callGetSkyColor(temperature)

    private fun basePineForest(depth: Float, scale: Float): Biome {
        val biome = Biome(
            Biome.Settings()
                .surfaceBuilder(ConfiguredSurfaceBuilders.GRASS)
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
        )

        biome.apply {
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
            addFeature(
                GenerationStep.Feature.LOCAL_MODIFICATIONS,
                Feature.FOREST_ROCK.configure(SingleStateFeatureConfig(Blocks.STONE.defaultState))
                    .decorate(ConfiguredFeatures.Decorators.field_26167.applyChance(16))
            )

            addSpawn(SpawnGroup.CREATURE, Biome.SpawnEntry(EntityType.SHEEP, 12, 4, 4))
            addSpawn(SpawnGroup.CREATURE, Biome.SpawnEntry(EntityType.PIG, 10, 4, 4))
            addSpawn(SpawnGroup.CREATURE, Biome.SpawnEntry(EntityType.CHICKEN, 10, 4, 4))
            addSpawn(SpawnGroup.CREATURE, Biome.SpawnEntry(EntityType.COW, 8, 4, 4))
            addSpawn(SpawnGroup.AMBIENT, Biome.SpawnEntry(EntityType.BAT, 10, 8, 8))
            addSpawn(SpawnGroup.CREATURE, Biome.SpawnEntry(EntityType.WOLF, 5, 4, 4))
            addSpawn(SpawnGroup.CREATURE, Biome.SpawnEntry(EntityType.FOX, 4, 2, 4))
            addSpawn(SpawnGroup.MONSTER, Biome.SpawnEntry(EntityType.SPIDER, 100, 4, 4))
            addSpawn(SpawnGroup.MONSTER, Biome.SpawnEntry(EntityType.ZOMBIE, 95, 4, 4))
            addSpawn(SpawnGroup.MONSTER, Biome.SpawnEntry(EntityType.ZOMBIE_VILLAGER, 5, 1, 1))
            addSpawn(SpawnGroup.MONSTER, Biome.SpawnEntry(EntityType.SKELETON, 100, 4, 4))
            addSpawn(SpawnGroup.MONSTER, Biome.SpawnEntry(EntityType.CREEPER, 100, 4, 4))
            addSpawn(SpawnGroup.MONSTER, Biome.SpawnEntry(EntityType.SLIME, 100, 4, 4))
            addSpawn(SpawnGroup.MONSTER, Biome.SpawnEntry(EntityType.ENDERMAN, 10, 1, 4))
            addSpawn(SpawnGroup.MONSTER, Biome.SpawnEntry(EntityType.WITCH, 5, 1, 1))
        }

        return biome
    }

    private fun pineForest(depth: Float, scale: Float): Biome = basePineForest(depth, scale).apply {
        addFeature(
            GenerationStep.Feature.VEGETAL_DECORATION,
            WamConfiguredFeatures.PINE
                .decorate(ConfiguredFeatures.Decorators.field_26165.repeat(10))
        )
    }

    private fun pineForestClearing(depth: Float, scale: Float): Biome = basePineForest(depth, scale).apply {
        DefaultBiomeFeatures.addMossyRocks(this)
        DefaultBiomeFeatures.addPlainsFeatures(this)
        DefaultBiomeFeatures.addExtraDefaultFlowers(this)

        addFeature(
            GenerationStep.Feature.VEGETAL_DECORATION,
            WamConfiguredFeatures.TALL_PINE_SHRUB
                .decorate(
                    ConfiguredFeatures.Decorators.field_26165
                        .decorate(Decorator.COUNT_EXTRA.configure(CountExtraDecoratorConfig(4, 0.3f, 3)))
                )
        )

        addFeature(
            GenerationStep.Feature.VEGETAL_DECORATION,
            WamConfiguredFeatures.PINE_SNAG
                .decorate(ConfiguredFeatures.Decorators.field_26167.applyChance(2))
        )

        addFeature(
            GenerationStep.Feature.VEGETAL_DECORATION,
            ConfiguredFeatures.BIRCH_BEES_005
                .decorate(ConfiguredFeatures.Decorators.field_26167.applyChance(3))
        )

        addFeature(
            GenerationStep.Feature.TOP_LAYER_MODIFICATION,
            WamConfiguredFeatures.CLEARING_MEADOW
                .decorate(Decorator.NOPE.configure(DecoratorConfig.DEFAULT))
        )

        addFeature(
            GenerationStep.Feature.VEGETAL_DECORATION,
            WamConfiguredFeatures.PLAINS_FLOWERS
                .decorate(ConfiguredFeatures.Decorators.field_26167.applyChance(4))
        )
    }

    private fun pineMire(depth: Float, scale: Float): Biome {
        val biome = Biome(
            Biome.Settings()
                .surfaceBuilder(ConfiguredSurfaceBuilders.SWAMP)
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
        )

        biome.apply {
            addFeature(
                GenerationStep.Feature.VEGETAL_DECORATION,
                WamConfiguredFeatures.SHORT_PINE_SHRUB
                    .decorate(
                        ConfiguredFeatures.Decorators.field_26165
                            .decorate(Decorator.COUNT_EXTRA.configure(CountExtraDecoratorConfig(3, 0.3f, 3)))
                    )
            )

            addFeature(
                GenerationStep.Feature.LAKES,
                WamConfiguredFeatures.MIRE_PONDS
                    .decorate(Decorator.NOPE.configure(DecoratorConfig.DEFAULT))
            )

            addFeature(
                GenerationStep.Feature.TOP_LAYER_MODIFICATION,
                WamConfiguredFeatures.MIRE_MEADOW
                    .decorate(Decorator.NOPE.configure(DecoratorConfig.DEFAULT))
            )

            addFeature(
                GenerationStep.Feature.VEGETAL_DECORATION,
                WamConfiguredFeatures.MIRE_FLOWERS
                    .decorate(
                        ConfiguredFeatures.Decorators.SPREAD_32_ABOVE
                            .decorate(ConfiguredFeatures.Decorators.field_26165)
                            .repeat(3)
                    )
            )

            addFeature(
                GenerationStep.Feature.VEGETAL_DECORATION,
                ConfiguredFeatures.PATCH_WATERLILLY
                    .decorate(ConfiguredFeatures.Decorators.field_26166.repeat(4))
            )

            addFeature(
                GenerationStep.Feature.VEGETAL_DECORATION,
                WamConfiguredFeatures.PINE_SNAG
                    .decorate(ConfiguredFeatures.Decorators.field_26167.applyChance(6))
            )

            addSpawn(SpawnGroup.CREATURE, Biome.SpawnEntry(EntityType.SHEEP, 12, 4, 4))
            addSpawn(SpawnGroup.CREATURE, Biome.SpawnEntry(EntityType.PIG, 10, 4, 4))
            addSpawn(SpawnGroup.CREATURE, Biome.SpawnEntry(EntityType.CHICKEN, 10, 4, 4))
            addSpawn(SpawnGroup.CREATURE, Biome.SpawnEntry(EntityType.COW, 8, 4, 4))
            addSpawn(SpawnGroup.AMBIENT, Biome.SpawnEntry(EntityType.BAT, 10, 8, 8))
            addSpawn(SpawnGroup.MONSTER, Biome.SpawnEntry(EntityType.SPIDER, 100, 4, 4))
            addSpawn(SpawnGroup.MONSTER, Biome.SpawnEntry(EntityType.ZOMBIE, 95, 4, 4))
            addSpawn(SpawnGroup.MONSTER, Biome.SpawnEntry(EntityType.ZOMBIE_VILLAGER, 5, 1, 1))
            addSpawn(SpawnGroup.MONSTER, Biome.SpawnEntry(EntityType.SKELETON, 100, 4, 4))
            addSpawn(SpawnGroup.MONSTER, Biome.SpawnEntry(EntityType.CREEPER, 100, 4, 4))
            addSpawn(SpawnGroup.MONSTER, Biome.SpawnEntry(EntityType.SLIME, 100, 4, 4))
            addSpawn(SpawnGroup.MONSTER, Biome.SpawnEntry(EntityType.ENDERMAN, 10, 1, 4))
            addSpawn(SpawnGroup.MONSTER, Biome.SpawnEntry(EntityType.WITCH, 5, 1, 1))
            addSpawn(SpawnGroup.MONSTER, Biome.SpawnEntry(EntityType.SLIME, 1, 1, 1))
        }

        return biome
    }

    private fun kettlePond(depth: Float, scale: Float): Biome {
        val biome = Biome(
            Biome.Settings()
                .surfaceBuilder(ConfiguredSurfaceBuilders.GRASS)
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
        )

        biome.apply {
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

            addFeature(
                GenerationStep.Feature.VEGETAL_DECORATION,
                WamConfiguredFeatures.SHORT_PINE_SHRUB
                    .decorate(
                        ConfiguredFeatures.Decorators.field_26165
                            .decorate(Decorator.COUNT_EXTRA.configure(CountExtraDecoratorConfig(3, 0.3f, 3)))
                    )
            )

            addSpawn(SpawnGroup.CREATURE, Biome.SpawnEntry(EntityType.SHEEP, 12, 4, 4))
            addSpawn(SpawnGroup.CREATURE, Biome.SpawnEntry(EntityType.PIG, 10, 4, 4))
            addSpawn(SpawnGroup.CREATURE, Biome.SpawnEntry(EntityType.CHICKEN, 10, 4, 4))
            addSpawn(SpawnGroup.CREATURE, Biome.SpawnEntry(EntityType.COW, 8, 4, 4))
            addSpawn(SpawnGroup.WATER_CREATURE, Biome.SpawnEntry(EntityType.SQUID, 2, 1, 4))
            addSpawn(SpawnGroup.WATER_CREATURE, Biome.SpawnEntry(EntityType.SALMON, 5, 1, 5))
            addSpawn(SpawnGroup.AMBIENT, Biome.SpawnEntry(EntityType.BAT, 10, 8, 8))
            addSpawn(SpawnGroup.MONSTER, Biome.SpawnEntry(EntityType.SPIDER, 100, 4, 4))
            addSpawn(SpawnGroup.MONSTER, Biome.SpawnEntry(EntityType.ZOMBIE, 95, 4, 4))
            addSpawn(SpawnGroup.MONSTER, Biome.SpawnEntry(EntityType.ZOMBIE_VILLAGER, 5, 1, 1))
            addSpawn(SpawnGroup.MONSTER, Biome.SpawnEntry(EntityType.SKELETON, 100, 4, 4))
            addSpawn(SpawnGroup.MONSTER, Biome.SpawnEntry(EntityType.CREEPER, 100, 4, 4))
            addSpawn(SpawnGroup.MONSTER, Biome.SpawnEntry(EntityType.SLIME, 100, 4, 4))
            addSpawn(SpawnGroup.MONSTER, Biome.SpawnEntry(EntityType.ENDERMAN, 10, 1, 4))
            addSpawn(SpawnGroup.MONSTER, Biome.SpawnEntry(EntityType.WITCH, 5, 1, 1))
            addSpawn(SpawnGroup.MONSTER, Biome.SpawnEntry(EntityType.DROWNED, 100, 1, 1))
        }

        return biome
    }
}
