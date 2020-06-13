package juuxel.woodsandmires.block

import juuxel.woodsandmires.WoodsAndMires
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.block.AbstractBlock
import net.minecraft.block.Block
import net.minecraft.block.Blocks
import net.minecraft.block.PillarBlock
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraft.util.registry.Registry

object WamBlocks {
    // TODO:
    //   - fences + gates
    //   - slabs
    //   - stairs
    //   - buttons
    //   - pressure plates
    //   - "leaves"
    //   - sapling

    val PINE_LOG: Block = PillarBlock(AbstractBlock.Settings.copy(Blocks.OAK_LOG))
    val PINE_PLANKS: Block = Block(AbstractBlock.Settings.copy(Blocks.OAK_PLANKS))

    fun init() {
        register("pine_log", PINE_LOG)
        register("pine_planks", PINE_PLANKS)
    }

    @Environment(EnvType.CLIENT)
    fun initClient() {
    }

    private fun register(id: String, block: Block, itemGroup: ItemGroup = ItemGroup.BUILDING_BLOCKS) {
        Registry.register(Registry.BLOCK, WoodsAndMires.id(id), block)
        Registry.register(Registry.ITEM, WoodsAndMires.id(id), BlockItem(block, Item.Settings().group(itemGroup)))
    }
}
