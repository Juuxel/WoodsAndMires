package juuxel.woodsandmires.terrablender;

import com.mojang.datafixers.util.Pair;
import juuxel.woodsandmires.biome.WamBiomes;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.biome.source.util.MultiNoiseUtil;
import terrablender.api.Region;
import terrablender.api.RegionType;

import java.util.function.Consumer;

public class WamRegion extends Region {
    public WamRegion(Identifier name, int overworldWeight) {
        super(name, RegionType.OVERWORLD, overworldWeight);
    }

    @Override
    public void addBiomes(Registry<Biome> registry, Consumer<Pair<MultiNoiseUtil.NoiseHypercube, RegistryKey<Biome>>> mapper) {
        addModifiedVanillaOverworldBiomes(mapper, builder -> {
            builder.replaceBiome(BiomeKeys.SWAMP, WamBiomes.PINE_MIRE);
            builder.replaceBiome(BiomeKeys.TAIGA, WamBiomes.PINE_FOREST);
            builder.replaceBiome(BiomeKeys.FOREST, WamBiomes.PINE_FOREST_CLEARING);
            builder.replaceBiome(BiomeKeys.OLD_GROWTH_PINE_TAIGA, WamBiomes.OLD_GROWTH_PINE_FOREST);
            builder.replaceBiome(BiomeKeys.OLD_GROWTH_SPRUCE_TAIGA, WamBiomes.OLD_GROWTH_PINE_FOREST);
            builder.replaceBiome(BiomeKeys.SNOWY_TAIGA, WamBiomes.SNOWY_PINE_FOREST);
            builder.replaceBiome(BiomeKeys.WINDSWEPT_FOREST, WamBiomes.SNOWY_PINE_FOREST);
            builder.replaceBiome(BiomeKeys.WINDSWEPT_GRAVELLY_HILLS, WamBiomes.FELL);
            builder.replaceBiome(BiomeKeys.WINDSWEPT_HILLS, WamBiomes.FELL);
            builder.replaceBiome(BiomeKeys.SNOWY_SLOPES, WamBiomes.SNOWY_PINE_FOREST);
            builder.replaceBiome(BiomeKeys.FROZEN_PEAKS, WamBiomes.FELL);
        });
    }
}
