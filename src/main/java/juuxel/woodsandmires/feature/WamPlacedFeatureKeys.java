package juuxel.woodsandmires.feature;

import juuxel.woodsandmires.WoodsAndMires;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.gen.feature.PlacedFeature;

public final class WamPlacedFeatureKeys {
    public static final RegistryKey<PlacedFeature> PLAINS_FLOWERS = key("plains_flowers");
    public static final RegistryKey<PlacedFeature> FOREST_TANSY = key("forest_tansy");
    public static final RegistryKey<PlacedFeature> TAIGA_HEATHER_PATCH = key("taiga_heather_patch");

    private static RegistryKey<PlacedFeature> key(String id) {
        return RegistryKey.of(Registry.PLACED_FEATURE_KEY, WoodsAndMires.id(id));
    }
}
