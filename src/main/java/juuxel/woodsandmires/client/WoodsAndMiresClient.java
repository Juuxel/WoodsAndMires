package juuxel.woodsandmires.client;

import com.terraformersmc.terraform.sign.SpriteIdentifierRegistry;
import com.terraformersmc.terraform.sign.TerraformSign;
import juuxel.woodsandmires.block.WamBlocks;
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
        WamBlocks.initClient();
        registerSign(WamBlocks.PINE_SIGN);
    }

    private static void registerSign(Block sign) {
        TerraformSign t = (TerraformSign) sign;
        SpriteIdentifier sprite = new SpriteIdentifier(TexturedRenderLayers.SIGNS_ATLAS_TEXTURE, t.getTexture());
        SpriteIdentifierRegistry.INSTANCE.addIdentifier(sprite);
    }
}
