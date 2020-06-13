package juuxel.woodsandmires

import juuxel.woodsandmires.block.WamBlocks
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.util.Identifier

object WoodsAndMires {
    const val ID: String = "woods_and_mires"

    fun id(path: String): Identifier = Identifier(ID, path)

    fun init() {
        WamBlocks.init()
    }

    @Environment(EnvType.CLIENT)
    fun initClient() {
        WamBlocks.initClient()
    }
}
