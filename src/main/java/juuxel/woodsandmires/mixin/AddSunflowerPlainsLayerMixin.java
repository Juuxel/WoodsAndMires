package juuxel.woodsandmires.mixin;

import juuxel.woodsandmires.biome.BiomeTransformations;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.world.biome.Biome;
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
        Biome input = BuiltinRegistries.BIOME.get(se);
        if (input == null) return;
        Biome transformed = BiomeTransformations.INSTANCE.transformMediumSubBiome(context, input);

        if (input != transformed) {
            info.setReturnValue(BuiltinRegistries.BIOME.getRawId(transformed));
        }
    }
}
