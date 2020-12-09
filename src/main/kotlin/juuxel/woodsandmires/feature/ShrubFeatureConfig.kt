package juuxel.woodsandmires.feature

import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.block.BlockState
import net.minecraft.world.gen.feature.FeatureConfig

data class ShrubFeatureConfig(
    val log: BlockState,
    val leaves: BlockState,
    val baseHeight: Int,
    val extraHeight: Int,
    val extraHeightChance: Float
) : FeatureConfig {
    companion object {
        val CODEC: Codec<ShrubFeatureConfig> = RecordCodecBuilder.create { instance ->
            instance.group(
                BlockState.CODEC.fieldOf("log").forGetter { it.log },
                BlockState.CODEC.fieldOf("leaves").forGetter { it.leaves },
                Codec.INT.fieldOf("base_height").forGetter { it.baseHeight },
                Codec.INT.fieldOf("extra_height").forGetter { it.extraHeight },
                Codec.FLOAT.fieldOf("extra_height_chance").forGetter { it.extraHeightChance }
            ).apply(instance, ::ShrubFeatureConfig)
        }
    }
}
