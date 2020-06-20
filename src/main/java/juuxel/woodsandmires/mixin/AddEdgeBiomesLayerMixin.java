package juuxel.woodsandmires.mixin;

import juuxel.woodsandmires.biome.BiomeTransformations;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.layer.AddEdgeBiomesLayer;
import net.minecraft.world.biome.layer.util.LayerRandomnessSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AddEdgeBiomesLayer.class)
abstract class AddEdgeBiomesLayerMixin {
    @Inject(method = "sample", at = @At("RETURN"), cancellable = true)
    private void wam_onSample(LayerRandomnessSource context, int n, int e, int s, int w, int center, CallbackInfoReturnable<Integer> info) {
        int matching = 0;
        if (center == n) matching++;
        if (center == e) matching++;
        if (center == s) matching++;
        if (center == w) matching++;

        if (matching >= 3) {
            Biome input = Registry.BIOME.get(center);
            if (input == null) return;
            Biome transformed = BiomeTransformations.INSTANCE.transformSmallSubBiome(context, input);

            if (input != transformed) {
                info.setReturnValue(Registry.BIOME.getRawId(transformed));
            }
        }
    }
}
