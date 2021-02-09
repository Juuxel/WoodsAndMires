package juuxel.woodsandmires.client;

import juuxel.woodsandmires.block.WamBlocks;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public final class WoodsAndMiresClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        WamBlocks.initClient();
    }
}
