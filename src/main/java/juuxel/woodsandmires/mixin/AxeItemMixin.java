package juuxel.woodsandmires.mixin;

import juuxel.woodsandmires.block.WamBlocks;
import net.minecraft.block.Block;
import net.minecraft.item.AxeItem;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

import java.util.HashMap;
import java.util.Map;

@Mixin(AxeItem.class)
abstract class AxeItemMixin {
    @Shadow
    @Final
    @Mutable
    protected static Map<Block, Block> STRIPPED_BLOCKS;

    static {
        STRIPPED_BLOCKS = new HashMap<>(STRIPPED_BLOCKS);
        STRIPPED_BLOCKS.put(WamBlocks.INSTANCE.getPINE_LOG(), WamBlocks.INSTANCE.getSTRIPPED_PINE_LOG());
        STRIPPED_BLOCKS.put(WamBlocks.INSTANCE.getPINE_WOOD(), WamBlocks.INSTANCE.getSTRIPPED_PINE_WOOD());
    }
}
