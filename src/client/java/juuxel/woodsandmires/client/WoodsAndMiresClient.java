package juuxel.woodsandmires.client;

import juuxel.woodsandmires.block.entity.WamBlockEntities;
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
            registerBoatModel(true, boat);
            registerBoatModel(false, boat);
        }
    }

    private static void registerBoatModel(boolean chest, WamBoat boat) {
        var type = boat.entityType(chest);
        EntityRendererRegistry.register(type, context -> new WamBoatEntityRenderer(context, chest, boat));
        EntityModelLayerRegistry.registerModelLayer(WamBoatEntityRenderer.getModelLayer(boat, chest),
            () -> BoatEntityModel.getTexturedModelData(chest));
    }
}
