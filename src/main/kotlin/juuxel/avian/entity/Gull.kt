package juuxel.avian.entity

import net.minecraft.entity.EntityType
import net.minecraft.entity.attribute.DefaultAttributeContainer
import net.minecraft.entity.attribute.EntityAttributes
import net.minecraft.entity.passive.AnimalEntity
import net.minecraft.entity.passive.PassiveEntity
import net.minecraft.world.World

class Gull(type: EntityType<out Gull>, world: World) : AnimalEntity(type, world) {
    override fun createChild(mate: PassiveEntity): PassiveEntity? =
        AvianEntities.GULL.create(world)

    companion object {
        fun createGullAttributes(): DefaultAttributeContainer.Builder =
            createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 8.0)
    }
}
