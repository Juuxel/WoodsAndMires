package juuxel.woodsandmires.feature;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

import java.util.HashSet;
import java.util.Set;

public final class FellPondFeature extends Feature<FellPondFeatureConfig> {
    private static final int MAX_HEIGHT_TO_DIG = 2;

    public FellPondFeature(Codec<FellPondFeatureConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean generate(FeatureContext<FellPondFeatureConfig> context) {
        FellPondFeatureConfig config = context.getConfig();
        BlockPos.Mutable origin = context.getOrigin().mutableCopy().move(0, -1, 0);

        // Don't generate in water.
        if (!context.getWorld().getFluidState(origin).isEmpty()) {
            return false;
        }

        var random = context.getRandom();
        int depth = config.depth().get(random);
        int semiMajor = config.radius().get(random);
        int semiMinor = config.radius().get(random);
        BlockPos.Mutable mut = new BlockPos.Mutable();
        Set<BlockPos> filledPositions = new HashSet<>();
        float theta = random.nextFloat() * MathHelper.TAU;

        int dugHeight = 0;
        // Check for air layers
        boolean foundAir;
        do {
            foundAir = false;
            float semiMajorSq = semiMajor * semiMajor;
            float semiMinorSq = semiMinor * semiMinor;

            outer: for (int x = -semiMajor; x <= semiMajor; x++) {
                for (int z = -semiMinor; z <= semiMinor; z++) {
                    if (isInsideEllipse(x, z, semiMajorSq, semiMinorSq, theta) && isAir(context.getWorld(), mut.set(origin).move(x, 0, z))) {
                        foundAir = true;
                        break outer;
                    }
                }
            }

            if (foundAir) {
                for (int x = -semiMajor; x <= semiMajor; x++) {
                    for (int z = -semiMinor; z <= semiMinor; z++) {
                        if (isInsideEllipse(x, z, semiMajorSq, semiMinorSq, theta)) {
                            setBlockState(context.getWorld(), mut.set(origin).move(x, 0, z), Blocks.AIR.getDefaultState());
                        }
                    }
                }

                origin.move(0, -1, 0);
                dugHeight++;

                if (dugHeight >= MAX_HEIGHT_TO_DIG) {
                    break;
                }
            }
        } while (foundAir);

        for (int yo = 0; yo < depth; yo++) {
            filledPositions.clear();
            float semiMajorSq = semiMajor * semiMajor;
            float semiMinorSq = semiMinor * semiMinor;

            for (int x = -semiMajor; x <= semiMajor; x++) {
                for (int z = -semiMinor; z <= semiMinor; z++) {
                    if (isInsideEllipse(x, z, semiMajorSq, semiMinorSq, theta)) {
                        mut.set(origin.getX() + x, origin.getY() - yo, origin.getZ() + z);
                        setBlockState(context.getWorld(), mut, config.fillBlock().getBlockState(random, mut));
                        filledPositions.add(new BlockPos(mut));

                        for (Direction d : Direction.Type.HORIZONTAL) {
                            mut.move(d);

                            if (!filledPositions.contains(mut) && shouldPlaceBorder(context.getWorld(), mut)) {
                                setBlockState(context.getWorld(), mut, config.border().getBlockState(random, mut));
                            }

                            mut.move(d.getOpposite());
                        }

                        if (random.nextFloat() < config.bottomReplaceChance()) {
                            mut.move(0, -1, 0);
                            setBlockState(context.getWorld(), mut, config.bottomBlock().getBlockState(random, mut));
                        }
                    }
                }
            }

            semiMajor--;
            semiMinor--;
        }

        return true;
    }

    private static boolean isInsideEllipse(int x, int y, float semiMajorSq, float semiMinorSq, float theta) {
        float sin = MathHelper.sin(theta);
        float cos = MathHelper.cos(theta);
        float sinSq = sin * sin;
        float cosSq = cos * cos;

        // https://en.wikipedia.org/wiki/Ellipse#General_ellipse
        // Ax^2 + Bxy + Cy^2 + Dx + Ey + F = 0
        // Since we're centred at the origin, D and E are 0.
        float a = semiMajorSq * sinSq + semiMinorSq * cosSq;
        float b = 2 * (semiMinorSq - semiMajorSq) * sin * cos;
        float c = semiMajorSq * cosSq + semiMinorSq * sinSq;
        float f = -semiMajorSq * semiMinorSq;

        return (a * x * x) + (b * x * y) + (c * y * y) + f <= 0;
    }

    private static boolean shouldPlaceBorder(StructureWorldAccess world, BlockPos pos) {
        BlockState state = world.getBlockState(pos);
        return state.isAir() || !state.isFullCube(world, pos);
    }
}
