package juuxel.woodsandmires.biome;

import com.mojang.serialization.Lifecycle;
import juuxel.woodsandmires.WoodsAndMires;
import juuxel.woodsandmires.feature.WamConfiguredFeatures;
import net.fabricmc.fabric.api.biome.v1.OverworldBiomes;
import net.fabricmc.fabric.api.biome.v1.OverworldClimate;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.sound.BiomeMoodSound;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.MutableRegistry;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeEffects;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.biome.BuiltinBiomes;
import net.minecraft.world.biome.DefaultBiomeCreator;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.ConfiguredFeatures;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;
import net.minecraft.world.gen.surfacebuilder.ConfiguredSurfaceBuilders;

import java.util.function.Consumer;

public final class WamBiomes {
    public static final RegistryKey<Biome> PINE_FOREST = key("pine_forest");
    public static final RegistryKey<Biome> PINE_FOREST_HILLS = key("pine_forest_hills");
    public static final RegistryKey<Biome> PINE_FOREST_CLEARING = key("pine_forest_clearing");
    public static final RegistryKey<Biome> PINE_MIRE = key("pine_mire");
    public static final RegistryKey<Biome> KETTLE_POND = key("kettle_pond");
    public static final RegistryKey<Biome> FELL = key("fell");
    public static final RegistryKey<Biome> FELL_EDGE = key("fell_edge");

    private WamBiomes() {
    }

    @SuppressWarnings("deprecation") // bad fabric api
    public static void init() {
        register(PINE_FOREST, pineForest(0.1f, 0.2f));
        register(PINE_FOREST_HILLS, pineForest(0.45f, 0.3f));
        register(PINE_FOREST_CLEARING, pineForestClearing(0.1f, 0.2f));
        register(PINE_MIRE, pineMire(0f, -0.1f));
        register(KETTLE_POND, kettlePond(-0.3f, 0f));
        register(FELL, fell(0.85f, 0.3f));
        register(FELL_EDGE, fellEdge(0.5f, 0.3f));

        OverworldBiomes.addContinentalBiome(PINE_FOREST, OverworldClimate.COOL, 1.0);
        OverworldBiomes.addHillsBiome(PINE_FOREST, PINE_FOREST_HILLS, 1.0);
        OverworldBiomes.addBiomeVariant(PINE_FOREST, PINE_FOREST_HILLS, 0.3);
        OverworldBiomes.addContinentalBiome(PINE_MIRE, OverworldClimate.TEMPERATE, 1.0);
        OverworldBiomes.addContinentalBiome(FELL, OverworldClimate.COOL, 1.0);
        OverworldBiomes.addHillsBiome(FELL, BiomeKeys.SNOWY_MOUNTAINS, 1.0);
        OverworldBiomes.addEdgeBiome(FELL, FELL_EDGE, 1.0);
    }

    private static RegistryKey<Biome> key(String id) {
        return RegistryKey.of(Registry.BIOME_KEY, WoodsAndMires.id(id));
    }

    private static void register(RegistryKey<Biome> key, Biome biome) {
        ((MutableRegistry<Biome>) BuiltinRegistries.BIOME).add(key, biome, Lifecycle.stable());

        // Ensures that the biome is stored in the internal raw ID map of BuiltinBiomes.
        // Fabric API usually does this, but some of my biomes don't go through OverworldBiomes at all,
        // which means that won't always get done.
        BuiltinBiomes.BY_RAW_ID.put(BuiltinRegistries.BIOME.getRawId(biome), key);
    }

    private static int getSkyColor(float temperature) {
        return DefaultBiomeCreator.getSkyColor(temperature);
    }

