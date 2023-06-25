package juuxel.woodsandmires.item;

import com.terraformersmc.terraform.boat.api.TerraformBoatType;
import com.terraformersmc.terraform.boat.api.TerraformBoatTypeRegistry;
import com.terraformersmc.terraform.boat.api.item.TerraformBoatItemHelper;
import juuxel.woodsandmires.WoodsAndMires;
import net.minecraft.item.Item;
import net.minecraft.util.registry.Registry;

public final class WamItems {
    public static final Item PINE_BOAT = TerraformBoatItemHelper.registerBoatItem(
        WoodsAndMires.id("pine_boat"),
        () -> WamItems.PINE_BOAT_TYPE);
    public static final TerraformBoatType PINE_BOAT_TYPE = Registry.register(
        TerraformBoatTypeRegistry.INSTANCE,
        WoodsAndMires.id("pine"),
        new TerraformBoatType.Builder()
            .item(PINE_BOAT)
            .build()
    );

    public static void init() {
    }
}
