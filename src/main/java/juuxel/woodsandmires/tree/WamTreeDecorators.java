package juuxel.woodsandmires.tree;

import juuxel.woodsandmires.WoodsAndMires;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.treedecorator.TreeDecoratorType;

public final class WamTreeDecorators {
    public static final TreeDecoratorType<BranchTreeDecorator> BRANCH = new TreeDecoratorType<>(BranchTreeDecorator.CODEC);
    public static final TreeDecoratorType<PineTrunkTreeDecorator> PINE_TRUNK = new TreeDecoratorType<>(PineTrunkTreeDecorator.CODEC);

    public static void register() {
        register("branch", BRANCH);
        register("pine_trunk", PINE_TRUNK);
    }

    private static void register(String id, TreeDecoratorType<?> type) {
        Registry.register(Registry.TREE_DECORATOR_TYPE, WoodsAndMires.id(id), type);
    }
}
