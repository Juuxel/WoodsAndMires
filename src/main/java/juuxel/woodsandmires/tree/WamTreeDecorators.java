package juuxel.woodsandmires.tree;

import juuxel.woodsandmires.WoodsAndMires;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.world.gen.treedecorator.TreeDecoratorType;

public final class WamTreeDecorators {
    public static final TreeDecoratorType<BranchTreeDecorator> BRANCH = new TreeDecoratorType<>(BranchTreeDecorator.CODEC);
    public static final TreeDecoratorType<AgedTrunkTreeDecorator> AGED_TRUNK = new TreeDecoratorType<>(AgedTrunkTreeDecorator.CODEC);

    public static void register() {
        register("branch", BRANCH);
        register("aged_trunk", AGED_TRUNK);
    }

    private static void register(String id, TreeDecoratorType<?> type) {
        Registry.register(Registries.TREE_DECORATOR_TYPE, WoodsAndMires.id(id), type);
    }
}
