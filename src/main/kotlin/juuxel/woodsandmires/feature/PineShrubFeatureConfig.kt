package juuxel.woodsandmires.feature

import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.world.gen.feature.FeatureConfig

data class PineShrubFeatureConfig(
    val baseHeight: Int,
    val extraHeight: Int,
    val extraHeightChance: Float
) : FeatureConfig {
    companion object {
        val CODEC: Codec<PineShrubFeatureConfig> = RecordCodecBuilder.create { instance ->
            instance.group(
                Codec.INT.fieldOf("base_height").forGetter { it.baseHeight },
                Codec.INT.fieldOf("extra_height").forGetter { it.extraHeight },
                Codec.FLOAT.fieldOf("extra_height_chance").forGetter { it.extraHeightChance }
            ).apply(instance, ::PineShrubFeatureConfig)
        }
    }
}
