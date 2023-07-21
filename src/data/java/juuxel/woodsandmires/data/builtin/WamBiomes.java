package juuxel.woodsandmires.data.builtin;

import juuxel.woodsandmires.biome.WamBiomeKeys;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.sound.BiomeMoodSound;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeEffects;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.biome.OverworldBiomeCreator;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.feature.VegetationPlacedFeatures;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DirectedAcyclicGraph;
import org.jgrapht.graph.GraphCycleProhibitedException;

import java.util.function.Consumer;

public final class WamBiomes {
    public static final RegistryCollector<RegistryEntry<Biome>> BIOMES = new RegistryCollector<>();
    private static final DirectedAcyclicGraph<RegistryEntry<PlacedFeature>, DefaultEdge> FEATURE_GRAPH =
        new DirectedAcyclicGraph<>(DefaultEdge.class);

    private WamBiomes() {
    }

    public static void register() {
        initGraph();
        register(WamBiomeKeys.PINE_FOREST, pineForest());
        register(WamBiomeKeys.SNOWY_PINE_FOREST, snowyPineForest());
        register(WamBiomeKeys.OLD_GROWTH_PINE_FOREST, oldGrowthPineForest());
        register(WamBiomeKeys.LUSH_PINE_FOREST, lushPineForest());
        register(WamBiomeKeys.PINE_MIRE, pineMire());
        register(WamBiomeKeys.FELL, fell());
        register(WamBiomeKeys.SNOWY_FELL, snowyFell());
        register(WamBiomeKeys.PINY_GROVE, pinyGrove());
    }

    private static void initGraph() {
        for (var biome : BuiltinRegistries.BIOME) {
            addBiomeToGraph(biome);
        }
    }

    private static void addBiomeToGraph(Biome biome) {
        for (var features : biome.getGenerationSettings().getFeatures()) {
            if (features.size() == 0) continue;

            var before = features.get(0);
            FEATURE_GRAPH.addVertex(before);

            for (int i = 1; i < features.size(); i++) {
                var after = features.get(i);
                FEATURE_GRAPH.addVertex(after);

                try {
                    FEATURE_GRAPH.addEdge(before, after);
                } catch (GraphCycleProhibitedException e) {
                    throw new IllegalStateException("Feature order cycle found between " + before.getKey() + " and " + after.getKey(), e);
                }

                before = after;
            }
        }
    }

