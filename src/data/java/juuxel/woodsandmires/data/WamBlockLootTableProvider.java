package juuxel.woodsandmires.data;

import juuxel.woodsandmires.block.WamBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.block.TallPlantBlock;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.data.server.BlockLootTableGenerator;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.BinomialLootNumberProvider;

public final class WamBlockLootTableProvider extends FabricBlockLootTableProvider {
    public WamBlockLootTableProvider(FabricDataGenerator dataGenerator) {
        super(dataGenerator);
    }

    @Override
    protected void generateBlockLootTables() {
        addDrop(WamBlocks.PINE_LOG);
        addDrop(WamBlocks.AGED_PINE_LOG);
        addDrop(WamBlocks.PINE_PLANKS);
        addDrop(WamBlocks.PINE_SLAB, BlockLootTableGenerator::slabDrops);
        addDrop(WamBlocks.PINE_STAIRS);
        addDrop(WamBlocks.PINE_FENCE);
        addDrop(WamBlocks.PINE_FENCE_GATE);
        addDrop(WamBlocks.PINE_BUTTON);
        addDrop(WamBlocks.PINE_PRESSURE_PLATE);
        addDrop(WamBlocks.PINE_SIGN);
        addDrop(WamBlocks.PINE_DOOR, BlockLootTableGenerator::doorDrops);
        addDrop(WamBlocks.PINE_TRAPDOOR);
        addDrop(WamBlocks.PINE_LEAVES, block -> leavesDrop(block, WamBlocks.PINE_SAPLING, SAPLING_DROP_CHANCE));
        addDrop(WamBlocks.PINE_SAPLING);
        addPottedPlantDrop(WamBlocks.POTTED_PINE_SAPLING);
        addDrop(WamBlocks.PINE_WOOD);
        addDrop(WamBlocks.AGED_PINE_WOOD);
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
        addPottedPlantDrop(WamBlocks.POTTED_TANSY);
        addDrop(WamBlocks.FELL_LICHEN);
        addPottedPlantDrop(WamBlocks.POTTED_FELL_LICHEN);
        addDrop(WamBlocks.HEATHER);
        addPottedPlantDrop(WamBlocks.POTTED_HEATHER);
    }
}
