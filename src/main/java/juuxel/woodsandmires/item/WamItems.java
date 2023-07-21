package juuxel.woodsandmires.item;

import juuxel.woodsandmires.WoodsAndMires;
import juuxel.woodsandmires.entity.WamBoat;
import net.minecraft.block.DispenserBlock;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
import net.minecraft.util.registry.Registry;

public final class WamItems {
    public static final Item PINE_BOAT = register("pine_boat", new WamBoatItem(false, WamBoat.PINE, new Item.Settings().group(ItemGroup.TRANSPORTATION)));
    public static final Item PINE_CHEST_BOAT = register("pine_chest_boat", new WamBoatItem(true, WamBoat.PINE, new Item.Settings().group(ItemGroup.TRANSPORTATION)));
    public static final Item PINE_CONE = register("pine_cone", new Item(new Item.Settings().group(ItemGroup.MATERIALS)));
    public static final Item PINE_CONE_JAM = register("pine_cone_jam", new FoodWithRemainderItem(
        new Item.Settings()
            .group(ItemGroup.FOOD)
            .recipeRemainder(Items.GLASS_BOTTLE)
            .food(new FoodComponent.Builder().hunger(3).saturationModifier(0.25f).build())
    ));

    public static void init() {
        for (WamBoat boat : WamBoat.values()) {
            DispenserBlock.registerBehavior(boat.boat(), new WamBoatDispenserBehavior(boat, false));
            DispenserBlock.registerBehavior(boat.chestBoat(), new WamBoatDispenserBehavior(boat, true));
        }
    }

    private static <T extends Item> T register(String id, T item) {
        return Registry.register(Registry.ITEM, WoodsAndMires.id(id), item);
    }
}
