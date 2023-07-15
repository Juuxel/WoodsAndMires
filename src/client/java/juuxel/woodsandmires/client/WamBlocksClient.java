package juuxel.woodsandmires.client;

import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.particle.v1.ParticleRenderEvents;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.client.color.world.FoliageColors;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.item.BlockItem;

import static juuxel.woodsandmires.block.WamBlocks.*;

public final class WamBlocksClient {
    public static void init() {
        BlockRenderLayerMap.INSTANCE.putBlocks(
            RenderLayer.getCutoutMipped(),
            PINE_LEAVES,
            PINE_SHRUB_LOG
        );

        BlockRenderLayerMap.INSTANCE.putBlocks(
            RenderLayer.getCutout(),
            PINE_DOOR,
            PINE_TRAPDOOR,
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

        ParticleRenderEvents.ALLOW_BLOCK_DUST_TINT.register((state, world, pos) -> {
            // Prevent tinting shrub log particles.
            // See https://github.com/Juuxel/WoodsAndMires/issues/5
            return !state.isOf(PINE_SHRUB_LOG);
        });
    }
}
