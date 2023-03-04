package juuxel.woodsandmires.block;

import juuxel.woodsandmires.block.entity.WamSignBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.WallSignBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.SignType;
import net.minecraft.util.math.BlockPos;

public class WamWallSignBlock extends WallSignBlock {
    public WamWallSignBlock(Settings settings, SignType signType) {
        super(settings, signType);
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new WamSignBlockEntity(pos, state);
    }
}
