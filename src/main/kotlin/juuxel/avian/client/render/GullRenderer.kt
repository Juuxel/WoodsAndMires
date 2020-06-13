package juuxel.avian.client.render

import juuxel.avian.Avian
import juuxel.avian.entity.Gull
import net.minecraft.client.render.entity.EntityRenderDispatcher
import net.minecraft.client.render.entity.MobEntityRenderer

class GullRenderer(
    dispatcher: EntityRenderDispatcher
) : MobEntityRenderer<Gull, GullModel>(dispatcher, GullModel(), 0.4f) {
    override fun getTexture(entity: Gull) = TEXTURE

    companion object {
        private val TEXTURE = Avian.id("textures/entity/gull.png")
    }
}
