package juuxel.avian.client.render

import juuxel.avian.entity.Gull
import net.minecraft.client.model.ModelPart
import net.minecraft.client.render.entity.model.AnimalModel

class GullModel : AnimalModel<Gull>() {
    private val head: ModelPart = ModelPart(this, 0, 0)
    private val beak: ModelPart = ModelPart(this)
    private val torso: ModelPart = ModelPart(this)
    private val leftWing: ModelPart = ModelPart(this)
    private val rightWing: ModelPart = ModelPart(this)
    private val leftLeg: ModelPart = ModelPart(this)
    private val rightLeg: ModelPart = ModelPart(this)

    init {
        head.addCuboid(-2.5f, -2f, -2f, 5f, 4f, 4f)
    }

    override fun setAngles(
        entity: Gull,
        limbAngle: Float,
        limbDistance: Float,
        animationProgress: Float,
        headYaw: Float,
        headPitch: Float
    ) {
        head.yaw = headYaw
        head.pitch = headPitch
        beak.yaw = headYaw
        beak.pitch = headPitch
        // TODO
    }

    override fun getHeadParts() = listOf(head, beak)

    override fun getBodyParts() = listOf(torso, leftWing, rightWing, leftLeg, rightLeg)
}
