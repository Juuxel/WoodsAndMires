package juuxel.woodsandmires.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public final class WamBoatEntity extends BoatEntity {
    private final WamBoat boatData;

    public WamBoatEntity(EntityType<? extends WamBoatEntity> type, World world, WamBoat boatData) {
        super(type, world);
        this.boatData = boatData;
    }

    public WamBoat getBoatData() {
        return boatData;
    }

    @Override
    public Type getBoatType() {
        return Type.OAK;
    }

    @Override
    public void setBoatType(Type type) {
    }

    @Override
    public Item asItem() {
        return boatData.boat().asItem();
    }

    public static WamBoatEntity copy(BoatEntity original, WamBoat boatData) {
        var boat = boatData.factory().create(boatData.entityType(), original.getEntityWorld());
        boat.updatePosition(original.getX(), original.getY(), original.getZ());
        return boat;
    }
}
