package juuxel.woodsandmires.biome;

import juuxel.woodsandmires.WoodsAndMires;
import juuxel.woodsandmires.feature.WamPlacedFeatures;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.sound.BiomeMoodSound;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeEffects;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.biome.OverworldBiomeCreator;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;
import net.minecraft.world.gen.feature.VegetationPlacedFeatures;

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

    public static void init() {
        register(PINE_FOREST, pineForest(0.1f, 0.2f));
        register(PINE_FOREST_HILLS, pineForest(0.45f, 0.3f));
        register(PINE_FOREST_CLEARING, pineForestClearing(0.1f, 0.2f));
        register(PINE_MIRE, pineMire(0f, -0.1f));
        register(KETTLE_POND, kettlePond(-0.3f, 0f));
        register(FELL, fell(0.85f, 0.3f));
        register(FELL_EDGE, fellEdge(0.5f, 0.3f));
    }

    private static RegistryKey<Biome> key(String id) {
        return RegistryKey.of(Registry.BIOME_KEY, WoodsAndMires.id(id));
    }

    private static void register(RegistryKey<Biome> key, Biome biome) {
        BuiltinRegistries.set(BuiltinRegistries.BIOME, key, biome);
    }

    private static int getSkyColor(float temperature) {
        return OverworldBiomeCreator.getSkyColor(temperature);
    }

    private static Biome pineForest(float depth, float scale, Consumer<GenerationSettings.Builder> generationSettingsConfigurator) {
        GenerationSettings generationSettings = generationSettings(builder -> {
            OverworldBiomeCreator.addBasicFeatures(builder);
            DefaultBiomeFeatures.addForestFlowers(builder);
            DefaultBiomeFeatures.addLargeFerns(builder);
            DefaultBiomeFeatures.addDefaultOres(builder);
            DefaultBiomeFeatures.addDefaultDisks(builder);
            DefaultBiomeFeatures.addDefaultFlowers(builder);
            DefaultBiomeFeatures.addForestGrass(builder);

            // Stone boulders
            builder.feature(GenerationStep.Feature.LOCAL_MODIFICATIONS, WamPlacedFeatures.PINE_FOREST_BOULDER);

            generationSettingsConfigurator.accept(builder);

            DefaultBiomeFeatures.addDefaultMushrooms(builder);
            DefaultBiomeFeatures.addDefaultVegetation(builder);
            DefaultBiomeFeatures.addSweetBerryBushes(builder);
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
            //.depth(depth)
            //.scale(scale)
            .generationSettings(generationSettings)
            .spawnSettings(spawnSettings)
            .build();
    }

    private static Biome pineForest(float depth, float scale) {
        return pineForest(depth, scale, builder -> {
            builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, WamPlacedFeatures.FOREST_PINE);
        });
    }

    private static Biome pineForestClearing(float depth, float scale) {
        return pineForest(depth, scale, builder -> {
            DefaultBiomeFeatures.addMossyRocks(builder);
            DefaultBiomeFeatures.addPlainsFeatures(builder);
            DefaultBiomeFeatures.addExtraDefaultFlowers(builder);

            builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, WamPlacedFeatures.CLEARING_PINE_SHRUB);
            builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, WamPlacedFeatures.CLEARING_SNAG);
            builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, WamPlacedFeatures.CLEARING_BIRCH);
            builder.feature(GenerationStep.Feature.TOP_LAYER_MODIFICATION, WamPlacedFeatures.CLEARING_MEADOW);
            builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, WamPlacedFeatures.CLEARING_FLOWERS);
        });
    }

    private static Biome pineMire(float depth, float scale) {
        GenerationSettings generationSettings = generationSettings(builder -> {
            OverworldBiomeCreator.addBasicFeatures(builder);
            builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, WamPlacedFeatures.MIRE_PINE_SHRUB);
            builder.feature(GenerationStep.Feature.LAKES, WamPlacedFeatures.MIRE_PONDS);
            builder.feature(GenerationStep.Feature.TOP_LAYER_MODIFICATION, WamPlacedFeatures.MIRE_MEADOW);
            builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, WamPlacedFeatures.MIRE_FLOWERS);
            builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, VegetationPlacedFeatures.PATCH_WATERLILY);
            builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, WamPlacedFeatures.MIRE_PINE_SNAG);
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
            //.depth(depth)
            //.scale(scale)
            .generationSettings(generationSettings)
            .spawnSettings(spawnSettings)
            .build();
    }

    private static Biome kettlePond(float depth, float scale) {
        GenerationSettings generationSettings = generationSettings(builder -> {
            OverworldBiomeCreator.addBasicFeatures(builder);
            DefaultBiomeFeatures.addDefaultOres(builder);
            DefaultBiomeFeatures.addDefaultDisks(builder);
            DefaultBiomeFeatures.addDefaultFlowers(builder);
            DefaultBiomeFeatures.addForestGrass(builder);
            DefaultBiomeFeatures.addDefaultMushrooms(builder);
            DefaultBiomeFeatures.addDefaultVegetation(builder);

            builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, WamPlacedFeatures.KETTLE_POND_PINE_SHRUB);
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
            //.depth(depth)
            //.scale(scale)
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
            //.depth(depth)
            //.scale(scale)
            .generationSettings(generationSettings)
            .spawnSettings(spawnSettings)
            .build();
    }

    private static Biome fell(float depth, float scale) {
        return fell(depth, scale, generationSettings(builder -> {
            OverworldBiomeCreator.addBasicFeatures(builder);
            DefaultBiomeFeatures.addDefaultOres(builder);
            DefaultBiomeFeatures.addDefaultDisks(builder);
            DefaultBiomeFeatures.addDefaultFlowers(builder);
            DefaultBiomeFeatures.addDefaultMushrooms(builder);
            DefaultBiomeFeatures.addDefaultVegetation(builder);

            builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, WamPlacedFeatures.FELL_VEGETATION);
            builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, WamPlacedFeatures.FELL_BIRCH_SHRUB);
            builder.feature(GenerationStep.Feature.LOCAL_MODIFICATIONS, WamPlacedFeatures.FELL_BOULDER);
            builder.feature(GenerationStep.Feature.LAKES, WamPlacedFeatures.FELL_LAKE);
        }));
    }

    private static Biome fellEdge(float depth, float scale) {
        return fell(depth, scale, generationSettings(builder -> {
            OverworldBiomeCreator.addBasicFeatures(builder);
            DefaultBiomeFeatures.addDefaultOres(builder);
            DefaultBiomeFeatures.addDefaultDisks(builder);
            DefaultBiomeFeatures.addDefaultFlowers(builder);
            DefaultBiomeFeatures.addForestGrass(builder);
            DefaultBiomeFeatures.addDefaultMushrooms(builder);
            DefaultBiomeFeatures.addDefaultVegetation(builder);

            builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, WamPlacedFeatures.FELL_VEGETATION);
            builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, WamPlacedFeatures.CLEARING_PINE_SHRUB);
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
