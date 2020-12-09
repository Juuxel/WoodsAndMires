package juuxel.woodsandmires.feature

import net.minecraft.block.BlockState
import java.util.Random

@Suppress("FunctionName")
interface LakeFeatureExtensions {
    fun wam_shouldReplaceBorderingBlocks(): Boolean
    fun wam_getBorderBlock(random: Random): BlockState?
}
