package juuxel.woodsandmires.biome;

import juuxel.woodsandmires.WoodsAndMires;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalBiomeTags;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.PlacedFeature;

public final class WamBiomeModifications {
    public static void init() {
        BiomeModifications.addFeature(
            context -> context.getBiomeKey() == BiomeKeys.PLAINS,
            GenerationStep.Feature.VEGETAL_DECORATION,
            placedFeature(WoodsAndMires.id("plains_flowers"))
        );

        BiomeModifications.addFeature(
            context -> context.hasTag(ConventionalBiomeTags.FOREST) && !WoodsAndMires.ID.equals(context.getBiomeKey().getValue().getNamespace()),
            GenerationStep.Feature.VEGETAL_DECORATION,
            placedFeature(WoodsAndMires.id("forest_tansy"))
        );
    }

    private static RegistryKey<PlacedFeature> placedFeature(Identifier id) {
        return RegistryKey.of(Registry.PLACED_FEATURE_KEY, id);
    }
}