    private static void register(RegistryKey<Biome> key, Biome biome) {
        BIOMES.add(BuiltinRegistries.add(BuiltinRegistries.BIOME, key, biome));

        try {
            addBiomeToGraph(biome);
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage() + " [biome=" + key.getValue() + "]", e);
        }
    }

    private static int getSkyColor(float temperature) {
        return OverworldBiomeCreator.getSkyColor(temperature);
    }

    private static Biome pineForest(Biome.Precipitation precipitation, float temperature,
                                    Consumer<WamGenerationSettingsBuilder> generationSettingsConfigurator) {
        GenerationSettings generationSettings = generationSettings(builder -> {
            OverworldBiomeCreator.addBasicFeatures(builder);
            DefaultBiomeFeatures.addForestFlowers(builder);
            DefaultBiomeFeatures.addLargeFerns(builder);
            DefaultBiomeFeatures.addDefaultOres(builder);
            DefaultBiomeFeatures.addDefaultDisks(builder);
            builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, WamPlacedFeatures.FALLEN_PINE);

            // Stone boulders
            builder.feature(GenerationStep.Feature.LOCAL_MODIFICATIONS, WamPlacedFeatures.PINE_FOREST_BOULDER);

            if (precipitation != Biome.Precipitation.SNOW) {
                DefaultBiomeFeatures.addDefaultFlowers(builder);
                builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, WamPlacedFeatures.PINE_FOREST_HEATHER_PATCH);
            }

            generationSettingsConfigurator.accept(builder);

            DefaultBiomeFeatures.addForestGrass(builder);
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
            .effects(
                new BiomeEffects.Builder()
                    .waterColor(OverworldBiomeCreator.DEFAULT_WATER_COLOR)
                    .waterFogColor(OverworldBiomeCreator.DEFAULT_WATER_FOG_COLOR)
                    .fogColor(OverworldBiomeCreator.DEFAULT_FOG_COLOR)
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
        return pineForest(Biome.Precipitation.RAIN, 0.6f, builder -> {
            builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, WamPlacedFeatures.FOREST_PINE);
        });
    }

    private static Biome snowyPineForest() {
        // noinspection CodeBlock2Expr
        return pineForest(Biome.Precipitation.SNOW, 0f, builder -> {
            builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, WamPlacedFeatures.SNOWY_PINE_FOREST_TREES);
        });
    }

    private static Biome oldGrowthPineForest() {
        return pineForest(Biome.Precipitation.RAIN, 0.4f, builder -> {
            builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, WamPlacedFeatures.OLD_GROWTH_PINE_FOREST_TREES);
        });
    }

    private static Biome lushPineForest() {
        return pineForest(Biome.Precipitation.RAIN, 0.6f, builder -> {
            DefaultBiomeFeatures.addSavannaTallGrass(builder);
            builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, WamPlacedFeatures.LUSH_PINE_FOREST_TREES);
            builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, WamPlacedFeatures.LUSH_PINE_FOREST_FLOWERS);
            DefaultBiomeFeatures.addExtraDefaultFlowers(builder);

            // https://github.com/Juuxel/WoodsAndMires/issues/14
            builder.addOrdering(
                GenerationStep.Feature.VEGETAL_DECORATION,
                VegetationPlacedFeatures.PATCH_GRASS_FOREST,
                VegetationPlacedFeatures.FLOWER_WARM
            );
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
            .effects(
                new BiomeEffects.Builder()
                    .waterColor(0x7B6D1B)
                    .waterFogColor(0x564C13)
                    .fogColor(OverworldBiomeCreator.DEFAULT_FOG_COLOR)
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

    private static Biome fell(Biome.Precipitation precipitation, float temperature, Consumer<GenerationSettings.Builder> generationSettingsConfigurator) {
        SpawnSettings spawnSettings = spawnSettings(builder -> {
            DefaultBiomeFeatures.addBatsAndMonsters(builder);

            builder.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.WOLF, 5, 4, 4));
            builder.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.FOX, 4, 2, 4));
        });
        GenerationSettings generationSettings = generationSettings(builder -> {
            OverworldBiomeCreator.addBasicFeatures(builder);
            DefaultBiomeFeatures.addDefaultOres(builder);
            DefaultBiomeFeatures.addDefaultDisks(builder);
            generationSettingsConfigurator.accept(builder);
        });

        return new Biome.Builder()
            .effects(
                new BiomeEffects.Builder()
                    .waterColor(OverworldBiomeCreator.DEFAULT_WATER_COLOR)
                    .waterFogColor(OverworldBiomeCreator.DEFAULT_WATER_FOG_COLOR)
                    .fogColor(OverworldBiomeCreator.DEFAULT_FOG_COLOR)
                    .skyColor(getSkyColor(temperature))
                    .moodSound(BiomeMoodSound.CAVE)
                    .build()
            )
            .precipitation(precipitation)
            .downfall(0.7f)
            .temperature(temperature)
            .generationSettings(generationSettings)
            .spawnSettings(spawnSettings)
            .build();
    }

    private static Biome fell() {
        return fell(Biome.Precipitation.RAIN, 0.25f, builder -> {
            DefaultBiomeFeatures.addDefaultFlowers(builder);
            builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, WamPlacedFeatures.FELL_HEATHER_PATCH);
            DefaultBiomeFeatures.addForestGrass(builder);
            DefaultBiomeFeatures.addDefaultMushrooms(builder);
            DefaultBiomeFeatures.addDefaultVegetation(builder);

            builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, WamPlacedFeatures.FELL_LICHEN);
            builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, WamPlacedFeatures.FELL_MOSS_PATCH);
            builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, WamPlacedFeatures.FELL_VEGETATION);
            builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, WamPlacedFeatures.FELL_BIRCH_SHRUB);
            builder.feature(GenerationStep.Feature.LOCAL_MODIFICATIONS, WamPlacedFeatures.FELL_BOULDER);
            builder.feature(GenerationStep.Feature.LAKES, WamPlacedFeatures.FELL_POND);
        });
    }

    private static Biome snowyFell() {
        return fell(Biome.Precipitation.SNOW, 0f, builder -> {
            builder.feature(GenerationStep.Feature.LOCAL_MODIFICATIONS, WamPlacedFeatures.FELL_BOULDER);
            builder.feature(GenerationStep.Feature.LAKES, WamPlacedFeatures.FELL_POND);
            builder.feature(GenerationStep.Feature.SURFACE_STRUCTURES, WamPlacedFeatures.FROZEN_TREASURE);
        });
    }

    private static Biome pinyGrove() {
        GenerationSettings generationSettings = generationSettings(builder -> {
            OverworldBiomeCreator.addBasicFeatures(builder);
            DefaultBiomeFeatures.addFrozenLavaSpring(builder);
            DefaultBiomeFeatures.addDefaultOres(builder);
            DefaultBiomeFeatures.addDefaultDisks(builder);
            builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, WamPlacedFeatures.PINY_GROVE_TREES);
            DefaultBiomeFeatures.addDefaultVegetation(builder);
            DefaultBiomeFeatures.addEmeraldOre(builder);
            DefaultBiomeFeatures.addInfestedStone(builder);
        });
        SpawnSettings spawnSettings = spawnSettings(builder -> {
            DefaultBiomeFeatures.addFarmAnimals(builder);
            builder.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.WOLF, 8, 4, 4))
                .spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.RABBIT, 4, 2, 3))
                .spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.FOX, 8, 2, 4));
            DefaultBiomeFeatures.addBatsAndMonsters(builder);
        });
        return new Biome.Builder()
            .effects(
                new BiomeEffects.Builder()
                    .waterColor(OverworldBiomeCreator.DEFAULT_WATER_COLOR)
                    .waterFogColor(OverworldBiomeCreator.DEFAULT_WATER_FOG_COLOR)
                    .fogColor(OverworldBiomeCreator.DEFAULT_FOG_COLOR)
                    .skyColor(getSkyColor(-0.2f))
                    .moodSound(BiomeMoodSound.CAVE)
                    .build()
            )
            .precipitation(Biome.Precipitation.SNOW)
            .downfall(0.8f)
            .temperature(-0.2f)
            .generationSettings(generationSettings)
            .spawnSettings(spawnSettings)
            .build();
    }

    private static GenerationSettings generationSettings(Consumer<? super WamGenerationSettingsBuilder> configurator) {
        WamGenerationSettingsBuilder builder = new WamGenerationSettingsBuilder();
        configurator.accept(builder);
        return builder.build(FEATURE_GRAPH);
    }

    private static SpawnSettings spawnSettings(Consumer<SpawnSettings.Builder> configurator) {
        SpawnSettings.Builder builder = new SpawnSettings.Builder();
        configurator.accept(builder);
        return builder.build();
    }
}
