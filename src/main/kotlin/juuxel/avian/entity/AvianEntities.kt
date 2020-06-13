package juuxel.avian.entity

import juuxel.avian.Avian
import net.fabricmc.fabric.api.`object`.builder.v1.entity.FabricDefaultAttributeRegistry
import net.fabricmc.fabric.api.`object`.builder.v1.entity.FabricEntityTypeBuilder
import net.minecraft.entity.EntityType
import net.minecraft.entity.SpawnGroup
import net.minecraft.util.registry.Registry

object AvianEntities {
    val GULL: EntityType<Gull> = FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, ::Gull).build()

    fun init() {
        register("gull", GULL)

        FabricDefaultAttributeRegistry.register(GULL, Gull.createGullAttributes())
    }

    private fun register(id: String, type: EntityType<*>) {
        Registry.register(Registry.ENTITY_TYPE, Avian.id(id), type)
    }
}
