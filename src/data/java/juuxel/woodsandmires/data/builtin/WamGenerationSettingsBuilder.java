package juuxel.woodsandmires.data.builtin;

import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.carver.ConfiguredCarver;
import net.minecraft.world.gen.feature.PlacedFeature;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DirectedAcyclicGraph;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class WamGenerationSettingsBuilder extends GenerationSettings.LookupBackedBuilder {
    // step -> before -> [after]
    private final Map<GenerationStep.Feature, Map<RegistryEntry<PlacedFeature>, Set<RegistryEntry<PlacedFeature>>>> orderingsByStep =
        new EnumMap<>(GenerationStep.Feature.class);
    private final RegistryEntryLookup<PlacedFeature> placedFeatureLookup;

    public WamGenerationSettingsBuilder(RegistryEntryLookup<PlacedFeature> placedFeatureLookup, RegistryEntryLookup<ConfiguredCarver<?>> configuredCarverLookup) {
        super(placedFeatureLookup, configuredCarverLookup);
        this.placedFeatureLookup = placedFeatureLookup;
    }

    public WamGenerationSettingsBuilder addOrdering(GenerationStep.Feature step, RegistryKey<PlacedFeature> before, RegistryKey<PlacedFeature> after) {
        orderingsByStep.computeIfAbsent(step, s -> new HashMap<>())
            .computeIfAbsent(placedFeatureLookup.getOrThrow(before), entry -> new HashSet<>())
            .add(placedFeatureLookup.getOrThrow(after));
        return this;
    }

    @Deprecated
    @Override
    public GenerationSettings build() {
        throw new UnsupportedOperationException("Use build(DirectedAcyclicGraph) instead");
    }

    public GenerationSettings build(DirectedAcyclicGraph<RegistryEntry<PlacedFeature>, DefaultEdge> globalGraph) {
        order(globalGraph);
        return super.build();
    }

    private void order(DirectedAcyclicGraph<RegistryEntry<PlacedFeature>, DefaultEdge> globalGraph) {
        var steps = GenerationStep.Feature.values();
        for (int i = 0; i < this.indexedFeaturesList.size(); i++) {
            var features = this.indexedFeaturesList.get(i);
            var localGraph = new DirectedAcyclicGraph<RegistryEntry<PlacedFeature>, DefaultEdge>(DefaultEdge.class);
            Graphs.addGraph(localGraph, globalGraph);

            for (var feature : features) {
                localGraph.addVertex(feature);
            }

            var step = steps[i];
            orderingsByStep.getOrDefault(step, Map.of()).forEach((before, allAfter) -> {
                for (var after : allAfter) {
                    localGraph.addEdge(before, after);
                }
            });

            var orderProvider = new ArrayList<RegistryEntry<PlacedFeature>>(localGraph.vertexSet().size());
            for (var feature : localGraph) {
                orderProvider.add(feature);
            }

            features.sort(Comparator.comparingInt(orderProvider::indexOf));
        }
    }
}
