package juuxel.woodsandmires;

import juuxel.woodsandmires.biome.WamBiomeModifications;
import juuxel.woodsandmires.block.WamBlockSetTypes;
import juuxel.woodsandmires.block.WamBlocks;
import juuxel.woodsandmires.block.WamWoodTypes;
import juuxel.woodsandmires.block.entity.WamBlockEntities;
import juuxel.woodsandmires.config.WamConfig;
import juuxel.woodsandmires.dev.WamDev;
import juuxel.woodsandmires.entity.WamEntityTypes;
import juuxel.woodsandmires.feature.WamFeatures;
import juuxel.woodsandmires.item.WamItems;
import juuxel.woodsandmires.item.WamItemGroups;
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
        WamConfig.load();
        WamBlockSetTypes.init();
        WamWoodTypes.init();
        WamBlocks.init();
        WamBlockEntities.register();
        WamItems.init();
        WamItemGroups.init();
        WamEntityTypes.init();
        WamTreeDecorators.register();
        WamFeatures.init();
        WamBiomeModifications.init();

        if (FabricLoader.getInstance().isDevelopmentEnvironment()) {
            WamDev.init();
        }
    }
}
