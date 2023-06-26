package juuxel.woodsandmires.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.entity.vehicle.ChestBoatEntity;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public final class WamChestBoatEntity extends ChestBoatEntity implements BoatWithWamData {
    private final WamBoat boatData;

    public WamChestBoatEntity(EntityType<? extends BoatEntity> entityType, World world, WamBoat boatData) {
        super(entityType, world);
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
        return boatData.chestBoat().asItem();
    }
}
