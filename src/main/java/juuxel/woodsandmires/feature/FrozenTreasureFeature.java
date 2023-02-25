package juuxel.woodsandmires.feature;

import com.mojang.serialization.Codec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.entity.LootableContainerBlockEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

public final class FrozenTreasureFeature extends Feature<FrozenTreasureFeatureConfig> {
    public FrozenTreasureFeature(Codec<FrozenTreasureFeatureConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean generate(FeatureContext<FrozenTreasureFeatureConfig> context) {
        FrozenTreasureFeatureConfig config = context.getConfig();
        StructureWorldAccess world = context.getWorld();
        BlockPos origin = context.getOrigin();
        var random = context.getRandom();
        int height = config.height().get(random);
        int semiMajor = config.radius().get(random);
        int semiMinor = config.radius().get(random);
        BlockPos.Mutable mut = new BlockPos.Mutable();
        int chestY = height / 3;

        for (int x = -semiMajor; x <= semiMajor; x++) {
            for (int z = -semiMinor; z <= semiMinor; z++) {
                mut.set(origin.getX() + x, origin.getY() - 1, origin.getZ() + z);
                if (!hasSolidTop(world, mut) || !world.testFluidState(mut, FluidState::isEmpty)) {
                    return false;
                }
            }
        }

        for (int yo = -1; yo < height; yo++) {
            float width = getWidthAdjustmentFromProgress(MathHelper.getLerpProgress(yo, -1f, height - 1f));
            int sma = Math.round(semiMajor * width);
            int smi = Math.round(semiMinor * width);
            float semiMajorSq = sma * sma;
            float semiMinorSq = smi * smi;

            for (int x = -sma; x <= sma; x++) {
                for (int z = -smi; z <= smi; z++) {
                    mut.set(origin.getX() + x, origin.getY() + yo, origin.getZ() + z);
                    if (x == 0 && z == 0 && yo == chestY) {
                        BlockState chest = Blocks.CHEST.getDefaultState()
                            .with(ChestBlock.FACING, Direction.Type.HORIZONTAL.random(random));
                        setBlockState(world, mut, chest);
                        LootableContainerBlockEntity.setLootTable(world, random, mut, config.lootTable());
                    } else if (FellPondFeature.isInsideEllipse(x, z, semiMajorSq, semiMinorSq, 0)) {
                        setBlockState(world, mut, config.ice().getBlockState(random, mut));
                    }
                }
            }
        }

        return true;
    }

    private static float getWidthAdjustmentFromProgress(float progress) {
        float coefficient = progress < 0.6f ? 0.6666f : -2.5f;
        return coefficient * (progress - 0.6f) + 1f;
    }

    private static boolean hasSolidTop(StructureWorldAccess world, BlockPos pos) {
        return world.testBlockState(pos, state -> {
            VoxelShape shape = state.getCollisionShape(world, pos);
            return Block.isFaceFullSquare(shape, Direction.UP);
        });
    }
}
