package juuxel.woodsandmires.terrablender;

import com.mojang.datafixers.util.Pair;
import juuxel.woodsandmires.biome.WamBiomes;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.surfacebuilder.MaterialRules;
import net.minecraft.world.gen.surfacebuilder.VanillaSurfaceRules;
import terrablender.api.BiomeProvider;
import terrablender.worldgen.TBClimate;

import java.util.Optional;
import java.util.function.Consumer;

public class WamBiomeProvider extends BiomeProvider {
    public WamBiomeProvider(Identifier name, int overworldWeight) {
        super(name, overworldWeight);
    }

    @Override
    public void addOverworldBiomes(Registry<Biome> registry, Consumer<Pair<TBClimate.ParameterPoint, RegistryKey<Biome>>> mapper) {
        addModifiedVanillaOverworldBiomes(mapper, builder -> {
            builder.replaceBiome(BiomeKeys.SWAMP, WamBiomes.PINE_MIRE);
            builder.replaceBiome(BiomeKeys.TAIGA, WamBiomes.PINE_FOREST);
            builder.replaceBiome(BiomeKeys.FOREST, WamBiomes.PINE_FOREST_CLEARING);
            builder.replaceBiome(BiomeKeys.SNOWY_TAIGA, WamBiomes.SNOWY_PINE_FOREST);
            builder.replaceBiome(BiomeKeys.WINDSWEPT_FOREST, WamBiomes.SNOWY_PINE_FOREST);
            builder.replaceBiome(BiomeKeys.WINDSWEPT_GRAVELLY_HILLS, WamBiomes.SNOWY_PINE_FOREST);
            builder.replaceBiome(BiomeKeys.WINDSWEPT_HILLS, WamBiomes.FELL);
            builder.replaceBiome(BiomeKeys.SNOWY_SLOPES, WamBiomes.FELL);
            builder.replaceBiome(BiomeKeys.FROZEN_PEAKS, WamBiomes.FELL);
        });
    }

    @Override
    public Optional<MaterialRules.MaterialRule> getOverworldSurfaceRules() {
        return Optional.of(
            MaterialRules.sequence(
                MaterialRules.condition(
                    MaterialRules.biome(WamBiomes.FELL),
                    MaterialRules.condition(VanillaSurfaceRules.surfaceNoiseThreshold(1.75), VanillaSurfaceRules.block(Blocks.STONE))
                )
            )
        );
    }
}
