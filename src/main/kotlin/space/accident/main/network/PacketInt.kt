package space.accident.main.network

import hohserg.elegant.networking.api.ClientToServerPacket
import hohserg.elegant.networking.api.ElegantPacket
import hohserg.elegant.networking.api.ServerToClientPacket
import net.minecraft.client.Minecraft
import net.minecraft.entity.player.EntityPlayerMP

@ElegantPacket
class PacketInt(val values: IntArray) : ClientToServerPacket, ServerToClientPacket {

    companion object {
        fun commitServer(values: IntArray) {
            PacketInt(values).sendToServer()
        }

        fun commitToClient(player: EntityPlayerMP, values: IntArray) {
            PacketInt(values).sendToPlayer(player)
        }
    }

    override fun onReceive(player: EntityPlayerMP) {
        (player.openContainer as? IUpdateInt)?.update(values)
    }

    override fun onReceive(mc: Minecraft) {
        (mc.currentScreen as? IUpdateInt)?.update(values)
    }

    interface IUpdateInt {
        fun update(values: IntArray)
    }
}