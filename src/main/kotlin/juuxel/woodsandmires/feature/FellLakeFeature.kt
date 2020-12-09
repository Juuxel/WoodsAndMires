package juuxel.woodsandmires.feature

import com.mojang.serialization.Codec
import net.minecraft.block.Blocks
import net.minecraft.world.gen.feature.LakeFeature
import net.minecraft.world.gen.feature.SingleStateFeatureConfig
import java.util.Random

class FellLakeFeature(
    configCodec: Codec<SingleStateFeatureConfig>
) : LakeFeature(configCodec), LakeFeatureExtensions {
    override fun wam_shouldReplaceBorderingBlocks() = true

    override fun wam_getBorderBlock(random: Random) =
        when {
            random.nextInt(60) == 0 -> Blocks.EMERALD_ORE.defaultState
            random.nextInt(55) == 0 -> Blocks.GOLD_ORE.defaultState
            else -> Blocks.STONE.defaultState
        }
}
