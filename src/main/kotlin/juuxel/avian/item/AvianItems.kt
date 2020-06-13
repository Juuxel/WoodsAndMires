package juuxel.avian.item

import juuxel.avian.Avian
import juuxel.avian.entity.AvianEntities
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraft.item.SpawnEggItem
import net.minecraft.util.registry.Registry

object AvianItems {
    val GULL_SPAWN_EGG = SpawnEggItem(AvianEntities.GULL, 0xE7E7E7, 0x67E3FC, Item.Settings().group(ItemGroup.MISC))

    fun init() {
        register("gull_spawn_egg", GULL_SPAWN_EGG)
    }

    private fun register(id: String, item: Item) {
        Registry.register(Registry.ITEM, Avian.id(id), item)
    }
}
