package juuxel.woodsandmires.data.builtin;

import net.minecraft.util.TopologicalSorts;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.carver.ConfiguredCarver;
import net.minecraft.world.gen.feature.PlacedFeature;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

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

    @Override
    public GenerationSettings build() {
        order();
        return super.build();
    }

    private void order() {
        orderingsByStep.forEach((step, orderings) -> {
            List<RegistryEntry<PlacedFeature>> features = this.features.get(step.ordinal());

            Set<RegistryEntry<PlacedFeature>> visited = new HashSet<>();
            Set<RegistryEntry<PlacedFeature>> visiting = new HashSet<>();
            Consumer<RegistryEntry<PlacedFeature>> noopConsumer = x -> {};

            // Iterate over all constraints
            for (var before : orderings.keySet()) {
                // Check that the ordering is consistent
                if (TopologicalSorts.sort(orderings, visited, visiting, noopConsumer, before)) {
                    throw new IllegalStateException("Found inconsistent order in step " + step + "; cycle starting from " + before);
                }
                visited.clear();
                visiting.clear();

                // Add the order
                // Note: we won't use the mc toposort here since we want to keep the general order.
                // It's still useful for checking for cycles.
                for (var after : orderings.get(before)) {
                    int beforeIndex = features.indexOf(before);
                    int afterIndex = features.indexOf(after);
                    if (beforeIndex <= afterIndex) continue;

                    // Move "after" to be after the "before" entry
                    features.add(beforeIndex + 1, after);
                    features.remove(afterIndex);
                }
            }
        });
    }
}
