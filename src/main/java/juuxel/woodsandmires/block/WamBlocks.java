package juuxel.woodsandmires.block;

import juuxel.woodsandmires.WoodsAndMires;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.fabricmc.fabric.api.registry.StrippableBlockRegistry;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.FenceBlock;
import net.minecraft.block.FenceGateBlock;
import net.minecraft.block.FlowerPotBlock;
import net.minecraft.block.Material;
import net.minecraft.block.PillarBlock;
import net.minecraft.block.PressurePlateBlock;
import net.minecraft.block.SaplingBlock;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.TallFlowerBlock;
import net.minecraft.block.WoodenButtonBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.client.color.world.FoliageColors;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.TallBlockItem;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.registry.Registry;
import org.jetbrains.annotations.Nullable;

public final class WamBlocks {
    public static final Block PINE_LOG = new PillarBlock(copyWoodSettings(Blocks.OAK_LOG));
    public static final Block GROUND_PINE_LOG = new GroundLogBlock(PINE_LOG, AbstractBlock.Settings.copy(PINE_LOG));
    public static final Block PINE_PLANKS = new Block(copyWoodSettings(Blocks.OAK_PLANKS));
    public static final Block PINE_SLAB = new SlabBlock(copyWoodSettings(Blocks.OAK_SLAB));
    public static final Block PINE_STAIRS = new StairsBlock(PINE_PLANKS.getDefaultState(), copyWoodSettings(Blocks.OAK_STAIRS)) {};
    public static final Block PINE_FENCE = new FenceBlock(copyWoodSettings(Blocks.OAK_FENCE));
    public static final Block PINE_FENCE_GATE = new FenceGateBlock(copyWoodSettings(Blocks.OAK_FENCE_GATE));
    public static final Block PINE_BUTTON = new WoodenButtonBlock(copyWoodSettings(Blocks.OAK_BUTTON)) {};
    public static final Block PINE_PRESSURE_PLATE = new PressurePlateBlock(PressurePlateBlock.ActivationRule.EVERYTHING, copyWoodSettings(Blocks.OAK_PRESSURE_PLATE)) {};
    public static final Block PINE_LEAVES = Blocks.createLeavesBlock(BlockSoundGroup.GRASS);
    public static final Block PINE_SAPLING = new SaplingBlock(new PineSaplingGenerator(), AbstractBlock.Settings.copy(Blocks.OAK_SAPLING)) {};
    public static final Block POTTED_PINE_SAPLING = new FlowerPotBlock(PINE_SAPLING, AbstractBlock.Settings.of(Material.DECORATION).breakInstantly());
    public static final Block PINE_WOOD = new PillarBlock(copyWoodSettings(Blocks.OAK_WOOD));
    public static final Block GROUND_PINE_WOOD = new WoodVariantBlock(PINE_WOOD, AbstractBlock.Settings.copy(PINE_WOOD));
    public static final Block STRIPPED_PINE_LOG = new PillarBlock(copyWoodSettings(Blocks.STRIPPED_OAK_LOG));
    public static final Block STRIPPED_PINE_WOOD = new PillarBlock(copyWoodSettings(Blocks.STRIPPED_OAK_WOOD));
    public static final Block PINE_SNAG_LOG = new PillarBlock(copyWoodSettings(Blocks.STRIPPED_OAK_LOG));
    public static final Block PINE_SNAG_WOOD = new PillarBlock(copyWoodSettings(Blocks.STRIPPED_OAK_WOOD));
    public static final Block PINE_SNAG_BRANCH = new BranchBlock(copyWoodSettings(PINE_SNAG_WOOD));
    public static final Block PINE_SHRUB_LOG = new ShrubLogBlock(copyWoodSettings(PINE_LOG).nonOpaque());
    public static final Block FIREWEED = new TallFlowerBlock(createFlowerSettings());
    public static final Block TANSY = new BigFlowerBlock(StatusEffects.SLOW_FALLING, 10, createFlowerSettings());
    public static final Block POTTED_TANSY = new FlowerPotBlock(TANSY, AbstractBlock.Settings.of(Material.DECORATION).breakInstantly());

    private WamBlocks() {
    }

