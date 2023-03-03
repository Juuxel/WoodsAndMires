package juuxel.woodsandmires.biome;

import juuxel.woodsandmires.WoodsAndMires;
import juuxel.woodsandmires.feature.WamPlacedFeatureKeys;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalBiomeTags;
import net.minecraft.registry.tag.BiomeTags;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.GenerationStep;

public final class WamBiomeModifications {
    public static void init() {
        BiomeModifications.addFeature(
            context -> context.getBiomeKey() == BiomeKeys.PLAINS,
            GenerationStep.Feature.VEGETAL_DECORATION,
            WamPlacedFeatureKeys.PLAINS_FLOWERS
        );

        BiomeModifications.addFeature(
            context -> context.hasTag(ConventionalBiomeTags.FOREST) && !WoodsAndMires.ID.equals(context.getBiomeKey().getValue().getNamespace()),
            GenerationStep.Feature.VEGETAL_DECORATION,
            WamPlacedFeatureKeys.FOREST_TANSY
        );

        BiomeModifications.addFeature(
            context -> context.hasTag(BiomeTags.IS_TAIGA) && !WoodsAndMires.ID.equals(context.getBiomeKey().getValue().getNamespace()),
            GenerationStep.Feature.VEGETAL_DECORATION,
            WamPlacedFeatureKeys.TAIGA_HEATHER_PATCH
        );
    }
}
