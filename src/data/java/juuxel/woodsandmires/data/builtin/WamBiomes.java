package juuxel.woodsandmires.data.builtin;

import com.mojang.serialization.Lifecycle;
import juuxel.woodsandmires.biome.WamBiomeKeys;
import juuxel.woodsandmires.feature.WamPlacedFeatureKeys;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.BiomeMoodSound;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeEffects;
import net.minecraft.world.biome.BuiltinBiomes;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.biome.OverworldBiomeCreator;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.carver.ConfiguredCarver;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.feature.VegetationPlacedFeatures;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DirectedAcyclicGraph;
import org.jgrapht.graph.GraphCycleProhibitedException;

import java.util.function.Consumer;

public final class WamBiomes {
    // See Biome.doesNotSnow (1.19.4)
    private static final float WARM_BIOME_MINIMUM_TEMPERATURE = 0.15f;

    private final Registerable<Biome> registerable;
    private final DirectedAcyclicGraph<RegistryEntry<PlacedFeature>, DefaultEdge> featureGraph =
        new DirectedAcyclicGraph<>(DefaultEdge.class);

    private WamBiomes(Registerable<Biome> registerable) {
        this.registerable = registerable;
        initGraph();
    }

    public static void register(Registerable<Biome> registerable) {
        new WamBiomes(registerable).registerAll();
    }

    private void registerAll() {
        register(WamBiomeKeys.PINE_FOREST, pineForest());
        register(WamBiomeKeys.SNOWY_PINE_FOREST, snowyPineForest());
        register(WamBiomeKeys.OLD_GROWTH_PINE_FOREST, oldGrowthPineForest());
        register(WamBiomeKeys.LUSH_PINE_FOREST, lushPineForest());
        register(WamBiomeKeys.PINE_MIRE, pineMire());
        register(WamBiomeKeys.FELL, fell());
        register(WamBiomeKeys.SNOWY_FELL, snowyFell());
        register(WamBiomeKeys.PINY_GROVE, pinyGrove());
    }

    private void initGraph() {
        record CapturingRegisterable<T>(Registerable<?> parent, Consumer<T> sink) implements Registerable<T> {
            @Override
            public RegistryEntry.Reference<T> register(RegistryKey<T> key, T value, Lifecycle lifecycle) {
                sink.accept(value);
                return null;
            }

            @Override
            public <S> RegistryEntryLookup<S> getRegistryLookup(RegistryKey<? extends Registry<? extends S>> registryRef) {
                return parent.getRegistryLookup(registryRef);
            }
        }

        BuiltinBiomes.bootstrap(new CapturingRegisterable<>(registerable, this::addBiomeToGraph));
    }

    private void addBiomeToGraph(Biome biome) {
        for (var features : biome.getGenerationSettings().getFeatures()) {
            if (features.size() == 0) continue;

            var before = features.get(0);
            featureGraph.addVertex(before);

            for (int i = 1; i < features.size(); i++) {
                var after = features.get(i);
                featureGraph.addVertex(after);

                try {
                    featureGraph.addEdge(before, after);
                } catch (GraphCycleProhibitedException e) {
                    throw new IllegalStateException("Feature order cycle found between " + before.getKey() + " and " + after.getKey(), e);
                }

                before = after;
            }
        }
    }

