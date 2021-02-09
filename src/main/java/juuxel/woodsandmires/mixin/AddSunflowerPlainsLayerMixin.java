package juuxel.woodsandmires.mixin;

import juuxel.woodsandmires.biome.BiomeTransformations;
import net.minecraft.world.biome.layer.AddSunflowerPlainsLayer;
import net.minecraft.world.biome.layer.util.LayerRandomnessSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AddSunflowerPlainsLayer.class)
abstract class AddSunflowerPlainsLayerMixin {
    @Inject(method = "sample", at = @At("RETURN"), cancellable = true)
    private void wam_onSample(LayerRandomnessSource context, int se, CallbackInfoReturnable<Integer> info) {
        int transformed = BiomeTransformations.transformMediumSubBiome(context, se);

        if (se != transformed) {
            info.setReturnValue(transformed);
        }
    }
}
