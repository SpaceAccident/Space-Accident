package space.accident.main.network

import hohserg.elegant.networking.api.ClientToServerPacket
import hohserg.elegant.networking.api.ElegantPacket
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.item.ItemStack

@ElegantPacket
class PacketUpdateItemStack(val stack: ItemStack, val slotId: Int) : ClientToServerPacket {

    companion object {
        fun commit(stack: ItemStack, slotId: Int) {
            PacketUpdateItemStack(stack, slotId).sendToServer()
        }
    }

    override fun onReceive(player: EntityPlayerMP) {
        val container = player.openContainer
        if (container is IUpdateItemStack) {
            container.updateItemStack(stack, slotId)
        }
    }

    interface IUpdateItemStack {
        fun updateItemStack(stack: ItemStack, slotId: Int)
    }
}