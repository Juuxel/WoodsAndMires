package juuxel.avian.client.render

import juuxel.avian.entity.AvianEntities
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry

@Environment(EnvType.CLIENT)
object AvianRenderers {
    fun init() {
        EntityRendererRegistry.INSTANCE.register(AvianEntities.GULL) { dispatcher, _ -> GullRenderer(dispatcher) }
    }
}
