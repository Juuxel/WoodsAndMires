package juuxel.woodsandmires.block;

import com.google.common.base.Suppliers;
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
import net.minecraft.block.DoorBlock;
import net.minecraft.block.FenceBlock;
import net.minecraft.block.FenceGateBlock;
import net.minecraft.block.FlowerPotBlock;
import net.minecraft.block.MapColor;
import net.minecraft.block.Material;
import net.minecraft.block.PillarBlock;
import net.minecraft.block.PressurePlateBlock;
import net.minecraft.block.SaplingBlock;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.TallFlowerBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.client.color.world.FoliageColors;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.SignItem;
import net.minecraft.item.TallBlockItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvents;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public final class WamBlocks {
    public static final Block PINE_LOG = new PillarBlock(copyWoodSettings(Blocks.OAK_LOG));
    public static final Block GROUND_PINE_LOG = new GroundLogBlock(PINE_LOG, AbstractBlock.Settings.copy(PINE_LOG));
    public static final Block PINE_PLANKS = new Block(copyWoodSettings(Blocks.OAK_PLANKS));
    public static final Block PINE_SLAB = new SlabBlock(copyWoodSettings(Blocks.OAK_SLAB));
    public static final Block PINE_STAIRS = new StairsBlock(PINE_PLANKS.getDefaultState(), copyWoodSettings(Blocks.OAK_STAIRS));
    public static final Block PINE_FENCE = new FenceBlock(copyWoodSettings(Blocks.OAK_FENCE));
    public static final Block PINE_FENCE_GATE = new FenceGateBlock(copyWoodSettings(Blocks.OAK_FENCE_GATE), SoundEvents.BLOCK_FENCE_GATE_CLOSE, SoundEvents.BLOCK_FENCE_GATE_OPEN);
    public static final Block PINE_DOOR = new DoorBlock(copyWoodSettings(Blocks.OAK_DOOR), SoundEvents.BLOCK_WOODEN_DOOR_CLOSE, SoundEvents.BLOCK_WOODEN_DOOR_OPEN);
    public static final Block PINE_BUTTON = Blocks.createWoodenButtonBlock();
    public static final Block PINE_PRESSURE_PLATE = new PressurePlateBlock(PressurePlateBlock.ActivationRule.EVERYTHING, copyWoodSettings(Blocks.OAK_PRESSURE_PLATE), SoundEvents.BLOCK_WOODEN_PRESSURE_PLATE_CLICK_OFF, SoundEvents.BLOCK_WOODEN_PRESSURE_PLATE_CLICK_ON);
    public static final Block PINE_SIGN = new WamSignBlock(copyWoodSettings(Blocks.OAK_SIGN), WamSignTypes.PINE);
    // We have to evaluate this *after* PINE_SIGN has registered due to the loot table condition.
    public static final Supplier<Block> PINE_WALL_SIGN = Suppliers.memoize(() -> new WamWallSignBlock(copyWoodSettings(PINE_SIGN).dropsLike(PINE_SIGN), WamSignTypes.PINE));
    public static final Block PINE_LEAVES = Blocks.createLeavesBlock(BlockSoundGroup.GRASS);
    public static final Block PINE_SAPLING = new SaplingBlock(new PineSaplingGenerator(), AbstractBlock.Settings.copy(Blocks.OAK_SAPLING));
    public static final Block POTTED_PINE_SAPLING = new FlowerPotBlock(PINE_SAPLING, createFlowerPotSettings());
    public static final Block PINE_WOOD = new PillarBlock(copyWoodSettings(Blocks.OAK_WOOD));
    public static final Block GROUND_PINE_WOOD = new WoodVariantBlock(PINE_WOOD, AbstractBlock.Settings.copy(PINE_WOOD));
    public static final Block STRIPPED_PINE_LOG = new PillarBlock(copyWoodSettings(Blocks.STRIPPED_OAK_LOG));
    public static final Block STRIPPED_PINE_WOOD = new PillarBlock(copyWoodSettings(Blocks.STRIPPED_OAK_WOOD));
    public static final Block PINE_SNAG_LOG = new PillarBlock(copyWoodSettings(Blocks.STRIPPED_OAK_LOG));
    public static final Block PINE_SNAG_WOOD = new PillarBlock(copyWoodSettings(Blocks.STRIPPED_OAK_WOOD));
    public static final Block PINE_SNAG_BRANCH = new BranchBlock(copyWoodSettings(PINE_SNAG_WOOD));
    public static final Block PINE_SHRUB_LOG = new ShrubLogBlock(copyWoodSettings(PINE_LOG).nonOpaque());
    public static final Block FIREWEED = new TallFlowerBlock(createFlowerSettings(true));
    public static final Block TANSY = new BigFlowerBlock(StatusEffects.SLOW_FALLING, 10, createFlowerSettings(false));
    public static final Block POTTED_TANSY = new FlowerPotBlock(TANSY, createFlowerPotSettings());
    public static final Block FELL_LICHEN = new LichenBlock(createFlowerSettings(false).mapColor(MapColor.OFF_WHITE).offsetType(AbstractBlock.OffsetType.XZ));
    public static final Block POTTED_FELL_LICHEN = new FlowerPotBlock(FELL_LICHEN, createFlowerPotSettings());
    public static final Block HEATHER = new HeatherBlock(StatusEffects.REGENERATION, 8, createFlowerSettings(false));
    public static final Block POTTED_HEATHER = new FlowerPotBlock(HEATHER, createFlowerPotSettings());

    private WamBlocks() {
    }

    public static void init() {
        register("pine_log", PINE_LOG);
        register("ground_pine_log", GROUND_PINE_LOG);
        register("pine_planks", PINE_PLANKS);
        register("pine_slab", PINE_SLAB);
        register("pine_stairs", PINE_STAIRS);
        register("pine_fence", PINE_FENCE);
        register("pine_fence_gate", PINE_FENCE_GATE);
        register("pine_door", PINE_DOOR, new TallBlockItem(PINE_DOOR, new Item.Settings()));
        register("pine_button", PINE_BUTTON);
        register("pine_pressure_plate", PINE_PRESSURE_PLATE);
        register("pine_sign", PINE_SIGN, () -> new SignItem(new Item.Settings().maxCount(16), PINE_SIGN, PINE_WALL_SIGN.get()));
        register("pine_wall_sign", PINE_WALL_SIGN.get(), (Item) null);
        register("pine_leaves", PINE_LEAVES);
        register("pine_sapling", PINE_SAPLING);
        register("potted_pine_sapling", POTTED_PINE_SAPLING, (Item) null);
        register("pine_wood", PINE_WOOD);
        register("ground_pine_wood", GROUND_PINE_WOOD);
        register("stripped_pine_log", STRIPPED_PINE_LOG);
        register("stripped_pine_wood", STRIPPED_PINE_WOOD);
        register("pine_snag_log", PINE_SNAG_LOG);
        register("pine_snag_wood", PINE_SNAG_WOOD);
        register("pine_snag_branch", PINE_SNAG_BRANCH, (Item) null);
        register("pine_shrub_log", PINE_SHRUB_LOG);
        register("fireweed", FIREWEED, new TallBlockItem(FIREWEED, new Item.Settings()));
        register("tansy", TANSY);
        register("potted_tansy", POTTED_TANSY, (Item) null);
        register("fell_lichen", FELL_LICHEN);
        register("potted_fell_lichen", POTTED_FELL_LICHEN, (Item) null);
        register("heather", HEATHER);
        register("potted_heather", POTTED_HEATHER, (Item) null);

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
            PINE_DOOR,
            PINE_SAPLING,
            POTTED_PINE_SAPLING,
            FIREWEED,
            TANSY,
            POTTED_TANSY,
            FELL_LICHEN,
            POTTED_FELL_LICHEN,
            HEATHER,
            POTTED_HEATHER
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
        register(id, block, new BlockItem(block, new Item.Settings()));
    }

    private static void register(String id, Block block, @Nullable Item item) {
        Registry.register(Registries.BLOCK, WoodsAndMires.id(id), block);

        if (item != null) {
            Registry.register(Registries.ITEM, WoodsAndMires.id(id), item);
        }
    }

    private static void register(String id, Block block, Supplier<@Nullable Item> itemSupplier) {
        Registry.register(Registries.BLOCK, WoodsAndMires.id(id), block);

        @Nullable Item item = itemSupplier.get();
        if (item != null) {
            Registry.register(Registries.ITEM, WoodsAndMires.id(id), item);
        }
    }

    private static AbstractBlock.Settings copyWoodSettings(Block block) {
        return AbstractBlock.Settings.copy(block);
    }

    private static AbstractBlock.Settings createFlowerSettings(boolean tall) {
        return AbstractBlock.Settings.of(tall ? Material.REPLACEABLE_PLANT : Material.PLANT)
            .noCollision()
            .breakInstantly()
            .sounds(BlockSoundGroup.GRASS);
    }

    private static AbstractBlock.Settings createFlowerPotSettings() {
        return AbstractBlock.Settings.of(Material.DECORATION)
            .breakInstantly()
            .nonOpaque();
    }
}
