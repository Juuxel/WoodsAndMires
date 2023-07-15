package juuxel.woodsandmires.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.HangingSignBlockEntity;
import net.minecraft.util.math.BlockPos;

public class WamHangingSignBlockEntity extends HangingSignBlockEntity {
    public WamHangingSignBlockEntity(BlockPos pos, BlockState state) {
        super(pos, state);
    }

    @Override
    public BlockEntityType<?> getType() {
        return WamBlockEntities.HANGING_SIGN;
    }
}
