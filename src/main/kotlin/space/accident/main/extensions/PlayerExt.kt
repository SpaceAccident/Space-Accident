package space.accident.main.extensions

import net.minecraft.entity.player.EntityPlayer
import net.minecraft.world.World
import space.accident.main.SpaceAccident
import space.accident.main.client.GuiType

/**
 * Ext. Open GUI by Player
 *
 * @param guiType GUI type
 * @param world World
 * @param x, y, z block/te coordinates
 */
@JvmOverloads
fun EntityPlayer.guiOpen(guiType: GuiType, world: World?, x: Int = 0, y: Int = 0, z: Int = 0) {
    world?.let { openGui(SpaceAccident.instance(), guiType.ordinal, world, x, y, z) }
}