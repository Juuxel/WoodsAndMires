package juuxel.woodsandmires.feature

import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.world.gen.feature.FeatureConfig
import net.minecraft.world.gen.stateprovider.BlockStateProvider

data class MireVegetationFeatureConfig(
    val stateProvider: BlockStateProvider
) : FeatureConfig {
    companion object {
        val CODEC: Codec<MireVegetationFeatureConfig> = RecordCodecBuilder.create { instance ->
            instance.group(BlockStateProvider.CODEC.fieldOf("state_provider").forGetter { it.stateProvider })
                .apply(instance, ::MireVegetationFeatureConfig)
        }
    }
}
