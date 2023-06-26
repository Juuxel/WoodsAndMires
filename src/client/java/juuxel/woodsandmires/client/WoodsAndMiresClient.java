package juuxel.woodsandmires.client;

import juuxel.woodsandmires.client.renderer.WamBoatEntityRenderer;
import juuxel.woodsandmires.entity.WamBoat;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.client.render.block.entity.SignBlockEntityRenderer;
import net.minecraft.client.render.entity.model.BoatEntityModel;

@Environment(EnvType.CLIENT)
public final class WoodsAndMiresClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        WamBlocksClient.init();
        BlockEntityRendererFactories.register(WamBlockEntities.SIGN, SignBlockEntityRenderer::new);

        for (WamBoat boat : WamBoat.values()) {
            EntityRendererRegistry.register(boat.entityType(), context -> new WamBoatEntityRenderer(context, boat));
            EntityModelLayerRegistry.registerModelLayer(WamBoatEntityRenderer.getModelLayer(boat),
                BoatEntityModel::getTexturedModelData);
        }
    }
}
