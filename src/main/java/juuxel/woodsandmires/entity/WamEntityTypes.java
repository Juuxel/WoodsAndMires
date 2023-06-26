package juuxel.woodsandmires.entity;

import juuxel.woodsandmires.WoodsAndMires;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.registry.Registry;

public final class WamEntityTypes {
    public static final EntityType<WamBoatEntity> PINE_BOAT = register("pine_boat", createBoatType(WamBoat.PINE));

    public static void init() {
    }

    private static <T extends EntityType<?>> T register(String id, T type) {
        return Registry.register(Registry.ENTITY_TYPE, WoodsAndMires.id(id), type);
    }

    private static EntityType<WamBoatEntity> createBoatType(WamBoat boat) {
        return FabricEntityTypeBuilder.create(SpawnGroup.MISC, boat.factory())
            .dimensions(EntityDimensions.changing(1.375f, 0.5625f))
            .trackRangeChunks(10)
            .build();
    }
}
