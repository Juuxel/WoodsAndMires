package juuxel.woodsandmires.item;

import juuxel.woodsandmires.WoodsAndMires;
import juuxel.woodsandmires.entity.WamBoat;
import net.minecraft.block.DispenserBlock;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.registry.Registry;

public final class WamItems {
    public static final Item PINE_BOAT = register("pine_boat", new WamBoatItem(WamBoat.PINE, new Item.Settings().group(ItemGroup.TRANSPORTATION)));
    // TODO: Chest boats

    public static void init() {
        for (WamBoat boat : WamBoat.values()) {
            DispenserBlock.registerBehavior(boat.boat(), new WamBoatDispenserBehavior(boat));
        }
    }

    private static <T extends Item> T register(String id, T item) {
        return Registry.register(Registry.ITEM, WoodsAndMires.id(id), item);
    }
}
