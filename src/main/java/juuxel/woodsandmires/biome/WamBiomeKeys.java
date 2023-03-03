package juuxel.woodsandmires.biome;

import juuxel.woodsandmires.WoodsAndMires;
import juuxel.woodsandmires.util.RegistryCollector;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.world.biome.Biome;

public final class WamBiomeKeys {
    public static final RegistryCollector<RegistryKey<Biome>> ALL = new RegistryCollector<>();
    public static final RegistryKey<Biome> PINE_FOREST = key("pine_forest");
    public static final RegistryKey<Biome> SNOWY_PINE_FOREST = key("snowy_pine_forest");
    public static final RegistryKey<Biome> OLD_GROWTH_PINE_FOREST = key("old_growth_pine_forest");
    public static final RegistryKey<Biome> LUSH_PINE_FOREST = key("lush_pine_forest");
    public static final RegistryKey<Biome> PINE_MIRE = key("pine_mire");
    public static final RegistryKey<Biome> FELL = key("fell");
    public static final RegistryKey<Biome> SNOWY_FELL = key("snowy_fell");
    public static final RegistryKey<Biome> PINY_GROVE = key("piny_grove");

    private WamBiomeKeys() {
    }

    private static RegistryKey<Biome> key(String id) {
        return ALL.add(RegistryKey.of(RegistryKeys.BIOME, WoodsAndMires.id(id)));
    }
}
