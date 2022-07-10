package space.accident.main.client

import cpw.mods.fml.common.network.IGuiHandler
import cpw.mods.fml.common.network.NetworkRegistry
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.world.World
import space.accident.main.SpaceAccident
import space.accident.main.client.GuiType.RECIPE_EDITOR
import space.accident.main.client.GuiType.RECIPE_EDITOR_MACHINE
import space.accident.main.extra.reciper.ReciperGuiSelect
import space.accident.main.extra.reciper.ReciperMachineContainer
import space.accident.main.extra.reciper.ReciperMachineGui

class GuiHandler : IGuiHandler {

    init {
        NetworkRegistry.INSTANCE.registerGuiHandler(SpaceAccident.instance(), this)
    }

    override fun getServerGuiElement(id: Int, p: EntityPlayer, w: World, x: Int, y: Int, z: Int): Any? {
        val inventoryPlayer = p.inventory
        return GuiType.getById(id)?.let { type ->
            when (type) {
                RECIPE_EDITOR -> null
                else -> null
            }
        }
    }

    override fun getClientGuiElement(id: Int, p: EntityPlayer, w: World, x: Int, y: Int, z: Int): Any? {
        val inventoryPlayer = p.inventory
        return GuiType.getById(id)?.let { type ->
            when (type) {
                RECIPE_EDITOR -> ReciperGuiSelect()
                else -> null
            }
        }
    }
}

enum class GuiType {
    NO_VALID,
    RECIPE_EDITOR,
    RECIPE_EDITOR_MACHINE;

    companion object {
        fun getById(id: Int) = values().find { id == it.ordinal && it != NO_VALID }
    }
}