    public static void init() {
        register("pine_log", PINE_LOG);
        register("ground_pine_log", GROUND_PINE_LOG);
        register("pine_planks", PINE_PLANKS);
        register("pine_slab", PINE_SLAB);
        register("pine_stairs", PINE_STAIRS);
        register("pine_fence", PINE_FENCE, ItemGroup.DECORATIONS);
        register("pine_fence_gate", PINE_FENCE_GATE, ItemGroup.REDSTONE);
        register("pine_button", PINE_BUTTON, ItemGroup.REDSTONE);
        register("pine_pressure_plate", PINE_PRESSURE_PLATE, ItemGroup.REDSTONE);
        register("pine_leaves", PINE_LEAVES, ItemGroup.DECORATIONS);
        register("pine_sapling", PINE_SAPLING, ItemGroup.DECORATIONS);
        register("potted_pine_sapling", POTTED_PINE_SAPLING, (Item) null);
        register("pine_wood", PINE_WOOD);
        register("ground_pine_wood", GROUND_PINE_WOOD);
        register("stripped_pine_log", STRIPPED_PINE_LOG);
        register("stripped_pine_wood", STRIPPED_PINE_WOOD);
        register("pine_snag_log", PINE_SNAG_LOG);
        register("pine_snag_wood", PINE_SNAG_WOOD);
        register("pine_snag_branch", PINE_SNAG_BRANCH, (Item) null);
        register("pine_shrub_log", PINE_SHRUB_LOG);
        register("fireweed", FIREWEED, new TallBlockItem(FIREWEED, new Item.Settings().group(ItemGroup.DECORATIONS)));
        register("tansy", TANSY, ItemGroup.DECORATIONS);
        register("potted_tansy", POTTED_TANSY, (Item) null);

        FlammableBlockRegistry fbr = FlammableBlockRegistry.getDefaultInstance();
        fbr.add(PINE_LOG, 5, 5);
        fbr.add(GROUND_PINE_LOG, 5, 5);
        fbr.add(PINE_WOOD, 5, 5);
        fbr.add(GROUND_PINE_WOOD, 5, 5);
        fbr.add(STRIPPED_PINE_LOG, 5, 5);
        fbr.add(STRIPPED_PINE_WOOD, 5, 5);
        fbr.add(PINE_SNAG_LOG, 5, 5);
        fbr.add(PINE_SNAG_WOOD, 5, 5);
        fbr.add(PINE_SNAG_BRANCH, 5, 5);
        fbr.add(PINE_SHRUB_LOG, 5, 5);
        fbr.add(PINE_PLANKS, 5, 20);
        fbr.add(PINE_SLAB, 5, 20);
        fbr.add(PINE_STAIRS, 5, 20);
        fbr.add(PINE_FENCE, 5, 20);
        fbr.add(PINE_FENCE_GATE, 5, 20);
        fbr.add(PINE_LEAVES, 5, 20);

        FuelRegistry fr = FuelRegistry.INSTANCE;
        fr.add(PINE_FENCE, 300);
        fr.add(PINE_FENCE_GATE, 300);

        StrippableBlockRegistry.register(PINE_LOG, STRIPPED_PINE_LOG);
        StrippableBlockRegistry.register(GROUND_PINE_LOG, STRIPPED_PINE_LOG);
        StrippableBlockRegistry.register(PINE_WOOD, STRIPPED_PINE_WOOD);
        StrippableBlockRegistry.register(GROUND_PINE_WOOD, STRIPPED_PINE_WOOD);
    }

    @Environment(EnvType.CLIENT)
    public static void initClient() {
        BlockRenderLayerMap.INSTANCE.putBlocks(
            RenderLayer.getCutoutMipped(),
            PINE_LEAVES,
            PINE_SHRUB_LOG
        );

        BlockRenderLayerMap.INSTANCE.putBlocks(
            RenderLayer.getCutout(),
            PINE_SAPLING,
            POTTED_PINE_SAPLING,
            FIREWEED,
            TANSY,
            POTTED_TANSY
        );

        ColorProviderRegistry.BLOCK.register(
            (state, world, pos, tintIndex) -> {
                if (world != null && pos != null) {
                    return BiomeColors.getFoliageColor(world, pos);
                }

                return FoliageColors.getColor(0.5, 1.0);
            },
            FIREWEED, TANSY, POTTED_TANSY, PINE_LEAVES, PINE_SHRUB_LOG
        );

        ColorProviderRegistry.ITEM.register(
            (stack, tintIndex) -> {
                if (tintIndex > 0) return -1;

                BlockColors colors = MinecraftClient.getInstance().getBlockColors();
                return colors.getColor(((BlockItem) stack.getItem()).getBlock().getDefaultState(), null, null, tintIndex);
            },
            FIREWEED, TANSY, PINE_LEAVES
        );
    }

    private static void register(String id, Block block) {
        register(id, block, ItemGroup.BUILDING_BLOCKS);
    }

    private static void register(String id, Block block, ItemGroup itemGroup) {
        register(id, block, new BlockItem(block, new Item.Settings().group(itemGroup)));
    }

    private static void register(String id, Block block, @Nullable Item item) {
        Registry.register(Registry.BLOCK, WoodsAndMires.id(id), block);

        if (item != null) {
            Registry.register(Registry.ITEM, WoodsAndMires.id(id), item);
        }
    }

    private static AbstractBlock.Settings copyWoodSettings(Block block) {
        return AbstractBlock.Settings.copy(block);
    }

    private static AbstractBlock.Settings createFlowerSettings() {
        return AbstractBlock.Settings.of(Material.REPLACEABLE_PLANT)
            .noCollision()
            .breakInstantly()
            .sounds(BlockSoundGroup.GRASS);
    }
}
