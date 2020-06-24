package juuxel.woodsandmires.block

import juuxel.woodsandmires.WoodsAndMires
import juuxel.woodsandmires.mixin.BlocksAccessor
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry
import net.fabricmc.fabric.api.registry.FuelRegistry
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags
import net.minecraft.block.AbstractBlock.Settings
import net.minecraft.block.Block
import net.minecraft.block.Blocks
import net.minecraft.block.FenceBlock
import net.minecraft.block.FenceGateBlock
import net.minecraft.block.FlowerPotBlock
import net.minecraft.block.Material
import net.minecraft.block.Material.SUPPORTED as PART_MATERIAL // Let's not use incorrect names...
import net.minecraft.block.PillarBlock
import net.minecraft.block.PressurePlateBlock
import net.minecraft.block.SaplingBlock
import net.minecraft.block.SlabBlock
import net.minecraft.block.StairsBlock
import net.minecraft.block.TallFlowerBlock
import net.minecraft.block.WoodButtonBlock
import net.minecraft.client.MinecraftClient
import net.minecraft.client.color.block.BlockColorProvider
import net.minecraft.client.color.item.ItemColorProvider
import net.minecraft.client.color.world.BiomeColors
import net.minecraft.client.color.world.FoliageColors
import net.minecraft.client.render.RenderLayer
import net.minecraft.entity.effect.StatusEffects
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraft.item.TallBlockItem
import net.minecraft.sound.BlockSoundGroup
import net.minecraft.util.registry.Registry

object WamBlocks {
    val PINE_LOG: Block = PillarBlock(copyWoodSettings(Blocks.OAK_LOG))
    val PINE_PLANKS: Block = Block(copyWoodSettings(Blocks.OAK_PLANKS))
    val PINE_SLAB: Block = SlabBlock(copyWoodSettings(Blocks.OAK_SLAB))
    val PINE_STAIRS: Block = object : StairsBlock(PINE_PLANKS.defaultState, copyWoodSettings(Blocks.OAK_SLAB)) {}
    val PINE_FENCE: Block = FenceBlock(copyWoodSettings(Blocks.OAK_FENCE))
    val PINE_FENCE_GATE: Block = FenceGateBlock(copyWoodSettings(Blocks.OAK_FENCE_GATE))
    val PINE_BUTTON: Block = object : WoodButtonBlock(copyWoodSettings(Blocks.OAK_BUTTON)) {}
    val PINE_PRESSURE_PLATE: Block = object : PressurePlateBlock(
        ActivationRule.EVERYTHING, copyWoodSettings(Blocks.OAK_PRESSURE_PLATE)
    ) {}
    val PINE_LEAVES: Block = BlocksAccessor.callCreateLeavesBlock()
    val PINE_SAPLING: Block = object : SaplingBlock(PineSaplingGenerator, Settings.copy(Blocks.OAK_SAPLING)) {}
    val POTTED_PINE_SAPLING: Block = FlowerPotBlock(PINE_SAPLING, Settings.of(PART_MATERIAL).breakInstantly())
    val PINE_WOOD: Block = PillarBlock(copyWoodSettings(Blocks.OAK_WOOD))
    val STRIPPED_PINE_LOG: Block = PillarBlock(copyWoodSettings(Blocks.STRIPPED_OAK_LOG))
    val STRIPPED_PINE_WOOD: Block = PillarBlock(copyWoodSettings(Blocks.STRIPPED_OAK_WOOD))
    val PINE_SNAG_LOG: Block = PillarBlock(copyWoodSettings(Blocks.STRIPPED_OAK_LOG))
    val PINE_SNAG_WOOD: Block = PillarBlock(copyWoodSettings(Blocks.STRIPPED_OAK_WOOD))
    val PINE_SNAG_BRANCH: Block = BranchBlock(copyWoodSettings(PINE_SNAG_WOOD))
    val PINE_SHRUB_LOG: Block = ShrubLogBlock(copyWoodSettings(PINE_LOG).nonOpaque())
    val FIREWEED: Block = TallFlowerBlock(createFlowerSettings())
    val TANSY: Block = BigFlowerBlock(StatusEffects.SLOW_FALLING, 10, createFlowerSettings())
    val POTTED_TANSY: Block = FlowerPotBlock(TANSY, Settings.of(PART_MATERIAL).breakInstantly())

