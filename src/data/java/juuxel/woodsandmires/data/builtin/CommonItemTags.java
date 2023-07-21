package juuxel.woodsandmires.data.builtin;

import net.minecraft.item.Item;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public final class CommonItemTags {
    public static final TagKey<Item> HONEY = of("honey");
    public static final TagKey<Item> JAMS = of("jams");
    public static final TagKey<Item> PINE_CONES = of("pine_cones");
    public static final TagKey<Item> SUGAR = of("sugar");
    public static final TagKey<Item> WOODEN_RODS = of("wooden_rods");

    private static TagKey<Item> of(String path) {
        return TagKey.of(Registry.ITEM_KEY, new Identifier("c", path));
    }
}
