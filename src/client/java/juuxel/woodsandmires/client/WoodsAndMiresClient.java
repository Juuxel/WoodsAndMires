package juuxel.woodsandmires.client;

import com.terraformersmc.terraform.boat.api.TerraformBoatType;
import com.terraformersmc.terraform.boat.api.TerraformBoatTypeRegistry;
import com.terraformersmc.terraform.boat.api.client.TerraformBoatClientHelper;
import com.terraformersmc.terraform.sign.SpriteIdentifierRegistry;
import com.terraformersmc.terraform.sign.TerraformSign;
import juuxel.woodsandmires.block.WamBlocks;
import juuxel.woodsandmires.item.WamItems;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.util.SpriteIdentifier;

@Environment(EnvType.CLIENT)
public final class WoodsAndMiresClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        WamBlocksClient.init();
        registerSign(WamBlocks.PINE_SIGN);
        registerBoat(WamItems.PINE_BOAT_TYPE);
    }

    private static void registerSign(Block sign) {
        TerraformSign t = (TerraformSign) sign;
        SpriteIdentifier sprite = new SpriteIdentifier(TexturedRenderLayers.SIGNS_ATLAS_TEXTURE, t.getTexture());
        SpriteIdentifierRegistry.INSTANCE.addIdentifier(sprite);
    }

    private static void registerBoat(TerraformBoatType type) {
        TerraformBoatClientHelper.registerModelLayer(TerraformBoatTypeRegistry.INSTANCE.getId(type));
    }
}
