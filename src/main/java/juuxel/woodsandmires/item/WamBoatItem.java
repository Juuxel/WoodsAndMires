package juuxel.woodsandmires.item;

import juuxel.woodsandmires.entity.WamBoat;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.item.BoatItem;

public class WamBoatItem extends BoatItem {
    private final WamBoat boatData;

    public WamBoatItem(WamBoat boatData, Settings settings) {
        super(BoatEntity.Type.OAK, settings);
        this.boatData = boatData;
    }

    public WamBoat getBoatData() {
        return boatData;
    }
}
