package juuxel.woodsandmires.item;

import juuxel.woodsandmires.entity.WamBoat;
import net.minecraft.block.dispenser.BoatDispenserBehavior;
import net.minecraft.entity.vehicle.BoatEntity;

public final class WamBoatDispenserBehavior extends BoatDispenserBehavior {
    private final WamBoat boatData;

    public WamBoatDispenserBehavior(WamBoat boatData) {
        super(BoatEntity.Type.OAK);
        this.boatData = boatData;
    }

    public WamBoat getBoatData() {
        return boatData;
    }
}
