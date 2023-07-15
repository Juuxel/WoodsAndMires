package juuxel.woodsandmires.data.builtin;

import juuxel.woodsandmires.data.mixin.GenerationSettingsBuilderAccessor;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.TopologicalSorts;
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

    @Override
    public GenerationSettings build() {
        order();
        return super.build();
    }

    private void order() {
        orderingsByStep.forEach((step, orderings) -> {
            List<RegistryEntry<PlacedFeature>> features = ((GenerationSettingsBuilderAccessor) this)
                .getIndexedFeaturesList()
                .get(step.ordinal());

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
