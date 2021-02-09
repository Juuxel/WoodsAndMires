package juuxel.woodsandmires.feature;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;

import java.util.Random;

public class MeadowFeature extends Feature<MeadowFeatureConfig> {
    public MeadowFeature(Codec<MeadowFeatureConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean generate(StructureWorldAccess world, ChunkGenerator chunkGenerator, Random random, BlockPos pos, MeadowFeatureConfig config) {
        BlockPos.Mutable mut = new BlockPos.Mutable();
        boolean generated = false;

        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                if (random.nextFloat() > config.chance) continue;

                int xo = pos.getX() + x;
                int zo = pos.getZ() + z;
                int y = world.getTopY(Heightmap.Type.MOTION_BLOCKING, xo, zo);
                mut.set(xo, y, zo);

                BlockState vegetation = config.stateProvider.getBlockState(random, mut);
                if (world.isAir(mut) && vegetation.canPlaceAt(world, mut)) {
                    world.setBlockState(mut, vegetation, 2);
                    generated = true;
                }
            }
        }

        return generated;
    }
}
