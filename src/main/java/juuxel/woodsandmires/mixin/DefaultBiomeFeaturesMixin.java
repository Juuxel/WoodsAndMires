package juuxel.woodsandmires.mixin;

import juuxel.woodsandmires.feature.WamFeatures;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DefaultBiomeFeatures.class)
abstract class DefaultBiomeFeaturesMixin {
    @Inject(method = "addPlainsFeatures", at = @At("RETURN"))
    private static void wam_onAddPlainsFeatures(GenerationSettings.Builder builder, CallbackInfo info) {
        WamFeatures.INSTANCE.addFlowers(builder);
    }
}
