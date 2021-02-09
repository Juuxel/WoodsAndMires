package juuxel.woodsandmires.feature;

import net.minecraft.block.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public interface LakeFeatureExtensions {
    boolean wam_shouldReplaceBorderingBlocks();
    @Nullable BlockState wam_getBorderBlock(Random random);
}
