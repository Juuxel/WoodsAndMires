package juuxel.woodsandmires.mixin;

import com.mojang.serialization.Codec;
import net.minecraft.world.gen.tree.TreeDecorator;
import net.minecraft.world.gen.tree.TreeDecoratorType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(TreeDecoratorType.class)
public interface TreeDecoratorTypeAccessor {
    @Invoker("<init>")
    static <P extends TreeDecorator> TreeDecoratorType<P> construct(Codec<P> codec) {
        throw new AssertionError("Invoker dummy body");
    }
}
