package juuxel.woodsandmires

import juuxel.woodsandmires.biome.WamBiomes
import juuxel.woodsandmires.block.WamBlocks
import juuxel.woodsandmires.decorator.DecoratorTransformer
import juuxel.woodsandmires.decorator.WamDecorators
import juuxel.woodsandmires.feature.WamConfiguredFeatures
import juuxel.woodsandmires.feature.WamFeatures
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.util.Identifier

object WoodsAndMires {
    const val ID: String = "woods_and_mires"

    fun id(path: String): Identifier = Identifier(ID, path)

    fun init() {
        WamBlocks.init()
        DecoratorTransformer.init()
        WamDecorators.init()
        WamFeatures.init()
        WamConfiguredFeatures.init()
        WamBiomes.init()
    }

    @Environment(EnvType.CLIENT)
    fun initClient() {
        WamBlocks.initClient()
    }
}