    private static Biome pineForest(float depth, float scale, Consumer<GenerationSettings.Builder> generationSettingsConfigurator) {
        GenerationSettings generationSettings = generationSettings(builder -> {
            builder.surfaceBuilder(ConfiguredSurfaceBuilders.GRASS);

            DefaultBiomeFeatures.addDefaultUndergroundStructures(builder);
            DefaultBiomeFeatures.addLandCarvers(builder);
            DefaultBiomeFeatures.addDefaultLakes(builder);
            DefaultBiomeFeatures.addDungeons(builder);
            DefaultBiomeFeatures.addForestFlowers(builder);
            DefaultBiomeFeatures.addMineables(builder);
            DefaultBiomeFeatures.addDefaultOres(builder);
            DefaultBiomeFeatures.addDefaultDisks(builder);
            DefaultBiomeFeatures.addDefaultFlowers(builder);
            DefaultBiomeFeatures.addForestGrass(builder);
            DefaultBiomeFeatures.addDefaultMushrooms(builder);
            DefaultBiomeFeatures.addDefaultVegetation(builder);
            DefaultBiomeFeatures.addSprings(builder);
            DefaultBiomeFeatures.addFrozenTopLayer(builder);
            DefaultBiomeFeatures.addSweetBerryBushes(builder);
            DefaultBiomeFeatures.addLargeFerns(builder);

            // Stone boulders
            builder.feature(GenerationStep.Feature.LOCAL_MODIFICATIONS, WamConfiguredFeatures.PINE_FOREST_BOULDER);

            generationSettingsConfigurator.accept(builder);
        });

        SpawnSettings spawnSettings = spawnSettings(builder -> {
            DefaultBiomeFeatures.addFarmAnimals(builder);
            DefaultBiomeFeatures.addBatsAndMonsters(builder);

            builder.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.WOLF, 5, 4, 4));
            builder.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.FOX, 4, 2, 4));
        });

        return new Biome.Builder()
            .category(Biome.Category.FOREST)
            .effects(
                new BiomeEffects.Builder()
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
            .build();
    }

    private static Biome pineForest(float depth, float scale) {
        // noinspection CodeBlock2Expr
        return pineForest(depth, scale, builder -> {
            builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, WamConfiguredFeatures.FOREST_PINE);
        });
    }

    private static Biome pineForestClearing(float depth, float scale) {
        return pineForest(depth, scale, builder -> {
            DefaultBiomeFeatures.addMossyRocks(builder);
            DefaultBiomeFeatures.addPlainsFeatures(builder);
            DefaultBiomeFeatures.addExtraDefaultFlowers(builder);

            builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, WamConfiguredFeatures.CLEARING_PINE_SHRUB);
            builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, WamConfiguredFeatures.CLEARING_SNAG);
            builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, WamConfiguredFeatures.CLEARING_BIRCH);
            builder.feature(GenerationStep.Feature.TOP_LAYER_MODIFICATION, WamConfiguredFeatures.CLEARING_MEADOW);
            builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, WamConfiguredFeatures.CLEARING_FLOWERS);
        });
    }

    private static Biome pineMire(float depth, float scale) {
        GenerationSettings generationSettings = generationSettings(builder -> {
            builder.surfaceBuilder(ConfiguredSurfaceBuilders.SWAMP);

            builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, WamConfiguredFeatures.MIRE_PINE_SHRUB);
            builder.feature(GenerationStep.Feature.LAKES, WamConfiguredFeatures.MIRE_PONDS);
            builder.feature(GenerationStep.Feature.TOP_LAYER_MODIFICATION, WamConfiguredFeatures.MIRE_MEADOW);
            builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, WamConfiguredFeatures.MIRE_FLOWERS);
            builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.PATCH_WATERLILLY);
            builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, WamConfiguredFeatures.MIRE_PINE_SNAG);
        });

        SpawnSettings spawnSettings = spawnSettings(builder -> {
            DefaultBiomeFeatures.addFarmAnimals(builder);
            DefaultBiomeFeatures.addBatsAndMonsters(builder);
        });

        return new Biome.Builder()
            .category(Biome.Category.SWAMP)
            .effects(
                new BiomeEffects.Builder()
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
            .build();
    }

    private static Biome kettlePond(float depth, float scale) {
        GenerationSettings generationSettings = generationSettings(builder -> {
            builder.surfaceBuilder(ConfiguredSurfaceBuilders.GRASS);

            DefaultBiomeFeatures.addLandCarvers(builder);
            DefaultBiomeFeatures.addDefaultLakes(builder);
            DefaultBiomeFeatures.addDungeons(builder);
            DefaultBiomeFeatures.addMineables(builder);
            DefaultBiomeFeatures.addDefaultOres(builder);
            DefaultBiomeFeatures.addDefaultDisks(builder);
            DefaultBiomeFeatures.addDefaultFlowers(builder);
            DefaultBiomeFeatures.addForestGrass(builder);
            DefaultBiomeFeatures.addDefaultMushrooms(builder);
            DefaultBiomeFeatures.addDefaultVegetation(builder);
            DefaultBiomeFeatures.addSprings(builder);
            DefaultBiomeFeatures.addFrozenTopLayer(builder);

            builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, WamConfiguredFeatures.KETTLE_POND_PINE_SHRUB);
        });

        SpawnSettings spawnSettings = spawnSettings(builder -> {
            DefaultBiomeFeatures.addFarmAnimals(builder);
            DefaultBiomeFeatures.addBatsAndMonsters(builder);
            builder.spawn(SpawnGroup.WATER_CREATURE, new SpawnSettings.SpawnEntry(EntityType.SQUID, 2, 1, 4));
            builder.spawn(SpawnGroup.WATER_CREATURE, new SpawnSettings.SpawnEntry(EntityType.SALMON, 5, 1, 5));
        });

        return new Biome.Builder()
            .category(Biome.Category.RIVER)
            .effects(
                new BiomeEffects.Builder()
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
            .build();
    }

    private static Biome fell(float depth, float scale, GenerationSettings generationSettings) {
        SpawnSettings spawnSettings = spawnSettings(builder -> {
            DefaultBiomeFeatures.addBatsAndMonsters(builder);

            builder.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.WOLF, 5, 4, 4));
            builder.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.FOX, 4, 2, 4));
        });

        return new Biome.Builder()
            .category(Biome.Category.EXTREME_HILLS)
            .effects(
                new BiomeEffects.Builder()
                    .waterColor(0x3F76E4)
                    .waterFogColor(0x050533)
                    .fogColor(0xC0D8FF)
                    .skyColor(getSkyColor(0.25f))
                    .moodSound(BiomeMoodSound.CAVE)
                    .build()
            )
            .precipitation(Biome.Precipitation.RAIN)
            .downfall(0.7f)
            .temperature(0.25f)
            .depth(depth)
            .scale(scale)
            .generationSettings(generationSettings)
            .spawnSettings(spawnSettings)
            .build();
    }

    private static Biome fell(float depth, float scale) {
        return fell(depth, scale, generationSettings(builder -> {
            builder.surfaceBuilder(ConfiguredSurfaceBuilders.SHATTERED_SAVANNA);

            DefaultBiomeFeatures.addLandCarvers(builder);
            DefaultBiomeFeatures.addDungeons(builder);
            DefaultBiomeFeatures.addMineables(builder);
            DefaultBiomeFeatures.addDefaultOres(builder);
            DefaultBiomeFeatures.addDefaultDisks(builder);
            DefaultBiomeFeatures.addDefaultFlowers(builder);
            DefaultBiomeFeatures.addDefaultMushrooms(builder);
            DefaultBiomeFeatures.addDefaultVegetation(builder);
            DefaultBiomeFeatures.addSprings(builder);
            DefaultBiomeFeatures.addFrozenTopLayer(builder);

            builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, WamConfiguredFeatures.FELL_VEGETATION);
            builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, WamConfiguredFeatures.FELL_BIRCH_SHRUB);
            builder.feature(GenerationStep.Feature.LOCAL_MODIFICATIONS, WamConfiguredFeatures.FELL_BOULDER);
            builder.feature(GenerationStep.Feature.LAKES, WamConfiguredFeatures.FELL_LAKE);
            builder.feature(GenerationStep.Feature.LAKES, ConfiguredFeatures.LAKE_LAVA);
        }));
    }

    private static Biome fellEdge(float depth, float scale) {
        return fell(depth, scale, generationSettings(builder -> {
            builder.surfaceBuilder(ConfiguredSurfaceBuilders.GRASS);

            DefaultBiomeFeatures.addLandCarvers(builder);
            DefaultBiomeFeatures.addDefaultLakes(builder);
            DefaultBiomeFeatures.addDungeons(builder);
            DefaultBiomeFeatures.addMineables(builder);
            DefaultBiomeFeatures.addDefaultOres(builder);
            DefaultBiomeFeatures.addDefaultDisks(builder);
            DefaultBiomeFeatures.addDefaultFlowers(builder);
            DefaultBiomeFeatures.addForestGrass(builder);
            DefaultBiomeFeatures.addDefaultMushrooms(builder);
            DefaultBiomeFeatures.addDefaultVegetation(builder);
            DefaultBiomeFeatures.addSprings(builder);
            DefaultBiomeFeatures.addFrozenTopLayer(builder);

            builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, WamConfiguredFeatures.FELL_VEGETATION);
            builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, WamConfiguredFeatures.CLEARING_PINE_SHRUB);
        }));
    }

    private static GenerationSettings generationSettings(Consumer<GenerationSettings.Builder> configurator) {
        GenerationSettings.Builder builder = new GenerationSettings.Builder();
        configurator.accept(builder);
        return builder.build();
    }

    private static SpawnSettings spawnSettings(Consumer<SpawnSettings.Builder> configurator) {
        SpawnSettings.Builder builder = new SpawnSettings.Builder();
        configurator.accept(builder);
        return builder.build();
    }
}
