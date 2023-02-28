package juuxel.woodsandmires.terrablender;

import juuxel.woodsandmires.WoodsAndMires;
import juuxel.woodsandmires.biome.WamBiomeKeys;
import juuxel.woodsandmires.config.WamConfig;
import net.minecraft.block.Blocks;
import net.minecraft.world.gen.noise.NoiseParametersKeys;
import net.minecraft.world.gen.surfacebuilder.MaterialRules;
import net.minecraft.world.gen.surfacebuilder.VanillaSurfaceRules;
import terrablender.api.Regions;
import terrablender.api.SurfaceRuleManager;
import terrablender.api.TerraBlenderApi;

public final class WoodsAndMiresTb implements TerraBlenderApi {
    @Override
    public void onTerraBlenderInitialized() {
        WamConfig.load();
        Regions.register(new WamRegion(WoodsAndMires.id("biomes"), WamConfig.biomeRegionWeight));
        SurfaceRuleManager.addSurfaceRules(SurfaceRuleManager.RuleCategory.OVERWORLD, WoodsAndMires.ID, createSurfaceRule());
    }

    private static MaterialRules.MaterialRule createSurfaceRule() {
        MaterialRules.MaterialCondition hasWater = MaterialRules.water(0, 0);
        MaterialRules.MaterialRule stone = VanillaSurfaceRules.block(Blocks.STONE);
        MaterialRules.MaterialRule snowBlock = VanillaSurfaceRules.block(Blocks.SNOW_BLOCK);
        MaterialRules.MaterialRule powderSnow = MaterialRules.condition(
            MaterialRules.noiseThreshold(NoiseParametersKeys.POWDER_SNOW, 0.35, 0.6),
            MaterialRules.condition(hasWater, VanillaSurfaceRules.block(Blocks.POWDER_SNOW))
        );

        return MaterialRules.sequence(
            MaterialRules.condition(
                MaterialRules.biome(WamBiomeKeys.FELL),
                MaterialRules.condition(VanillaSurfaceRules.surfaceNoiseThreshold(1.75), stone)
            ),
            MaterialRules.condition(
                MaterialRules.biome(WamBiomeKeys.OLD_GROWTH_PINE_FOREST),
                MaterialRules.condition(
                    VanillaSurfaceRules.surfaceNoiseThreshold(1.75),
                    VanillaSurfaceRules.block(Blocks.COARSE_DIRT)
                )
            ),
            MaterialRules.condition(
                MaterialRules.biome(WamBiomeKeys.PINY_GROVE),
                MaterialRules.sequence(
                    powderSnow,
                    MaterialRules.condition(hasWater, snowBlock),
                    VanillaSurfaceRules.block(Blocks.DIRT)
                )
            ),
            MaterialRules.condition(
                MaterialRules.biome(WamBiomeKeys.SNOWY_PINE_FOREST),
                MaterialRules.sequence(
                    MaterialRules.condition(MaterialRules.steepSlope(), stone),
                    powderSnow,
                    MaterialRules.condition(hasWater, snowBlock)
                )
            ),
            MaterialRules.condition(
                MaterialRules.biome(WamBiomeKeys.SNOWY_FELL),
                MaterialRules.sequence(
                    MaterialRules.condition(VanillaSurfaceRules.surfaceNoiseThreshold(1.75), stone),
                    MaterialRules.condition(MaterialRules.steepSlope(), stone),
                    MaterialRules.condition(hasWater, snowBlock)
                )
            )
        );
    }
}
