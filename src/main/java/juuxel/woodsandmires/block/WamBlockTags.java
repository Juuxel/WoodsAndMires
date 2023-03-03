package juuxel.woodsandmires.block;

import juuxel.woodsandmires.WoodsAndMires;
import net.minecraft.block.Block;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;

public final class WamBlockTags {
    public static TagKey<Block> LICHEN_PLANTABLE_ON = tag("lichen_plantable_on");

    private static TagKey<Block> tag(String id) {
        return TagKey.of(RegistryKeys.BLOCK, WoodsAndMires.id(id));
    }
}
