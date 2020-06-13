package juuxel.avian

import juuxel.avian.client.render.AvianRenderers
import juuxel.avian.entity.AvianEntities
import juuxel.avian.item.AvianItems
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.util.Identifier

object Avian {
    const val ID: String = "avian"

    fun id(path: String): Identifier = Identifier(ID, path)

    fun init() {
        AvianEntities.init()
        AvianItems.init()
    }

    @Environment(EnvType.CLIENT)
    fun initClient() {
        AvianRenderers.init()
    }
}
