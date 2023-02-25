package juuxel.woodsandmires;

import juuxel.woodsandmires.biome.WamBiomeModifications;
import juuxel.woodsandmires.block.WamBlocks;
import juuxel.woodsandmires.dev.WamDev;
import juuxel.woodsandmires.feature.WamFeatures;
import juuxel.woodsandmires.tree.WamTreeDecorators;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Identifier;

public final class WoodsAndMires implements ModInitializer {
    public static final String ID = "woods_and_mires";

    public static Identifier id(String path) {
        return new Identifier(ID, path);
    }

    @Override
    public void onInitialize() {
        WamBlocks.init();
        WamTreeDecorators.register();
        WamFeatures.init();
        WamBiomeModifications.init();

        if (FabricLoader.getInstance().isDevelopmentEnvironment()) {
            WamDev.init();
        }
    }
}
