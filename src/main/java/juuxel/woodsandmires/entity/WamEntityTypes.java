package juuxel.woodsandmires.entity;

import juuxel.woodsandmires.WoodsAndMires;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public final class WamEntityTypes {
    public static final EntityType<BoatEntity> PINE_BOAT = register("pine_boat", createBoatType(false, WamBoat.PINE));
    public static final EntityType<BoatEntity> PINE_CHEST_BOAT = register("pine_chest_boat", createBoatType(true, WamBoat.PINE));

    public static void init() {
    }

    private static <T extends EntityType<?>> T register(String id, T type) {
        return Registry.register(Registries.ENTITY_TYPE, WoodsAndMires.id(id), type);
    }

    private static EntityType<BoatEntity> createBoatType(boolean chest, WamBoat boat) {
        return FabricEntityTypeBuilder.create(SpawnGroup.MISC, boat.factory(chest))
            .dimensions(EntityDimensions.changing(1.375f, 0.5625f))
            .trackRangeChunks(10)
            .build();
    }
}
