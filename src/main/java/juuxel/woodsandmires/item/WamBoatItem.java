package juuxel.woodsandmires.item;

import juuxel.woodsandmires.entity.WamBoat;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.item.BoatItem;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;

public class WamBoatItem extends BoatItem {
    private final boolean chest;
    private final WamBoat boatData;

    public WamBoatItem(boolean chest, WamBoat boatData, Settings settings) {
        super(chest, BoatEntity.Type.OAK, settings);
        this.chest = chest;
        this.boatData = boatData;
    }

    @Override
    protected BoatEntity createEntity(World world, HitResult hitResult) {
        var entity = boatData.factory(chest).create(boatData.entityType(chest), world);
        entity.updatePosition(hitResult.getPos().x, hitResult.getPos().y, hitResult.getPos().z);
        return entity;
    }
}
