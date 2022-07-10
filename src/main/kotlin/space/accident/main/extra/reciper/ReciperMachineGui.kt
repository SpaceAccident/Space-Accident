package space.accident.main.extra.reciper

import codechicken.nei.VisiblityData
import codechicken.nei.api.INEIGuiHandler
import codechicken.nei.api.TaggedInventoryArea
import cpw.mods.fml.common.Optional
import gregtech.api.gui.GT_GUIContainerMetaTile_Machine
import gregtech.api.interfaces.tileentity.IGregTechTileEntity
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiButton
import net.minecraft.client.gui.inventory.GuiContainer
import net.minecraft.entity.player.InventoryPlayer
import net.minecraft.inventory.Slot
import net.minecraft.item.ItemStack
import space.accident.main.client.GuiType
import space.accident.main.extensions.guiOpen

@Optional.Interface(iface = "codechicken.nei.api.INEIGuiHandler", modid = "NotEnoughItems")
class ReciperMachineGui(
    playerInv: InventoryPlayer,
    gte: IGregTechTileEntity,
    gui: String = ReciperMachine.SELECT_MAP.mNEIGUIPath
) : GT_GUIContainerMetaTile_Machine(ReciperMachineContainer(playerInv, gte), gui), INEIGuiHandler {

    override fun initGui() {
        super.initGui()
        buttonList.add(GuiButton(0, guiLeft, guiTop - 20, xSize, 20, "Change Map"))
    }

    override fun actionPerformed(btn: GuiButton) {
        super.actionPerformed(btn)
        if (btn.id == 0) {
            Minecraft.getMinecraft().thePlayer?.let {
                it.guiOpen(GuiType.RECIPE_EDITOR, it.worldObj)
            }
        }
    }

    override fun drawGuiContainerBackgroundLayer(par1: Float, par2: Int, par3: Int) {
        super.drawGuiContainerBackgroundLayer(par1, par2, par3)
        val x = (width - xSize) / 2
        val y = (height - ySize) / 2
        drawTexturedModalRect(x, y, 0, 0, xSize, ySize)
    }

    @Optional.Method(modid = "NotEnoughItems")
    override fun modifyVisiblity(gui: GuiContainer, currentVisibility: VisiblityData): VisiblityData {
        return currentVisibility
    }

    @Optional.Method(modid = "NotEnoughItems")
    override fun getItemSpawnSlots(gui: GuiContainer, item: ItemStack): MutableIterable<Int> {
        return mutableListOf()
    }

    @Optional.Method(modid = "NotEnoughItems")
    override fun getInventoryAreas(gui: GuiContainer): MutableList<TaggedInventoryArea> {
        return mutableListOf()
    }

    @Optional.Method(modid = "NotEnoughItems")
    override fun handleDragNDrop(
        gui: GuiContainer,
        mousex: Int,
        mousey: Int,
        draggedStack: ItemStack,
        button: Int
    ): Boolean {
        if (gui is ReciperMachineGui) {
            inventorySlots.inventorySlots.map {
                (it as Slot)
            }.find { slot ->
                slot.xDisplayPosition in mousex..(mousex + 16) && slot.yDisplayPosition in mousey..(mousey + 16)
            }?.putStack(draggedStack)
            //TODO add Packet to set ItemStack on server side
            return true
        }
        return false
    }

    @Optional.Method(modid = "NotEnoughItems")
    override fun hideItemPanelSlot(gui: GuiContainer, x: Int, y: Int, w: Int, h: Int): Boolean {
        return false
    }
}