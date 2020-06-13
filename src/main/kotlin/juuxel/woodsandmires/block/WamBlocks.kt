package juuxel.woodsandmires.block

import juuxel.woodsandmires.WoodsAndMires
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry
import net.minecraft.block.AbstractBlock.Settings
import net.minecraft.block.Block
import net.minecraft.block.Blocks
import net.minecraft.block.FenceBlock
import net.minecraft.block.FenceGateBlock
import net.minecraft.block.PillarBlock
import net.minecraft.block.PressurePlateBlock
import net.minecraft.block.SlabBlock
import net.minecraft.block.StairsBlock
import net.minecraft.block.WoodButtonBlock
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraft.util.registry.Registry

object WamBlocks {
    // TODO:
    //   - "leaves"
    //   - sapling
    //   - wood
    //   - fuel values
    //   - recipes + advancements for them

    val PINE_LOG: Block = PillarBlock(Settings.copy(Blocks.OAK_LOG))
    val PINE_PLANKS: Block = Block(Settings.copy(Blocks.OAK_PLANKS))
    val PINE_SLAB: Block = SlabBlock(Settings.copy(Blocks.OAK_SLAB))
    val PINE_STAIRS: Block = object : StairsBlock(PINE_PLANKS.defaultState, Settings.copy(Blocks.OAK_SLAB)) {}
    val PINE_FENCE: Block = FenceBlock(Settings.copy(Blocks.OAK_FENCE))
    val PINE_FENCE_GATE: Block = FenceGateBlock(Settings.copy(Blocks.OAK_FENCE_GATE))
    val PINE_BUTTON: Block = object : WoodButtonBlock(Settings.copy(Blocks.OAK_BUTTON)) {}
    val PINE_PRESSURE_PLATE: Block = object : PressurePlateBlock(
        ActivationRule.EVERYTHING, Settings.copy(Blocks.OAK_PRESSURE_PLATE)
    ) {}

    fun init() {
        register("pine_log", PINE_LOG)
        register("pine_planks", PINE_PLANKS)
        register("pine_slab", PINE_SLAB)
        register("pine_stairs", PINE_STAIRS)
        register("pine_fence", PINE_FENCE)
        register("pine_fence_gate", PINE_FENCE_GATE)
        register("pine_button", PINE_BUTTON)
        register("pine_pressure_plate", PINE_PRESSURE_PLATE)

        FlammableBlockRegistry.getDefaultInstance().apply {
            add(PINE_LOG, 5, 5)
            add(PINE_PLANKS, 5, 20)
            add(PINE_SLAB, 5, 20)
            add(PINE_STAIRS, 5, 20)
            add(PINE_FENCE, 5, 20)
            add(PINE_FENCE_GATE, 5, 20)
        }
    }

    @Environment(EnvType.CLIENT)
    fun initClient() {
    }

    private fun register(id: String, block: Block, itemGroup: ItemGroup = ItemGroup.BUILDING_BLOCKS) {
        Registry.register(Registry.BLOCK, WoodsAndMires.id(id), block)
        Registry.register(Registry.ITEM, WoodsAndMires.id(id), BlockItem(block, Item.Settings().group(itemGroup)))
    }
}
