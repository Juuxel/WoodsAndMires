package juuxel.woodsandmires.data.builtin;

import net.minecraft.util.registry.RegistryEntry;
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

public class WamGenerationSettingsBuilder extends GenerationSettings.Builder {
    // step -> before -> [after]
    private final Map<GenerationStep.Feature, Map<RegistryEntry<PlacedFeature>, Set<RegistryEntry<PlacedFeature>>>> orderingsByStep =
        new EnumMap<>(GenerationStep.Feature.class);

    @Override
    public WamGenerationSettingsBuilder feature(GenerationStep.Feature featureStep, RegistryEntry<PlacedFeature> feature) {
        super.feature(featureStep, feature);
        return this;
    }

    @Override
    public WamGenerationSettingsBuilder feature(int stepIndex, RegistryEntry<PlacedFeature> featureEntry) {
        super.feature(stepIndex, featureEntry);
        return this;
    }

    @Override
    public WamGenerationSettingsBuilder carver(GenerationStep.Carver carverStep, RegistryEntry<? extends ConfiguredCarver<?>> carver) {
        super.carver(carverStep, carver);
        return this;
    }

    public WamGenerationSettingsBuilder addOrdering(GenerationStep.Feature step, RegistryEntry<PlacedFeature> before, RegistryEntry<PlacedFeature> after) {
        orderingsByStep.computeIfAbsent(step, s -> new HashMap<>())
            .computeIfAbsent(before, entry -> new HashSet<>())
            .add(after);
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
        for (int i = 0; i < this.features.size(); i++) {
            var features = this.features.get(i);
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
