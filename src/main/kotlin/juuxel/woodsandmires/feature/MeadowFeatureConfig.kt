package juuxel.woodsandmires.feature

import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.world.gen.feature.FeatureConfig
import net.minecraft.world.gen.stateprovider.BlockStateProvider

data class MeadowFeatureConfig(
    val stateProvider: BlockStateProvider,
    val chance: Float
) : FeatureConfig {
    companion object {
        val CODEC: Codec<MeadowFeatureConfig> = RecordCodecBuilder.create { instance ->
            instance.group(
                BlockStateProvider.CODEC.fieldOf("state_provider").forGetter { it.stateProvider },
                Codec.FLOAT.fieldOf("chance").forGetter { it.chance }
            ).apply(instance, ::MeadowFeatureConfig)
        }
    }
}
