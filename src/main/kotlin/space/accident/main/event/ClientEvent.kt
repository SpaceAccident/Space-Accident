package space.accident.main.event

import cpw.mods.fml.common.FMLCommonHandler
import cpw.mods.fml.common.eventhandler.SubscribeEvent
import cpw.mods.fml.common.gameevent.InputEvent.KeyInputEvent
import cpw.mods.fml.relauncher.Side
import cpw.mods.fml.relauncher.SideOnly
import net.minecraft.client.Minecraft
import net.minecraftforge.common.MinecraftForge
import space.accident.main.client.GuiType
import space.accident.main.client.KeyBindings
import space.accident.main.extensions.guiOpen
import space.accident.main.network.PacketOpenRecipeEditor

class ClientEvent {

    init {
        FMLCommonHandler.instance().bus().register(this)
        MinecraftForge.EVENT_BUS.register(this)
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    fun onKeyInput(event: KeyInputEvent) {
        if (KeyBindings.openRecipeEditor.isPressed) {
            Minecraft.getMinecraft().thePlayer?.let {
                it.guiOpen(GuiType.RECIPE_EDITOR, it.worldObj)
            }
        }
    }
}