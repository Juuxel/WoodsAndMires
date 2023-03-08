package juuxel.woodsandmires.tree;

import juuxel.woodsandmires.WoodsAndMires;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.treedecorator.TreeDecoratorType;

public final class WamTreeDecorators {
    public static final TreeDecoratorType<BranchTreeDecorator> BRANCH = new TreeDecoratorType<>(BranchTreeDecorator.CODEC);
    public static final TreeDecoratorType<AgedTrunkTreeDecorator> AGED_TRUNK = new TreeDecoratorType<>(AgedTrunkTreeDecorator.CODEC);
    public static final TreeDecoratorType<ChanceTreeDecorator> CHANCE = new TreeDecoratorType<>(ChanceTreeDecorator.CODEC);
    public static final TreeDecoratorType<PoolTreeDecorator> POOL = new TreeDecoratorType<>(PoolTreeDecorator.CODEC);
    public static final TreeDecoratorType<ReplaceTrunkTreeDecorator> REPLACE_TRUNK = new TreeDecoratorType<>(ReplaceTrunkTreeDecorator.CODEC);

    public static void register() {
        register("branch", BRANCH);
        register("aged_trunk", AGED_TRUNK);
        register("chance", CHANCE);
        register("pool", POOL);
        register("replace_trunk", REPLACE_TRUNK);
    }

    private static void register(String id, TreeDecoratorType<?> type) {
        Registry.register(Registry.TREE_DECORATOR_TYPE, WoodsAndMires.id(id), type);
    }
}
