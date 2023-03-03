package juuxel.woodsandmires.data;

import juuxel.woodsandmires.biome.WamBiomeKeys;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalBiomeTags;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BiomeTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.world.biome.Biome;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

public final class WamBiomeTagProvider extends FabricTagProvider<Biome> {
    public WamBiomeTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, RegistryKeys.BIOME, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {
        // Vanilla tags
        generateOverworld(BiomeTags.IS_OVERWORLD);
        getOrCreateTagBuilder(BiomeTags.IS_FOREST)
            .add(WamBiomeKeys.PINE_FOREST)
            .add(WamBiomeKeys.OLD_GROWTH_PINE_FOREST)
            .add(WamBiomeKeys.SNOWY_PINE_FOREST)
            .add(WamBiomeKeys.LUSH_PINE_FOREST)
            .add(WamBiomeKeys.PINY_GROVE);
        getOrCreateTagBuilder(BiomeTags.IS_HILL)
            .add(WamBiomeKeys.SNOWY_PINE_FOREST);
        getOrCreateTagBuilder(BiomeTags.IS_MOUNTAIN)
            .add(WamBiomeKeys.FELL)
            .add(WamBiomeKeys.SNOWY_FELL);
        getOrCreateTagBuilder(BiomeTags.IS_TAIGA)
            .add(WamBiomeKeys.PINE_FOREST)
            .add(WamBiomeKeys.OLD_GROWTH_PINE_FOREST)
            .add(WamBiomeKeys.SNOWY_PINE_FOREST);
        generateOverworld(BiomeTags.STRONGHOLD_HAS_STRUCTURE);
        getOrCreateTagBuilder(BiomeTags.PILLAGER_OUTPOST_HAS_STRUCTURE)
            .add(WamBiomeKeys.PINE_FOREST)
            .add(WamBiomeKeys.PINY_GROVE);

        // Common tags
        generateOverworld(ConventionalBiomeTags.IN_OVERWORLD);
        getOrCreateTagBuilder(ConventionalBiomeTags.CLIMATE_COLD)
            .add(WamBiomeKeys.PINE_FOREST)
            .add(WamBiomeKeys.SNOWY_PINE_FOREST)
            .add(WamBiomeKeys.OLD_GROWTH_PINE_FOREST)
            .add(WamBiomeKeys.FELL)
            .add(WamBiomeKeys.SNOWY_FELL)
            .add(WamBiomeKeys.PINY_GROVE);
        getOrCreateTagBuilder(ConventionalBiomeTags.CLIMATE_TEMPERATE)
            .add(WamBiomeKeys.LUSH_PINE_FOREST);
        forEachTag(ConventionalBiomeTags.CLIMATE_WET, ConventionalBiomeTags.SWAMP)
            .add(WamBiomeKeys.PINE_MIRE);
        getOrCreateTagBuilder(ConventionalBiomeTags.EXTREME_HILLS)
            .add(WamBiomeKeys.SNOWY_PINE_FOREST)
            .add(WamBiomeKeys.FELL)
            .add(WamBiomeKeys.SNOWY_FELL);
        getOrCreateTagBuilder(ConventionalBiomeTags.MOUNTAIN_PEAK)
            .add(WamBiomeKeys.FELL)
            .add(WamBiomeKeys.SNOWY_FELL);
        getOrCreateTagBuilder(ConventionalBiomeTags.MOUNTAIN_SLOPE)
            .add(WamBiomeKeys.SNOWY_PINE_FOREST);
        getOrCreateTagBuilder(ConventionalBiomeTags.SNOWY)
            .add(WamBiomeKeys.SNOWY_PINE_FOREST)
            .add(WamBiomeKeys.SNOWY_FELL)
            .add(WamBiomeKeys.PINY_GROVE);
    }

    @SafeVarargs
    private MultiBuilder<Biome> forEachTag(TagKey<Biome>... tags) {
        return new MultiBuilder<>(Stream.of(tags).map(this::getOrCreateTagBuilder).toList());
    }

    private void generateOverworld(TagKey<Biome> tag) {
        FabricTagBuilder tagBuilder = getOrCreateTagBuilder(tag);
        WamBiomeKeys.ALL.stream().forEach(tagBuilder::add);
    }

    static final class MultiBuilder<T> {
        private final List<FabricTagProvider<T>.FabricTagBuilder> builders;

        private MultiBuilder(List<FabricTagProvider<T>.FabricTagBuilder> builders) {
            this.builders = builders;
        }

        public MultiBuilder<T> add(RegistryKey<T> key) {
            for (var builder : builders) {
                builder.add(key);
            }
            return this;
        }
    }
}
