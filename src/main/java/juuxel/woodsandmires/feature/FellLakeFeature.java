package juuxel.woodsandmires.feature;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.world.gen.feature.LakeFeature;
import net.minecraft.world.gen.feature.SingleStateFeatureConfig;

import java.util.Random;

public class FellLakeFeature extends LakeFeature implements LakeFeatureExtensions {
    public FellLakeFeature(Codec<SingleStateFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean wam_shouldReplaceBorderingBlocks() {
        return true;
    }

    @Override
    public BlockState wam_getBorderBlock(Random random) {
        if (random.nextInt(60) == 0) {
            return Blocks.EMERALD_ORE.getDefaultState();
        } else if (random.nextInt(55) == 0) {
            return Blocks.GOLD_ORE.getDefaultState();
        }

        return Blocks.STONE.getDefaultState();
    }
}
