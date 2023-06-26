package juuxel.woodsandmires.item;

import juuxel.woodsandmires.WoodsAndMires;
import juuxel.woodsandmires.entity.WamBoat;
import net.minecraft.block.DispenserBlock;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public final class WamItems {
    public static final Item PINE_BOAT = register("pine_boat", new WamBoatItem(false, WamBoat.PINE, new Item.Settings()));
    public static final Item PINE_CHEST_BOAT = register("pine_chest_boat", new WamBoatItem(true, WamBoat.PINE, new Item.Settings()));

    public static void init() {
        for (WamBoat boat : WamBoat.values()) {
            DispenserBlock.registerBehavior(boat.boat(), new WamBoatDispenserBehavior(boat, false));
            DispenserBlock.registerBehavior(boat.chestBoat(), new WamBoatDispenserBehavior(boat, true));
        }
    }

    private static <T extends Item> T register(String id, T item) {
        return Registry.register(Registries.ITEM, WoodsAndMires.id(id), item);
    }
}
