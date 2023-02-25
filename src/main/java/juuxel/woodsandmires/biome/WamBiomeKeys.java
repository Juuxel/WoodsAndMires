package juuxel.woodsandmires.biome;

import juuxel.woodsandmires.WoodsAndMires;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;

public final class WamBiomeKeys {
    public static final RegistryKey<Biome> PINE_FOREST = key("pine_forest");
    public static final RegistryKey<Biome> SNOWY_PINE_FOREST = key("snowy_pine_forest");
    public static final RegistryKey<Biome> OLD_GROWTH_PINE_FOREST = key("old_growth_pine_forest");
    public static final RegistryKey<Biome> PINE_FOREST_CLEARING = key("pine_forest_clearing");
    public static final RegistryKey<Biome> PINE_MIRE = key("pine_mire");
    public static final RegistryKey<Biome> FELL = key("fell");
    public static final RegistryKey<Biome> SNOWY_FELL = key("snowy_fell");

    private WamBiomeKeys() {
    }

    private static RegistryKey<Biome> key(String id) {
        return RegistryKey.of(Registry.BIOME_KEY, WoodsAndMires.id(id));
    }
}
