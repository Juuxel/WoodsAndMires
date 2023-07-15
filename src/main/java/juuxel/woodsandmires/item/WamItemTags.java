package juuxel.woodsandmires.item;

import juuxel.woodsandmires.WoodsAndMires;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;

public final class WamItemTags {
    public static TagKey<Item> PINE_LOGS = tag("pine_logs");
    public static TagKey<Item> THICK_PINE_LOGS = tag("thick_pine_logs");

    private static TagKey<Item> tag(String id) {
        return TagKey.of(RegistryKeys.ITEM, WoodsAndMires.id(id));
    }
}
