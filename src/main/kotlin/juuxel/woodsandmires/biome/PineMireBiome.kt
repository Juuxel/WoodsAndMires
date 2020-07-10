package juuxel.woodsandmires.biome

import juuxel.woodsandmires.decorator.DecoratorTransformer
import juuxel.woodsandmires.decorator.transform
import juuxel.woodsandmires.feature.WamConfiguredFeatures
import net.minecraft.class_5464
import net.minecraft.class_5471
import net.minecraft.entity.EntityType
import net.minecraft.entity.SpawnGroup
import net.minecraft.sound.BiomeMoodSound
import net.minecraft.world.biome.Biome
import net.minecraft.world.biome.BiomeEffects
import net.minecraft.world.gen.GenerationStep
import net.minecraft.world.gen.decorator.ChanceDecoratorConfig
import net.minecraft.world.gen.decorator.CountExtraChanceDecoratorConfig
import net.minecraft.world.gen.decorator.Decorator
import net.minecraft.world.gen.decorator.DecoratorConfig
import net.minecraft.world.gen.feature.DefaultBiomeFeatures
import net.minecraft.world.gen.feature.SeaPickleFeatureConfig

class PineMireBiome(config: Settings.() -> Unit) : Biome(
    Settings()
        .configureSurfaceBuilder(class_5471.field_26338)
        .category(Category.SWAMP)
        .effects(
            BiomeEffects.Builder()
                .waterColor(0x7B6D1B)
                .waterFogColor(0x050533)
                .fogColor(0xC0D8FF)
                .moodSound(BiomeMoodSound.CAVE)
                .build()
        )
        .precipitation(Precipitation.RAIN)
        .downfall(0.9f)
        .temperature(0.6f)
        .apply(config)
) {
    init {
        DefaultBiomeFeatures.addLandCarvers(this)
        DefaultBiomeFeatures.addFossils(this)
        DefaultBiomeFeatures.addDungeons(this)
        DefaultBiomeFeatures.addMineables(this)
        DefaultBiomeFeatures.addDefaultOres(this)
        DefaultBiomeFeatures.addDefaultMushrooms(this)
        DefaultBiomeFeatures.addSwampVegetation(this)
        DefaultBiomeFeatures.addSprings(this)
        DefaultBiomeFeatures.addClay(this)
        DefaultBiomeFeatures.addSweetBerryBushes(this)
        DefaultBiomeFeatures.addDefaultFlowers(this)
        DefaultBiomeFeatures.addDefaultGrass(this)
        DefaultBiomeFeatures.addPlainsTallGrass(this)
        DefaultBiomeFeatures.addLargeFerns(this)

        addFeature(
            GenerationStep.Feature.VEGETAL_DECORATION,
            WamConfiguredFeatures.SHORT_PINE_SHRUB
                .method_30374(
                    Decorator.COUNT_EXTRA.configure(CountExtraChanceDecoratorConfig(3, 0.3f, 3))
                        .transform(
                            DecoratorTransformer.CHUNK_OFFSET,
                            DecoratorTransformer.MOTION_BLOCKING_HEIGHTMAP
                        )
                )
        )

        addFeature(
            GenerationStep.Feature.LAKES,
            WamConfiguredFeatures.MIRE_PONDS
                .method_30374(Decorator.NOPE.configure(DecoratorConfig.DEFAULT))
        )

        addFeature(
            GenerationStep.Feature.TOP_LAYER_MODIFICATION,
            WamConfiguredFeatures.MIRE_MEADOW
                .method_30374(Decorator.NOPE.configure(DecoratorConfig.DEFAULT))
        )

        addFeature(
            GenerationStep.Feature.VEGETAL_DECORATION,
            WamConfiguredFeatures.MIRE_FLOWERS
                .method_30374(
                    Decorator.COUNT.configure(SeaPickleFeatureConfig(3))
                        .transform(
                            DecoratorTransformer.CHUNK_OFFSET,
                            DecoratorTransformer.HEIGHT_OFFSET_32
                        )
                )
        )

        addFeature(
            GenerationStep.Feature.VEGETAL_DECORATION,
            class_5464.field_25984
                .method_30374(
                    Decorator.COUNT.configure(SeaPickleFeatureConfig(4))
                        .transform(
                            DecoratorTransformer.CHUNK_OFFSET,
                            DecoratorTransformer.HEIGHT_SCALE_DOUBLE
                        )
                )
        )

        addFeature(
            GenerationStep.Feature.VEGETAL_DECORATION,
            WamConfiguredFeatures.PINE_SNAG
                .method_30374(
                    Decorator.CHANCE.configure(ChanceDecoratorConfig(6))
                        .transform(
                            DecoratorTransformer.CHUNK_OFFSET,
                            DecoratorTransformer.TOP_SOLID_HEIGHTMAP
                        )
                )
        )

        addSpawn(SpawnGroup.CREATURE, SpawnEntry(EntityType.SHEEP, 12, 4, 4))
        addSpawn(SpawnGroup.CREATURE, SpawnEntry(EntityType.PIG, 10, 4, 4))
        addSpawn(SpawnGroup.CREATURE, SpawnEntry(EntityType.CHICKEN, 10, 4, 4))
        addSpawn(SpawnGroup.CREATURE, SpawnEntry(EntityType.COW, 8, 4, 4))
        addSpawn(SpawnGroup.AMBIENT, SpawnEntry(EntityType.BAT, 10, 8, 8))
        addSpawn(SpawnGroup.MONSTER, SpawnEntry(EntityType.SPIDER, 100, 4, 4))
        addSpawn(SpawnGroup.MONSTER, SpawnEntry(EntityType.ZOMBIE, 95, 4, 4))
        addSpawn(SpawnGroup.MONSTER, SpawnEntry(EntityType.ZOMBIE_VILLAGER, 5, 1, 1))
        addSpawn(SpawnGroup.MONSTER, SpawnEntry(EntityType.SKELETON, 100, 4, 4))
        addSpawn(SpawnGroup.MONSTER, SpawnEntry(EntityType.CREEPER, 100, 4, 4))
        addSpawn(SpawnGroup.MONSTER, SpawnEntry(EntityType.SLIME, 100, 4, 4))
        addSpawn(SpawnGroup.MONSTER, SpawnEntry(EntityType.ENDERMAN, 10, 1, 4))
        addSpawn(SpawnGroup.MONSTER, SpawnEntry(EntityType.WITCH, 5, 1, 1))
        addSpawn(SpawnGroup.MONSTER, SpawnEntry(EntityType.SLIME, 1, 1, 1))
    }

    override fun getFoliageColor() = 0xBFA243

    override fun getGrassColorAt(x: Double, z: Double) = 0xADA24C
}
