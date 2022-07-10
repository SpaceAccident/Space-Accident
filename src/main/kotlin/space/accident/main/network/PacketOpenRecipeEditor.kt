package space.accident.main.network

import gregtech.api.util.GT_Recipe
import hohserg.elegant.networking.api.ClientToServerPacket
import hohserg.elegant.networking.api.ElegantPacket
import net.minecraft.entity.player.EntityPlayerMP
import space.accident.main.client.GuiType
import space.accident.main.extensions.guiOpen
import space.accident.main.extra.reciper.ReciperMachine
import space.accident.main.extra.reciper.ReciperMachineContainer

@ElegantPacket
class PacketOpenRecipeEditor(val mapName: String) : ClientToServerPacket {

    companion object {
        fun commit(map: String) {
            PacketOpenRecipeEditor(map).sendToServer()
        }
    }

    override fun onReceive(player: EntityPlayerMP) {
        ReciperMachine.SELECT_MAP = GT_Recipe.GT_Recipe_Map.sMappings.first { it.mNEIName == mapName }
        print(ReciperMachine.SELECT_MAP.mNEIName)
    }
}

