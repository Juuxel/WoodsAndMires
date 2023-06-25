package juuxel.woodsandmires.client;

import com.terraformersmc.terraform.boat.api.TerraformBoatType;
import com.terraformersmc.terraform.boat.api.TerraformBoatTypeRegistry;
import com.terraformersmc.terraform.boat.api.client.TerraformBoatClientHelper;
import juuxel.woodsandmires.block.entity.WamBlockEntities;
import juuxel.woodsandmires.item.WamItems;
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
        registerBoat(WamItems.PINE_BOAT_TYPE);
    }

    private static void registerBoat(TerraformBoatType type) {
        TerraformBoatClientHelper.registerModelLayers(TerraformBoatTypeRegistry.INSTANCE.getId(type));
    }
}