    private void register(RegistryKey<Biome> key, Biome biome) {
        registerable.register(key, biome);

        try {
            addBiomeToGraph(biome);
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage() + " [biome=" + key.getValue() + "]", e);
        }
    }

    private static int getSkyColor(float temperature) {
        return OverworldBiomeCreator.getSkyColor(temperature);
    }

    private Biome pineForest(float temperature, Consumer<WamGenerationSettingsBuilder> generationSettingsConfigurator) {
        GenerationSettings generationSettings = generationSettings(builder -> {
            OverworldBiomeCreator.addBasicFeatures(builder);
            DefaultBiomeFeatures.addForestFlowers(builder);
            DefaultBiomeFeatures.addLargeFerns(builder);
            DefaultBiomeFeatures.addDefaultOres(builder);
            DefaultBiomeFeatures.addDefaultDisks(builder);
            builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, WamPlacedFeatureKeys.FALLEN_PINE);

            // Stone boulders
            builder.feature(GenerationStep.Feature.LOCAL_MODIFICATIONS, WamPlacedFeatureKeys.PINE_FOREST_BOULDER);

            if (temperature >= WARM_BIOME_MINIMUM_TEMPERATURE) {
                DefaultBiomeFeatures.addDefaultFlowers(builder);
                builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, WamPlacedFeatureKeys.PINE_FOREST_HEATHER_PATCH);
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
            .downfall(0.6f)
            .temperature(temperature)
            .generationSettings(generationSettings)
            .spawnSettings(spawnSettings)
            .build();
    }

    private Biome pineForest() {
        // noinspection CodeBlock2Expr
        return pineForest(0.6f, builder -> {
            builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, WamPlacedFeatureKeys.FOREST_PINE);
        });
    }

    private Biome snowyPineForest() {
        // noinspection CodeBlock2Expr
        return pineForest(0f, builder -> {
            builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, WamPlacedFeatureKeys.SNOWY_PINE_FOREST_TREES);
        });
    }

    private Biome oldGrowthPineForest() {
        return pineForest(0.4f, builder -> {
            builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, WamPlacedFeatureKeys.OLD_GROWTH_PINE_FOREST_TREES);
        });
    }

    private Biome lushPineForest() {
        return pineForest(0.6f, builder -> {
            DefaultBiomeFeatures.addSavannaTallGrass(builder);
            builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, WamPlacedFeatureKeys.LUSH_PINE_FOREST_TREES);
            builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, WamPlacedFeatureKeys.LUSH_PINE_FOREST_FLOWERS);
            DefaultBiomeFeatures.addExtraDefaultFlowers(builder);

            // https://github.com/Juuxel/WoodsAndMires/issues/14
            builder.addOrdering(
                GenerationStep.Feature.VEGETAL_DECORATION,
                VegetationPlacedFeatures.PATCH_GRASS_FOREST,
                VegetationPlacedFeatures.FLOWER_WARM
            );

            // https://github.com/Juuxel/WoodsAndMires/issues/22
            builder.addOrdering(
                GenerationStep.Feature.VEGETAL_DECORATION,
                VegetationPlacedFeatures.PATCH_TALL_GRASS,
                VegetationPlacedFeatures.FLOWER_DEFAULT
            );
            builder.addOrdering(
                GenerationStep.Feature.VEGETAL_DECORATION,
                VegetationPlacedFeatures.PATCH_TALL_GRASS,
                VegetationPlacedFeatures.PATCH_LARGE_FERN
            );
        });
    }

    private Biome pineMire() {
        GenerationSettings generationSettings = generationSettings(builder -> {
            OverworldBiomeCreator.addBasicFeatures(builder);
            builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, WamPlacedFeatureKeys.MIRE_PINE_SHRUB);
            builder.feature(GenerationStep.Feature.LAKES, WamPlacedFeatureKeys.MIRE_PONDS);
            builder.feature(GenerationStep.Feature.TOP_LAYER_MODIFICATION, WamPlacedFeatureKeys.MIRE_MEADOW);
            builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, WamPlacedFeatureKeys.MIRE_FLOWERS);
            builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, VegetationPlacedFeatures.PATCH_WATERLILY);
            builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, WamPlacedFeatureKeys.MIRE_PINE_SNAG);
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
            .downfall(0.9f)
            .temperature(0.6f)
            .generationSettings(generationSettings)
            .spawnSettings(spawnSettings)
            .build();
    }

    private Biome fell(float temperature, Consumer<GenerationSettings.LookupBackedBuilder> generationSettingsConfigurator) {
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
            .downfall(0.7f)
            .temperature(temperature)
            .generationSettings(generationSettings)
            .spawnSettings(spawnSettings)
            .build();
    }

    private Biome fell() {
        return fell(0.25f, builder -> {
            DefaultBiomeFeatures.addDefaultFlowers(builder);
            builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, WamPlacedFeatureKeys.FELL_HEATHER_PATCH);
            DefaultBiomeFeatures.addForestGrass(builder);
            DefaultBiomeFeatures.addDefaultMushrooms(builder);
            DefaultBiomeFeatures.addDefaultVegetation(builder);

            builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, WamPlacedFeatureKeys.FELL_LICHEN);
            builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, WamPlacedFeatureKeys.FELL_MOSS_PATCH);
            builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, WamPlacedFeatureKeys.FELL_VEGETATION);
            builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, WamPlacedFeatureKeys.FELL_BIRCH_SHRUB);
            builder.feature(GenerationStep.Feature.LOCAL_MODIFICATIONS, WamPlacedFeatureKeys.FELL_BOULDER);
            builder.feature(GenerationStep.Feature.LAKES, WamPlacedFeatureKeys.FELL_POND);
        });
    }

    private Biome snowyFell() {
        return fell(0f, builder -> {
            builder.feature(GenerationStep.Feature.LOCAL_MODIFICATIONS, WamPlacedFeatureKeys.FELL_BOULDER);
            builder.feature(GenerationStep.Feature.LAKES, WamPlacedFeatureKeys.FELL_POND);
            builder.feature(GenerationStep.Feature.SURFACE_STRUCTURES, WamPlacedFeatureKeys.FROZEN_TREASURE);
        });
    }

    private Biome pinyGrove() {
        GenerationSettings generationSettings = generationSettings(builder -> {
            OverworldBiomeCreator.addBasicFeatures(builder);
            DefaultBiomeFeatures.addFrozenLavaSpring(builder);
            DefaultBiomeFeatures.addDefaultOres(builder);
            DefaultBiomeFeatures.addDefaultDisks(builder);
            builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, WamPlacedFeatureKeys.PINY_GROVE_TREES);
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
            .downfall(0.8f)
            .temperature(-0.2f)
            .generationSettings(generationSettings)
            .spawnSettings(spawnSettings)
            .build();
    }

    private GenerationSettings generationSettings(Consumer<WamGenerationSettingsBuilder> configurator) {
        RegistryEntryLookup<PlacedFeature> placedFeatures = registerable.getRegistryLookup(RegistryKeys.PLACED_FEATURE);
        RegistryEntryLookup<ConfiguredCarver<?>> configuredCarvers = registerable.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER);
        WamGenerationSettingsBuilder builder = new WamGenerationSettingsBuilder(placedFeatures, configuredCarvers);
        configurator.accept(builder);
        return builder.build(featureGraph);
    }

    private static SpawnSettings spawnSettings(Consumer<SpawnSettings.Builder> configurator) {
        SpawnSettings.Builder builder = new SpawnSettings.Builder();
        configurator.accept(builder);
        return builder.build();
    }
}
