package juuxel.woodsandmires.item;

import juuxel.woodsandmires.block.WamBlocks;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;

import java.util.List;

public final class WamItemGroups {
    public static void init() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.BUILDING_BLOCKS).register(entries -> {
            entries.addAfter(Items.WARPED_BUTTON,
                WamBlocks.PINE_LOG,
                WamBlocks.AGED_PINE_LOG,
                WamBlocks.PINE_SHRUB_LOG,
                WamBlocks.PINE_WOOD,
                WamBlocks.AGED_PINE_WOOD,
                WamBlocks.STRIPPED_PINE_LOG,
                WamBlocks.STRIPPED_PINE_WOOD,
                WamBlocks.PINE_SNAG_LOG,
                WamBlocks.PINE_SNAG_WOOD,
                WamBlocks.PINE_PLANKS,
                WamBlocks.PINE_STAIRS,
                WamBlocks.PINE_SLAB,
                WamBlocks.PINE_FENCE,
                WamBlocks.PINE_FENCE_GATE,
                WamBlocks.PINE_DOOR,
                WamBlocks.PINE_TRAPDOOR,
                WamBlocks.PINE_PRESSURE_PLATE,
                WamBlocks.PINE_BUTTON
            );
        });
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL).register(entries -> {
            entries.addAfter(Items.WARPED_STEM,
                WamBlocks.PINE_LOG,
                WamBlocks.AGED_PINE_LOG,
                WamBlocks.PINE_SHRUB_LOG,
                WamBlocks.PINE_SNAG_LOG);
            entries.addAfter(Items.FLOWERING_AZALEA_LEAVES,
                WamBlocks.PINE_LEAVES);
            entries.addAfter(Items.FLOWERING_AZALEA,
                WamBlocks.PINE_SAPLING);
            entries.addAfter(Items.LILY_OF_THE_VALLEY,
                WamBlocks.TANSY,
                WamBlocks.HEATHER);
            entries.addAfter(Items.PEONY,
                WamBlocks.FIREWEED);
            entries.addBefore(Items.GLOW_LICHEN,
                WamBlocks.FELL_LICHEN);
        });
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FUNCTIONAL).register(entries -> {
            addAfterFirstEnabled(entries, List.of(Items.WARPED_HANGING_SIGN, Items.WARPED_SIGN),
                WamBlocks.PINE_SIGN);
        });
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(entries -> {
            addAfterFirstEnabled(entries, List.of(Items.BAMBOO_CHEST_RAFT, Items.MANGROVE_CHEST_BOAT),
                WamItems.PINE_BOAT,
                WamItems.PINE_CHEST_BOAT);
        });
    }

    private static void addAfterFirstEnabled(FabricItemGroupEntries entries, List<Item> after, ItemConvertible... items) {
        Item start = after.stream()
            .filter(item -> item.getRequiredFeatures().isSubsetOf(entries.getEnabledFeatures()))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Could not find any of the items " + after));
        entries.addAfter(start, items);
    }
}
