package juuxel.woodsandmires.decorator

import com.google.common.collect.ImmutableList
import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.class_5444
import net.minecraft.util.math.BlockPos
import net.minecraft.world.gen.decorator.ConfiguredDecorator
import net.minecraft.world.gen.decorator.Decorator
import net.minecraft.world.gen.decorator.DecoratorConfig
import java.util.Random
import java.util.stream.Stream

class TransformedDecorator : Decorator<TransformedDecoratorConfig>(TransformedDecoratorConfig.CODEC) {
    override fun getPositions(
        context: class_5444,
        random: Random,
        config: TransformedDecoratorConfig,
        pos: BlockPos
    ): Stream<BlockPos> = config.transformers.fold(
        config.base.method_30444(context, random, pos)
    ) { positions, transformer -> transformer.transform(context, random, positions) }
}

data class TransformedDecoratorConfig(
    val base: ConfiguredDecorator<*>,
    val transformers: List<DecoratorTransformer>
) : DecoratorConfig {
    companion object {
        val CODEC: Codec<TransformedDecoratorConfig> = RecordCodecBuilder.create { instance ->
            instance.group(
                ConfiguredDecorator.field_24981.fieldOf("base").forGetter { it.base },
                DecoratorTransformer.CODEC.listOf().fieldOf("transformers").forGetter { it.transformers }
            ).apply(instance, ::TransformedDecoratorConfig)
        }
    }
}

fun ConfiguredDecorator<*>.transform(vararg transformers: DecoratorTransformer): ConfiguredDecorator<*> =
    WamDecorators.TRANSFORMED.configure(TransformedDecoratorConfig(this, ImmutableList.copyOf(transformers)))
