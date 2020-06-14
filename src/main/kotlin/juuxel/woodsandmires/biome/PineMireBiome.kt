package juuxel.woodsandmires.biome

import juuxel.woodsandmires.feature.MeadowFeatureConfig
import juuxel.woodsandmires.feature.PineShrubFeatureConfig
import juuxel.woodsandmires.feature.WamFeatures
import net.minecraft.block.Blocks
import net.minecraft.entity.EntityType
import net.minecraft.entity.SpawnGroup
import net.minecraft.sound.BiomeMoodSound
import net.minecraft.world.biome.Biome
import net.minecraft.world.biome.BiomeEffects
import net.minecraft.world.gen.GenerationStep
import net.minecraft.world.gen.decorator.ChanceDecoratorConfig
import net.minecraft.world.gen.decorator.CountDecoratorConfig
import net.minecraft.world.gen.decorator.CountExtraChanceDecoratorConfig
import net.minecraft.world.gen.decorator.Decorator
import net.minecraft.world.gen.decorator.DecoratorConfig
import net.minecraft.world.gen.feature.DefaultBiomeFeatures
import net.minecraft.world.gen.feature.Feature
import net.minecraft.world.gen.feature.FeatureConfig
import net.minecraft.world.gen.stateprovider.WeightedBlockStateProvider
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder

class PineMireBiome(config: Settings.() -> Unit) : Biome(
    Settings()
        .configureSurfaceBuilder(SurfaceBuilder.SWAMP, SurfaceBuilder.GRASS_CONFIG)
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
            WamFeatures.PINE_SHRUB.configure(PineShrubFeatureConfig(1, 2, 0.6f))
                .createDecoratedFeature(
                    Decorator.COUNT_EXTRA_HEIGHTMAP.configure(CountExtraChanceDecoratorConfig(3, 0.3f, 3))
                )
        )

        addFeature(
            GenerationStep.Feature.LAKES,
            WamFeatures.MIRE_PONDS.configure(FeatureConfig.DEFAULT)
                .createDecoratedFeature(Decorator.NOPE.configure(DecoratorConfig.DEFAULT))
        )

        val stateProvider = WeightedBlockStateProvider()
        stateProvider.addState(Blocks.GRASS.defaultState, 5)
        stateProvider.addState(Blocks.FERN.defaultState, 1)

        addFeature(
            GenerationStep.Feature.TOP_LAYER_MODIFICATION,
            WamFeatures.MEADOW.configure(MeadowFeatureConfig(stateProvider, 0.5f))
                .createDecoratedFeature(Decorator.NOPE.configure(DecoratorConfig.DEFAULT))
        )

        addFeature(
            GenerationStep.Feature.VEGETAL_DECORATION,
            Feature.FLOWER.configure(DefaultBiomeFeatures.BLUE_ORCHID_CONFIG)
                .createDecoratedFeature(Decorator.COUNT_HEIGHTMAP_32.configure(CountDecoratorConfig(3)))
        )

        addFeature(
            GenerationStep.Feature.VEGETAL_DECORATION,
            Feature.RANDOM_PATCH.configure(DefaultBiomeFeatures.LILY_PAD_CONFIG)
                .createDecoratedFeature(Decorator.COUNT_HEIGHTMAP_DOUBLE.configure(CountDecoratorConfig(4)))
        )

        addFeature(
            GenerationStep.Feature.VEGETAL_DECORATION,
            Feature.TREE.configure(WamFeatures.MIRE_PINE_TREE_CONFIG)
                .createDecoratedFeature(Decorator.CHANCE_TOP_SOLID_HEIGHTMAP.configure(ChanceDecoratorConfig(6)))
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
