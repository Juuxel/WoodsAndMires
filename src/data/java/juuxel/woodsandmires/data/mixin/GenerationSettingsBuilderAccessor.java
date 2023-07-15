package juuxel.woodsandmires.data.mixin;

import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.gen.feature.PlacedFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(GenerationSettings.Builder.class)
public interface GenerationSettingsBuilderAccessor {
    @Accessor
    List<List<RegistryEntry<PlacedFeature>>> getIndexedFeaturesList();
}
