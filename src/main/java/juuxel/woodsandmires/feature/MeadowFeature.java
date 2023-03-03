package juuxel.woodsandmires.feature;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

public class MeadowFeature extends Feature<MeadowFeatureConfig> {
    public MeadowFeature(Codec<MeadowFeatureConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean generate(FeatureContext<MeadowFeatureConfig> context) {
        var world = context.getWorld();
        var pos = context.getOrigin();
        var config = context.getConfig();
        var random = context.getRandom();

        BlockPos.Mutable mut = new BlockPos.Mutable();
        boolean generated = false;

        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                if (random.nextFloat() > config.chance) continue;

                int xo = pos.getX() + x;
                int zo = pos.getZ() + z;
                int y = world.getTopY(Heightmap.Type.MOTION_BLOCKING, xo, zo);
                mut.set(xo, y, zo);

                if (!config.allowedPlacement.test(world, mut)) continue;

                BlockState vegetation = config.stateProvider.get(random, mut);
                if (world.isAir(mut) && vegetation.canPlaceAt(world, mut)) {
                    setBlockState(world, mut, vegetation);
                    generated = true;
                }
            }
        }

        return generated;
    }
}
