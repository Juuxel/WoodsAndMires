package juuxel.woodsandmires.mixin;

import juuxel.woodsandmires.feature.LakeFeatureExtensions;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.LakeFeature;
import net.minecraft.world.gen.feature.SingleStateFeatureConfig;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(LakeFeature.class)
abstract class LakeFeatureMixin implements LakeFeatureExtensions {
    @Unique
    private Random random;

    @Inject(method = "generate", at = @At("HEAD"))
    private void wam_onGenerate_head(StructureWorldAccess world, ChunkGenerator chunkGenerator, Random random, BlockPos pos, SingleStateFeatureConfig config, CallbackInfoReturnable<Boolean> info) {
        this.random = random;
    }

    @Inject(method = "generate", at = @At("RETURN"))
    private void wam_onGenerate_return(StructureWorldAccess world, ChunkGenerator chunkGenerator, Random random, BlockPos pos, SingleStateFeatureConfig config, CallbackInfoReturnable<Boolean> info) {
        this.random = null;
    }

    @Redirect(
        method = "generate",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockState;getMaterial()Lnet/minecraft/block/Material;", ordinal = 0),
        slice = @Slice(from = @At(value = "FIELD", target = "Lnet/minecraft/block/Material;LAVA:Lnet/minecraft/block/Material;", shift = At.Shift.BEFORE))
    )
    private Material wam_replaceBlockMaterial(BlockState state) {
        return wam_shouldReplaceBorderingBlocks() ? Material.LAVA : state.getMaterial();
    }

    @ModifyArg(
        method = "generate",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/world/StructureWorldAccess;setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;I)Z", ordinal = 0),
        slice = @Slice(from = @At(value = "FIELD", target = "Lnet/minecraft/block/Material;LAVA:Lnet/minecraft/block/Material;"))
    )
    private BlockState wam_modifyBorderBlockState(BlockState state) {
        @Nullable BlockState replacement = wam_getBorderBlock(random);
        return replacement != null ? replacement : state;
    }

    @Override
    public boolean wam_shouldReplaceBorderingBlocks() {
        return false;
    }

    @Nullable
    @Override
    public BlockState wam_getBorderBlock(@NotNull Random random) {
        return null;
    }
}
