package juuxel.woodsandmires;

import juuxel.woodsandmires.biome.WamBiomes;
import juuxel.woodsandmires.block.WamBlocks;
import juuxel.woodsandmires.feature.WamConfiguredFeatures;
import juuxel.woodsandmires.feature.WamFeatures;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;

public final class WoodsAndMires implements ModInitializer {
    public static final String ID = "woods_and_mires";

    public static Identifier id(String path) {
        return new Identifier(ID, path);
    }

    @Override
    public void onInitialize() {
        WamBlocks.init();
        WamFeatures.init();
        WamConfiguredFeatures.init();
        WamBiomes.init();
    }
}
