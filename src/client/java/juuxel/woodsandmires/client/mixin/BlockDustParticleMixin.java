package juuxel.woodsandmires.client.mixin;

import juuxel.woodsandmires.block.WamBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.particle.BlockDustParticle;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Slice;

// Prevents Minecraft from colouring pine shrub log particles with the biome colour.
// See https://github.com/Juuxel/WoodsAndMires/issues/5
@Mixin(BlockDustParticle.class)
abstract class BlockDustParticleMixin {
    @ModifyVariable(
        method = "<init>(Lnet/minecraft/client/world/ClientWorld;DDDDDDLnet/minecraft/block/BlockState;Lnet/minecraft/util/math/BlockPos;)V",
        at = @At("LOAD"),
        argsOnly = true,
        slice = @Slice(
            from = @At(value = "FIELD", target = "Lnet/minecraft/client/particle/BlockDustParticle;blue:F", ordinal = 0),
            to = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockState;isOf(Lnet/minecraft/block/Block;)Z")
        )
    )
    private BlockState changeBlock(BlockState state) {
        if (state.isOf(WamBlocks.PINE_SHRUB_LOG)) {
            return Blocks.GRASS_BLOCK.getDefaultState(); // hardcoded not to get coloured
        }

        return state;
    }
}