    fun init() {
        register("pine_log", PINE_LOG)
        register("pine_planks", PINE_PLANKS)
        register("pine_slab", PINE_SLAB)
        register("pine_stairs", PINE_STAIRS)
        register("pine_fence", PINE_FENCE, ItemGroup.DECORATIONS)
        register("pine_fence_gate", PINE_FENCE_GATE, ItemGroup.REDSTONE)
        register("pine_button", PINE_BUTTON, ItemGroup.REDSTONE)
        register("pine_pressure_plate", PINE_PRESSURE_PLATE, ItemGroup.REDSTONE)
        register("pine_leaves", PINE_LEAVES, ItemGroup.DECORATIONS)
        register("pine_sapling", PINE_SAPLING, ItemGroup.DECORATIONS)
        register("potted_pine_sapling", POTTED_PINE_SAPLING, item = null)
        register("pine_wood", PINE_WOOD)
        register("stripped_pine_log", STRIPPED_PINE_LOG)
        register("stripped_pine_wood", STRIPPED_PINE_WOOD)
        register("pine_snag_log", PINE_SNAG_LOG)
        register("pine_snag_wood", PINE_SNAG_WOOD)
        register("pine_snag_branch", PINE_SNAG_BRANCH, item = null)
        register("pine_shrub_log", PINE_SHRUB_LOG)
        register("fireweed", FIREWEED, TallBlockItem(FIREWEED, Item.Settings().group(ItemGroup.DECORATIONS)))
        register("tansy", TANSY, ItemGroup.DECORATIONS)
        register("potted_tansy", POTTED_TANSY, item = null)

        FlammableBlockRegistry.getDefaultInstance().apply {
            add(PINE_LOG, 5, 5)
            add(PINE_WOOD, 5, 5)
            add(STRIPPED_PINE_LOG, 5, 5)
            add(STRIPPED_PINE_WOOD, 5, 5)
            add(PINE_SNAG_LOG, 5, 5)
            add(PINE_SNAG_WOOD, 5, 5)
            add(PINE_SNAG_BRANCH, 5, 5)
            add(PINE_SHRUB_LOG, 5, 5)
            add(PINE_PLANKS, 5, 20)
            add(PINE_SLAB, 5, 20)
            add(PINE_STAIRS, 5, 20)
            add(PINE_FENCE, 5, 20)
            add(PINE_FENCE_GATE, 5, 20)
            add(PINE_LEAVES, 5, 20)
        }

        FuelRegistry.INSTANCE.apply {
            add(PINE_FENCE, 300)
            add(PINE_FENCE_GATE, 300)
        }
    }

    @Environment(EnvType.CLIENT)
    fun initClient() {
        BlockRenderLayerMap.INSTANCE.putBlocks(
            RenderLayer.getCutoutMipped(),
            PINE_LEAVES,
            PINE_SHRUB_LOG
        )

        BlockRenderLayerMap.INSTANCE.putBlocks(
            RenderLayer.getCutout(),
            PINE_SAPLING,
            POTTED_PINE_SAPLING,
            FIREWEED,
            TANSY,
            POTTED_TANSY
        )

        ColorProviderRegistry.BLOCK.register(BlockColorProvider { _, world, pos, _ ->
            if (world != null && pos != null) BiomeColors.getFoliageColor(world, pos)
            else FoliageColors.getColor(0.5, 1.0)
        }, FIREWEED, TANSY, POTTED_TANSY, PINE_LEAVES, PINE_SHRUB_LOG)

        ColorProviderRegistry.ITEM.register(ItemColorProvider { stack, tintIndex ->
            if (tintIndex > 0) return@ItemColorProvider -1

            val colors = MinecraftClient.getInstance().blockColors
            colors.getColor((stack.item as BlockItem).block.defaultState, null, null, tintIndex)
        }, FIREWEED, TANSY, PINE_LEAVES)
    }

    private fun register(id: String, block: Block, itemGroup: ItemGroup = ItemGroup.BUILDING_BLOCKS) =
        register(id, block, BlockItem(block, Item.Settings().group(itemGroup)))

    private fun register(id: String, block: Block, item: Item?) {
        Registry.register(Registry.BLOCK, WoodsAndMires.id(id), block)

        if (item != null) {
            Registry.register(Registry.ITEM, WoodsAndMires.id(id), item)
        }
    }

    private fun copyWoodSettings(block: Block): Settings =
        FabricBlockSettings.copyOf(block)
            .breakByTool(FabricToolTags.AXES)

    private fun createFlowerSettings(): Settings =
        Settings.of(Material.REPLACEABLE_PLANT)
            .noCollision()
            .breakInstantly()
            .sounds(BlockSoundGroup.GRASS)
}
