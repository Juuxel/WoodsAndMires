package juuxel.notavian

import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.util.Identifier

object NotAvian {
    const val ID: String = "not_avian"

    fun id(path: String): Identifier = Identifier(ID, path)

    fun init() {
    }

    @Environment(EnvType.CLIENT)
    fun initClient() {
    }
}
