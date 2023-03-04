package juuxel.woodsandmires.block;

import juuxel.woodsandmires.WoodsAndMires;
import net.fabricmc.fabric.api.object.builder.v1.sign.SignTypeRegistry;
import net.minecraft.util.SignType;

public final class WamSignTypes {
    public static final SignType PINE = register("pine");

    public static void init() {
    }

    private static SignType register(String id) {
        return SignTypeRegistry.registerSignType(WoodsAndMires.id(id));
    }
}
