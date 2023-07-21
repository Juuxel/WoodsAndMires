package juuxel.woodsandmires.data;

import juuxel.woodsandmires.block.WamBlocks;
import juuxel.woodsandmires.data.builtin.CommonItemTags;
import juuxel.woodsandmires.item.WamItemTags;
import juuxel.woodsandmires.item.WamItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.tag.ItemTags;
import net.minecraft.tag.TagKey;

public final class WamItemTagProvider extends FabricTagProvider.ItemTagProvider {
    public WamItemTagProvider(FabricDataGenerator dataGenerator) {
        super(dataGenerator);
    }

    @Override
    protected void generateTags() {
        // Minecraft tags
        builder(ItemTags.BOATS)
            .add(WamItems.PINE_BOAT);
        builder(ItemTags.LEAVES)
            .add(WamBlocks.PINE_LEAVES);
        builder(ItemTags.LOGS_THAT_BURN)
            .addTag(WamItemTags.PINE_LOGS);
        builder(ItemTags.PLANKS)
            .add(WamBlocks.PINE_PLANKS);
        builder(ItemTags.SAPLINGS)
            .add(WamBlocks.PINE_SAPLING);
        builder(ItemTags.SIGNS)
            .add(WamBlocks.PINE_SIGN);
        builder(ItemTags.SMALL_FLOWERS)
            .add(WamBlocks.HEATHER, WamBlocks.TANSY);
        builder(ItemTags.TALL_FLOWERS)
            .add(WamBlocks.FIREWEED);
        builder(ItemTags.WOODEN_BUTTONS)
            .add(WamBlocks.PINE_BUTTON);
        builder(ItemTags.WOODEN_DOORS)
            .add(WamBlocks.PINE_DOOR);
        builder(ItemTags.WOODEN_FENCES)
            .add(WamBlocks.PINE_FENCE);
        builder(ItemTags.WOODEN_PRESSURE_PLATES)
            .add(WamBlocks.PINE_PRESSURE_PLATE);
        builder(ItemTags.WOODEN_SLABS)
            .add(WamBlocks.PINE_SLAB);
        builder(ItemTags.WOODEN_STAIRS)
            .add(WamBlocks.PINE_STAIRS);
        builder(ItemTags.WOODEN_TRAPDOORS)
            .add(WamBlocks.PINE_TRAPDOOR);

        // WaM tags
        builder(WamItemTags.PINE_LOGS)
            .addTag(WamItemTags.THICK_PINE_LOGS)
            .add(WamBlocks.PINE_SHRUB_LOG);
        builder(WamItemTags.THICK_PINE_LOGS)
            .add(WamBlocks.PINE_LOG, WamBlocks.AGED_PINE_LOG)
            .add(WamBlocks.PINE_WOOD, WamBlocks.AGED_PINE_WOOD)
            .add(WamBlocks.STRIPPED_PINE_LOG, WamBlocks.STRIPPED_PINE_WOOD)
            .add(WamBlocks.PINE_SNAG_LOG, WamBlocks.PINE_SNAG_WOOD);

        // Common tags
        builder(CommonItemTags.HONEY)
            .add(WamItems.PINE_CONE_JAM);
        builder(CommonItemTags.JAMS)
            .add(WamItems.PINE_CONE_JAM);
        builder(CommonItemTags.PINE_CONES)
            .add(WamItems.PINE_CONE);
        builder(CommonItemTags.SUGAR)
            .add(Items.SUGAR);
        builder(CommonItemTags.WOODEN_RODS)
            .add(Items.STICK);
    }

    private Builder builder(TagKey<Item> tag) {
        return new Builder(getOrCreateTagBuilder(tag));
    }

    private static final class Builder {
        private final FabricTagProvider<Item>.FabricTagBuilder<Item> parent;

        private Builder(FabricTagProvider<Item>.FabricTagBuilder<Item> parent) {
            this.parent = parent;
        }

        public Builder add(ItemConvertible... items) {
            for (ItemConvertible item : items) {
                parent.add(item.asItem());
            }

            return this;
        }

        public Builder addTag(TagKey<Item> tag) {
            parent.addTag(tag);
            return this;
        }
    }
}
