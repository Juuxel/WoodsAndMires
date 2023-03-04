package juuxel.woodsandmires.data;

import juuxel.woodsandmires.block.WamBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.block.TallPlantBlock;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.BinomialLootNumberProvider;

public final class WamBlockLootTableProvider extends FabricBlockLootTableProvider {
    public WamBlockLootTableProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generate() {
        addDrop(WamBlocks.PINE_LOG);
        addDrop(WamBlocks.GROUND_PINE_LOG);
        addDrop(WamBlocks.PINE_PLANKS);
        addDrop(WamBlocks.PINE_SLAB, this::slabDrops);
        addDrop(WamBlocks.PINE_STAIRS);
        addDrop(WamBlocks.PINE_FENCE);
        addDrop(WamBlocks.PINE_FENCE_GATE);
        addDrop(WamBlocks.PINE_BUTTON);
        addDrop(WamBlocks.PINE_PRESSURE_PLATE);
        addDrop(WamBlocks.PINE_SIGN);
        addDrop(WamBlocks.PINE_DOOR, this::doorDrops);
        addDrop(WamBlocks.PINE_LEAVES, block -> leavesDrops(block, WamBlocks.PINE_SAPLING, SAPLING_DROP_CHANCE));
        addDrop(WamBlocks.PINE_SAPLING);
        addPottedPlantDrops(WamBlocks.POTTED_PINE_SAPLING);
        addDrop(WamBlocks.PINE_WOOD);
        addDrop(WamBlocks.GROUND_PINE_WOOD);
        addDrop(WamBlocks.STRIPPED_PINE_LOG);
        addDrop(WamBlocks.STRIPPED_PINE_WOOD);
        addDrop(WamBlocks.PINE_SNAG_LOG);
        addDrop(WamBlocks.PINE_SNAG_WOOD);
        addDrop(WamBlocks.PINE_SNAG_BRANCH,
            LootTable.builder()
                .pool(addSurvivesExplosionCondition(Items.STICK, LootPool.builder()
                    .with(ItemEntry.builder(Items.STICK)
                        .apply(SetCountLootFunction.builder(BinomialLootNumberProvider.create(4, 0.8f)))
                    )
                ))
        );
        addDrop(WamBlocks.PINE_SHRUB_LOG);
        addDrop(WamBlocks.FIREWEED, block -> dropsWithProperty(block, TallPlantBlock.HALF, DoubleBlockHalf.LOWER));
        addDrop(WamBlocks.TANSY);
        addPottedPlantDrops(WamBlocks.POTTED_TANSY);
        addDrop(WamBlocks.FELL_LICHEN);
        addPottedPlantDrops(WamBlocks.POTTED_FELL_LICHEN);
        addDrop(WamBlocks.HEATHER);
        addPottedPlantDrops(WamBlocks.POTTED_HEATHER);
    }
}
