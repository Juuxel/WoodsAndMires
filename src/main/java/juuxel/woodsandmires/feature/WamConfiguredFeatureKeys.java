package juuxel.woodsandmires.feature;

import juuxel.woodsandmires.WoodsAndMires;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.gen.feature.ConfiguredFeature;

public final class WamConfiguredFeatureKeys {
    public static final RegistryKey<ConfiguredFeature<?, ?>> PINE_FROM_SAPLING = key("pine_from_sapling");

    private static RegistryKey<ConfiguredFeature<?, ?>> key(String id) {
        return RegistryKey.of(Registry.CONFIGURED_FEATURE_KEY, WoodsAndMires.id(id));
    }
}
