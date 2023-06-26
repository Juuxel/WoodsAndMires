package juuxel.woodsandmires.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.entity.vehicle.ChestBoatEntity;
import net.minecraft.item.Item;
import net.minecraft.text.Text;
import net.minecraft.world.World;

public final class WamBoatEntity extends BoatEntity implements BoatWithWamData {
    private final WamBoat boatData;

    public WamBoatEntity(EntityType<? extends BoatEntity> type, World world, WamBoat boatData) {
        super(type, world);
        this.boatData = boatData;
    }

    @Override
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

    @Override
    protected Text getDefaultName() {
        return super.getDefaultName();
    }

    public static BoatEntity copy(BoatEntity original, WamBoat boatData) {
        var chest = original instanceof ChestBoatEntity;
        var boat = boatData.factory(chest).create(boatData.entityType(chest), original.getEntityWorld());
        boat.updatePosition(original.getX(), original.getY(), original.getZ());
        return boat;
    }
}
