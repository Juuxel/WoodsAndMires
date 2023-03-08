package juuxel.woodsandmires.client;

import juuxel.woodsandmires.block.entity.WamBlockEntities;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.client.render.block.entity.SignBlockEntityRenderer;

@Environment(EnvType.CLIENT)
public final class WoodsAndMiresClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        WamBlocksClient.init();
        BlockEntityRendererFactories.register(WamBlockEntities.SIGN, SignBlockEntityRenderer::new);
    }
}
