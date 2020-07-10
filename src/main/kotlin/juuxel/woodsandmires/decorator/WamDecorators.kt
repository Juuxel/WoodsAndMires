package juuxel.woodsandmires.decorator

import juuxel.woodsandmires.WoodsAndMires
import net.minecraft.util.registry.Registry
import net.minecraft.world.gen.decorator.Decorator

object WamDecorators {
    val TRANSFORMED: Decorator<TransformedDecoratorConfig> = TransformedDecorator()

    fun init() {
        register("transformed", TRANSFORMED)
    }

    private fun register(id: String, decorator: Decorator<*>) {
        Registry.register(Registry.DECORATOR, WoodsAndMires.id(id), decorator)
    }
}
