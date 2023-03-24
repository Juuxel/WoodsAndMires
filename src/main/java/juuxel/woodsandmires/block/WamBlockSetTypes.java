package juuxel.woodsandmires.block;

import juuxel.woodsandmires.WoodsAndMires;
import net.fabricmc.fabric.api.object.builder.v1.block.type.BlockSetTypeRegistry;
import net.minecraft.block.BlockSetType;

public final class WamBlockSetTypes {
    public static final BlockSetType PINE = registerWood("pine");

    public static void init() {
    }

    private static BlockSetType registerWood(String id) {
        return BlockSetTypeRegistry.registerWood(WoodsAndMires.id(id));
    }
}
