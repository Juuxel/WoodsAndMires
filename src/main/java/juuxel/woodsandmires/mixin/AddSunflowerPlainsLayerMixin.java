package juuxel.woodsandmires.mixin;

import juuxel.woodsandmires.biome.WamBiomes;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.layer.AddSunflowerPlainsLayer;
import net.minecraft.world.biome.layer.util.LayerRandomnessSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AddSunflowerPlainsLayer.class)
abstract class AddSunflowerPlainsLayerMixin {
    @Unique
    private static final int PINE_FOREST_ID = Registry.BIOME.getRawId(WamBiomes.PINE_FOREST);

    @Unique
    private static final int PINE_FOREST_CLEARING_ID = Registry.BIOME.getRawId(WamBiomes.PINE_FOREST_CLEARING);

    @Inject(method = "sample", at = @At("RETURN"), cancellable = true)
    private void wam_onSample(LayerRandomnessSource context, int se, CallbackInfoReturnable<Integer> info) {
        if (se == PINE_FOREST_ID && context.nextInt(15) == 0) {
            info.setReturnValue(PINE_FOREST_CLEARING_ID);
        }
    }
}
