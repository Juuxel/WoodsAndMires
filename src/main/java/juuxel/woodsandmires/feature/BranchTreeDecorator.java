package juuxel.woodsandmires.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import juuxel.woodsandmires.block.BranchBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.gen.tree.TreeDecorator;
import net.minecraft.world.gen.tree.TreeDecoratorType;

import java.util.List;
import java.util.Random;
import java.util.Set;

public final class BranchTreeDecorator extends TreeDecorator {
    public static final Codec<BranchTreeDecorator> CODEC = RecordCodecBuilder.create(
        instance -> instance.group(
            Registry.BLOCK.fieldOf("block").forGetter(BranchTreeDecorator::getBlock),
            Codec.FLOAT.fieldOf("chance").forGetter(BranchTreeDecorator::getChance)
        ).apply(instance, BranchTreeDecorator::new)
    );

    private final Block block;
    private final float chance;

    public BranchTreeDecorator(Block block, float chance) {
        this.block = block;
        this.chance = chance;
    }

    @Override
    protected TreeDecoratorType<?> getType() {
        return WamFeatures.BRANCH_TREE_DECORATOR;
    }

    public Block getBlock() {
        return block;
    }

    public float getChance() {
        return chance;
    }

    @Override
    public void generate(StructureWorldAccess world, Random random, List<BlockPos> logPositions, List<BlockPos> leavesPositions, Set<BlockPos> positions, BlockBox box) {
        BlockPos.Mutable mut = new BlockPos.Mutable();

        for (BlockPos pos : logPositions) {
            mut.set(pos);

            for (Direction side : Direction.Type.HORIZONTAL) {
                if (random.nextFloat() < chance) {
                    BlockState state = block.getDefaultState()
                        .with(BranchBlock.AXIS, side.getAxis())
                        .with(BranchBlock.STYLE, random.nextBoolean() ? BranchBlock.Style.THICK : BranchBlock.Style.THIN);

                    mut.move(side);
                    setIfAir(world, mut, state, positions, box);
                    mut.move(side.getOpposite());
                }
            }
        }
    }

    private void setIfAir(WorldAccess world, BlockPos pos, BlockState state, Set<BlockPos> positions, BlockBox box) {
        if (world.isAir(pos)) {
            setBlockStateAndEncompassPosition(world, pos, state, positions, box);
        }
    }
}
