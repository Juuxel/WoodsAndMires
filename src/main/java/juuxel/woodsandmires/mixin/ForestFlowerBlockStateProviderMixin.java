package juuxel.woodsandmires.mixin;

import juuxel.woodsandmires.block.WamBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.world.gen.stateprovider.ForestFlowerBlockStateProvider;
import org.apache.commons.lang3.ArrayUtils;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ForestFlowerBlockStateProvider.class)
abstract class ForestFlowerBlockStateProviderMixin {
    @Shadow
    @Final
    @Mutable
    private static BlockState[] FLOWERS;

    static {
        FLOWERS = ArrayUtils.add(FLOWERS, WamBlocks.TANSY.getDefaultState());
    }
}
