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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import terrablender.api.BiomeProvider;
import terrablender.worldgen.TBClimate;

import java.util.Optional;
import java.util.function.Consumer;

public class WamBiomeProvider extends BiomeProvider {
    private static final Logger LOGGER = LogManager.getLogger("WAM");

    public WamBiomeProvider(Identifier name, int overworldWeight) {
        super(name, overworldWeight);
    }

    @Override
    public void addOverworldBiomes(Registry<Biome> registry, Consumer<Pair<TBClimate.ParameterPoint, RegistryKey<Biome>>> mapper) {
        LOGGER.info("adding overworld biomes");
        addModifiedVanillaOverworldBiomes(mapper, builder -> {
            /*OverworldBiomes.addContinentalBiome(PINE_FOREST, OverworldClimate.COOL, 1.0);
            OverworldBiomes.addHillsBiome(PINE_FOREST, PINE_FOREST_HILLS, 1.0);
            OverworldBiomes.addBiomeVariant(PINE_FOREST, PINE_FOREST_HILLS, 0.3);
            OverworldBiomes.addContinentalBiome(PINE_MIRE, OverworldClimate.TEMPERATE, 1.0);
            OverworldBiomes.addContinentalBiome(FELL, OverworldClimate.COOL, 1.0);
            OverworldBiomes.addHillsBiome(FELL, BiomeKeys.SNOWY_MOUNTAINS, 1.0);
            OverworldBiomes.addEdgeBiome(FELL, FELL_EDGE, 1.0);*/

            /*List<MultiNoiseUtil.NoiseHypercube> pineForestPoints = new ParameterUtils.ParameterPointListBuilder()
                .continentalness(Continentalness.INLAND)
                .temperature(Temperature.COOL)
                .depth(Depth.SURFACE)
                .weirdness(Weirdness.FULL_RANGE)
                .humidity(Humidity.span(Humidity.DRY, Humidity.HUMID))
                .erosion(Erosion.FULL_RANGE)
                .buildVanilla();

            pineForestPoints.forEach(point -> builder.replaceBiome(point, WamBiomes.PINE_FOREST));*/
            builder.replaceBiome(BiomeKeys.SWAMP, WamBiomes.PINE_MIRE);
            builder.replaceBiome(BiomeKeys.TAIGA, WamBiomes.PINE_FOREST);
            builder.replaceBiome(BiomeKeys.FOREST, WamBiomes.PINE_FOREST_CLEARING);
            builder.replaceBiome(BiomeKeys.WINDSWEPT_FOREST, WamBiomes.PINE_FOREST_HILLS);
            builder.replaceBiome(BiomeKeys.WINDSWEPT_HILLS, WamBiomes.FELL);
            builder.replaceBiome(BiomeKeys.WINDSWEPT_GRAVELLY_HILLS, WamBiomes.FELL);
            LOGGER.info("Registered biomes");
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
