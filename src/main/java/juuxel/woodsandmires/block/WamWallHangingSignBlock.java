package juuxel.woodsandmires.block;

import juuxel.woodsandmires.block.entity.WamHangingSignBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.WallHangingSignBlock;
import net.minecraft.block.WoodType;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;

public class WamWallHangingSignBlock extends WallHangingSignBlock {
    public WamWallHangingSignBlock(Settings settings, WoodType woodType) {
        super(settings, woodType);
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new WamHangingSignBlockEntity(pos, state);
    }
}
