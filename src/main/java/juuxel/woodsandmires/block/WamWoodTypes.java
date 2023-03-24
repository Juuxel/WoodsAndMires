package juuxel.woodsandmires.block;

import juuxel.woodsandmires.WoodsAndMires;
import net.fabricmc.fabric.api.object.builder.v1.block.type.WoodTypeRegistry;
import net.minecraft.block.BlockSetType;
import net.minecraft.block.WoodType;

public final class WamWoodTypes {
    public static final WoodType PINE = register("pine", WamBlockSetTypes.PINE);

    public static void init() {
    }

    private static WoodType register(String id, BlockSetType blockSetType) {
        return WoodTypeRegistry.register(WoodsAndMires.id(id), blockSetType);
    }
}
