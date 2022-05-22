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
    public static final RegistryKey<Biome> SNOWY_PINE_FOREST = key("snowy_pine_forest");
    public static final RegistryKey<Biome> OLD_GROWTH_PINE_FOREST = key("old_growth_pine_forest");
    public static final RegistryKey<Biome> PINE_FOREST_CLEARING = key("pine_forest_clearing");
    public static final RegistryKey<Biome> PINE_MIRE = key("pine_mire");
    public static final RegistryKey<Biome> FELL = key("fell");

    private WamBiomes() {
    }

    public static void init() {
        register(PINE_FOREST, pineForest());
        register(SNOWY_PINE_FOREST, snowyPineForest());
        register(OLD_GROWTH_PINE_FOREST, oldGrowthPineForest());
        register(PINE_FOREST_CLEARING, pineForestClearing());
        register(PINE_MIRE, pineMire());
        register(FELL, fell());
    }

    private static RegistryKey<Biome> key(String id) {
        return RegistryKey.of(Registry.BIOME_KEY, WoodsAndMires.id(id));
    }

    private static void register(RegistryKey<Biome> key, Biome biome) {
        BuiltinRegistries.add(BuiltinRegistries.BIOME, key, biome);
    }

    private static int getSkyColor(float temperature) {
        return OverworldBiomeCreator.getSkyColor(temperature);
    }

    private static Biome pineForest(Biome.Category category, Biome.Precipitation precipitation, float temperature,
                                    Consumer<GenerationSettings.Builder> earlyGenerationSettingsConfigurator,
                                    Consumer<GenerationSettings.Builder> generationSettingsConfigurator) {
        GenerationSettings generationSettings = generationSettings(builder -> {
            OverworldBiomeCreator.addBasicFeatures(builder);
            earlyGenerationSettingsConfigurator.accept(builder);
            DefaultBiomeFeatures.addForestFlowers(builder);
            DefaultBiomeFeatures.addLargeFerns(builder);
            DefaultBiomeFeatures.addDefaultOres(builder);
            DefaultBiomeFeatures.addDefaultDisks(builder);
            if (precipitation != Biome.Precipitation.SNOW) {
                DefaultBiomeFeatures.addDefaultFlowers(builder);
            }
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
            .category(category)
            .effects(
                new BiomeEffects.Builder()
                    .waterColor(0x3F76E4)
                    .waterFogColor(0x050533)
                    .fogColor(0xC0D8FF)
                    .foliageColor(0x43C44F)
                    .skyColor(getSkyColor(temperature))
                    .moodSound(BiomeMoodSound.CAVE)
                    .build()
            )
            .precipitation(precipitation)
            .downfall(0.6f)
            .temperature(temperature)
            .generationSettings(generationSettings)
            .spawnSettings(spawnSettings)
            .build();
    }

    private static Biome pineForest() {
        // noinspection CodeBlock2Expr
        return pineForest(Biome.Category.FOREST, Biome.Precipitation.RAIN, 0.6f, builder -> {}, builder -> {
            builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, WamPlacedFeatures.FOREST_PINE);
        });
    }

    private static Biome snowyPineForest() {
        // noinspection CodeBlock2Expr
        return pineForest(Biome.Category.FOREST, Biome.Precipitation.SNOW, 0f, builder -> {}, builder -> {
            builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, WamPlacedFeatures.SNOWY_FOREST_PINE);
        });
    }

    private static Biome oldGrowthPineForest() {
        return pineForest(Biome.Category.FOREST, Biome.Precipitation.RAIN, 0.4f, builder -> {}, builder -> {
            builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, WamPlacedFeatures.GIANT_PINE);
            builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, WamPlacedFeatures.OLD_GROWTH_FOREST_PINE);
        });
    }

    private static Biome pineForestClearing() {
        return pineForest(Biome.Category.PLAINS, Biome.Precipitation.RAIN, 0.6f, builder -> {
            DefaultBiomeFeatures.addSavannaTallGrass(builder);
        }, builder -> {
            DefaultBiomeFeatures.addMossyRocks(builder);
            DefaultBiomeFeatures.addPlainsFeatures(builder);
            DefaultBiomeFeatures.addExtraDefaultFlowers(builder);

            builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, WamPlacedFeatures.CLEARING_FALLEN_PINE);
            builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, WamPlacedFeatures.CLEARING_PINE_SHRUB);
            builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, WamPlacedFeatures.CLEARING_SNAG);
            builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, WamPlacedFeatures.CLEARING_BIRCH);
            builder.feature(GenerationStep.Feature.TOP_LAYER_MODIFICATION, WamPlacedFeatures.CLEARING_MEADOW);
            builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, WamPlacedFeatures.CLEARING_FLOWERS);
        });
    }

    private static Biome pineMire() {
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
            .generationSettings(generationSettings)
            .spawnSettings(spawnSettings)
            .build();
    }

    private static Biome fell(GenerationSettings generationSettings) {
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
            .generationSettings(generationSettings)
            .spawnSettings(spawnSettings)
            .build();
    }

    private static Biome fell() {
        return fell(generationSettings(builder -> {
            OverworldBiomeCreator.addBasicFeatures(builder);
            DefaultBiomeFeatures.addDefaultOres(builder);
            DefaultBiomeFeatures.addDefaultDisks(builder);
            DefaultBiomeFeatures.addDefaultFlowers(builder);
            DefaultBiomeFeatures.addForestGrass(builder);
            DefaultBiomeFeatures.addDefaultMushrooms(builder);
            DefaultBiomeFeatures.addDefaultVegetation(builder);

            builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, WamPlacedFeatures.FELL_VEGETATION);
            builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, WamPlacedFeatures.FELL_BIRCH_SHRUB);
            builder.feature(GenerationStep.Feature.LOCAL_MODIFICATIONS, WamPlacedFeatures.FELL_BOULDER);
            builder.feature(GenerationStep.Feature.LAKES, WamPlacedFeatures.FELL_POND);
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
