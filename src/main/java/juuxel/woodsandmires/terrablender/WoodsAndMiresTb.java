package juuxel.woodsandmires.terrablender;

import juuxel.woodsandmires.WoodsAndMires;
import juuxel.woodsandmires.biome.WamBiomes;
import net.minecraft.block.Blocks;
import net.minecraft.world.gen.surfacebuilder.MaterialRules;
import net.minecraft.world.gen.surfacebuilder.VanillaSurfaceRules;
import terrablender.api.Regions;
import terrablender.api.SurfaceRuleManager;
import terrablender.api.TerraBlenderApi;

public final class WoodsAndMiresTb implements TerraBlenderApi {
    @Override
    public void onTerraBlenderInitialized() {
        Regions.register(new WamRegion(WoodsAndMires.id("biomes"), 4));
        SurfaceRuleManager.addSurfaceRules(SurfaceRuleManager.RuleCategory.OVERWORLD, WoodsAndMires.ID, createSurfaceRule());
    }

    private static MaterialRules.MaterialRule createSurfaceRule() {
        return MaterialRules.sequence(
            MaterialRules.condition(
                MaterialRules.biome(WamBiomes.FELL),
                MaterialRules.condition(VanillaSurfaceRules.surfaceNoiseThreshold(1.75), VanillaSurfaceRules.block(Blocks.STONE))
            )
        );
    }
}
