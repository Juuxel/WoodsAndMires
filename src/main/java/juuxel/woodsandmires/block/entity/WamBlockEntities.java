package juuxel.woodsandmires.block.entity;

import juuxel.woodsandmires.WoodsAndMires;
import juuxel.woodsandmires.block.WamBlocks;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public final class WamBlockEntities {
    public static final BlockEntityType<WamSignBlockEntity> SIGN = BlockEntityType.Builder.create(WamSignBlockEntity::new,
        WamBlocks.PINE_SIGN,
        WamBlocks.PINE_WALL_SIGN.get()
    ).build(null);
    public static final BlockEntityType<WamHangingSignBlockEntity> HANGING_SIGN = BlockEntityType.Builder.create(WamHangingSignBlockEntity::new,
        WamBlocks.PINE_HANGING_SIGN,
        WamBlocks.PINE_WALL_HANGING_SIGN.get()
    ).build(null);

    public static void register() {
        register("sign", SIGN);
        register("hanging_sign", HANGING_SIGN);
    }

    private static void register(String id, BlockEntityType<?> type) {
        Registry.register(Registries.BLOCK_ENTITY_TYPE, WoodsAndMires.id(id), type);
    }
}
