package space.accident.main.extra.reciper

import codechicken.nei.recipe.GuiCraftingRecipe
import gregtech.api.enums.GT_Values.RES_PATH_GUI
import gregtech.api.interfaces.tileentity.IGregTechTileEntity
import gregtech.api.util.GT_LanguageManager
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiButton
import net.minecraft.client.gui.GuiTextField
import net.minecraft.entity.player.InventoryPlayer
import net.minecraft.inventory.Slot
import space.accident.main.client.GuiType
import space.accident.main.client.widgets.GtGuiExtended
import space.accident.main.client.widgets.GuiIntegerBox
import space.accident.main.extensions.guiOpen
import space.accident.main.extensions.setText
import space.accident.main.network.PacketInt
import space.accident.main.network.PacketInt.IUpdateInt
import java.awt.Color

class ReciperMachineGui(
    playerInv: InventoryPlayer,
    gte: IGregTechTileEntity,
    gui: String = RES_PATH_GUI + "RecipeEditor.png"
) : GtGuiExtended(ReciperMachineContainer(playerInv, gte), gui), IUpdateInt {

    init {
        ySize = 251
        xSize = 200
    }

    var vol: GuiIntegerBox? = null
    var time: GuiIntegerBox? = null
    var special: GuiIntegerBox? = null

    private var boxes = mutableListOf<GuiTextField>()

    override fun initGui() {
        super.initGui()



        buttonList.add(
            GuiButton(
                0, guiLeft, guiTop - 20, xSize, 20,
                GT_LanguageManager.getTranslation(ReciperMachine.SELECT_MAP.mUnlocalizedName)
            )
        )

        boxes.add(GuiIntegerBox(fontRendererObj, guiLeft + 55, guiTop + 120, 115, 10).also { vol = it })
        boxes.add(GuiIntegerBox(fontRendererObj, guiLeft + 55, guiTop + 135, 115, 10).also { time = it })
        boxes.add(GuiIntegerBox(fontRendererObj, guiLeft + 55, guiTop + 150, 115, 10).also { special = it })
        vol?.text = "0"
        time?.text = "0"
        special?.text = "0"
    }

    override fun actionPerformed(btn: GuiButton) {
        super.actionPerformed(btn)
        if (btn.id == 0) {
            Minecraft.getMinecraft().thePlayer?.let {
                it.guiOpen(GuiType.RECIPE_EDITOR, it.worldObj)
            }
        }
    }

    override fun drawGuiContainerForegroundLayer(p_146979_1_: Int, p_146979_2_: Int) {
        super.drawGuiContainerForegroundLayer(p_146979_1_, p_146979_2_)
        setText("Middle Mouse Click:", 0, -65, Color.GRAY)
        setText("enabled/disabled OreDict for ITEM", 0, -53, Color.GRAY)
        setText("Shift + Right/Left Mouse Click:", 0, -40, Color.WHITE)
        setText("+/- Chance for SLOT", 0, -28, Color.WHITE)

        fontRendererObj.drawString("Voltage:", 10, 120, Color.BLACK.hashCode(), false)
        fontRendererObj.drawString("Time:", 10, 135, Color.BLACK.hashCode(), false)
        fontRendererObj.drawString("Special:", 10, 150, Color.BLACK.hashCode(), false)
    }

    override fun drawGuiContainerBackgroundLayer(par1: Float, par2: Int, par3: Int) {
        super.drawGuiContainerBackgroundLayer(par1, par2, par3)
        val x = (width - xSize) / 2
        val y = (height - ySize) / 2
        drawTexturedModalRect(x, y, 0, 0, xSize, ySize)
        drawTexturedModalRect(x + 92 - 1, y + 44 - 1, 200, 18, 18, 18)

        drawTexturedModalRect(x + 92 - 1, y + 80 - 1, 218, 18, 18, 18)

        for ((indexSlot, i) in (0..47).withIndex()) {
            val xx = (mContainer.inventorySlots[i] as Slot).xDisplayPosition - 1
            val yy = (mContainer.inventorySlots[i] as Slot).yDisplayPosition - 1
            if (indexSlot in 24..47) {
                drawTexturedModalRect(x + xx, y + yy, 218, 0, 18, 18)
            } else {
                drawTexturedModalRect(x + xx, y + yy, 200, 0, 18, 18)
            }
        }
        for (f in boxes) {
            f.drawTextBox()
        }
    }

    override fun mouseClicked(mouseX: Int, mouseY: Int, par3: Int) {
        super.mouseClicked(mouseX, mouseY, par3)
        val x = (width - xSize) / 2
        val y = (height - ySize) / 2
        if (mContainer.inventorySlots.size >= 49) {
            val xx: Int = x + (mContainer.inventorySlots[49] as Slot).xDisplayPosition - 1
            val yy: Int = y + (mContainer.inventorySlots[49] as Slot).yDisplayPosition - 1
            if (xx <= mouseX && xx + 18 >= mouseX && yy <= mouseY && yy + 18 >= mouseY) {
                GuiCraftingRecipe.openRecipeGui(ReciperMachine.SELECT_MAP.mUnlocalizedName)
            }
        }
        for (f in boxes) {
            f.mouseClicked(mouseX, mouseY, par3)
        }
    }

    override fun keyTyped(ch: Char, id: Int) {
        super.keyTyped(ch, id)
        for (f in boxes) {
            f.textboxKeyTyped(ch, id)
        }
        if (vol!!.text.isEmpty() || time!!.text.isEmpty()) {
            return
        }
        try {
            val ints: IntArray
            val v = vol!!.text.toInt()
            val t = time!!.text.toInt()
            ints = if (!special!!.text.isEmpty()) {
                val s = special!!.text.toInt()
                intArrayOf(v, t, s)
            } else {
                intArrayOf(v, t)
            }
            PacketInt.commitServer(ints)
        } catch (ignored: Exception) {
        }
    }

    override fun drawScreen(mouseX: Int, mouseY: Int, parTicks: Float) {
        super.drawScreen(mouseX, mouseY, parTicks)
        mContainer?.mTileEntity?.apply {
            if (mContainer.inventorySlots.size >= 50) {
                var x = (mContainer.inventorySlots[49] as Slot).xDisplayPosition - 1
                var y = (mContainer.inventorySlots[49] as Slot).yDisplayPosition - 1
                addTooltip(mouseX, mouseY, x..16, y..16, listOf("Open NEI recipes"))

                x = (mContainer.inventorySlots[48] as Slot).xDisplayPosition - 1
                y = (mContainer.inventorySlots[48] as Slot).yDisplayPosition - 1
                addTooltip(
                    mouseX, mouseY, x..16, y..16, listOf(
                        "Save Recipe",
                        "LClick: Add Recipe",
                    )
                )
                x = (mContainer.inventorySlots[50] as Slot).xDisplayPosition - 1
                y = (mContainer.inventorySlots[50] as Slot).yDisplayPosition - 1
                addTooltip(
                    mouseX, mouseY, x..16, y..16, listOf(
                        "Refresh",
                        "LClick: Clear all Slots",
                    )
                )
            }
        }
    }

    override fun update(values: IntArray) {
        vol?.text = "${values[0]}"
        time?.text = "${values[1]}"
        special?.text = "${values[2]}"
    }
}