@file:Suppress("UNCHECKED_CAST")

package space.accident.main.extra.reciper

import cpw.mods.fml.relauncher.Side
import cpw.mods.fml.relauncher.SideOnly
import gregtech.api.util.GT_LanguageManager
import gregtech.api.util.GT_Recipe
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiButton
import net.minecraft.client.gui.GuiScreen
import net.minecraft.client.gui.GuiTextField
import space.accident.main.network.PacketOpenRecipeEditor


@SideOnly(Side.CLIENT)
class ReciperGuiSelect : GuiScreen() {

    private val xSize = 176
    private val ySize = 166

    private val maps = GT_Recipe.GT_Recipe_Map.sMappings.toList()
    private val mapLocalized = maps.map { GT_LanguageManager.getTranslation(it.mUnlocalizedName) }
    private val mapSearched = mutableMapOf<Int, GT_Recipe.GT_Recipe_Map>()

    private lateinit var searchField: GuiTextField

    override fun initGui() {
        buttonList.clear()
        val posX = (width - xSize) / 2
        val posY = (height - ySize) / 2
        buttonList.add(GuiButton(0, posX + xSize - 50, posY - 19, 50, 20, "Search"))
        searchField = GuiTextField(fontRendererObj, posX + 1, posY - 18, xSize - 55, 18)
        searchField.enableBackgroundDrawing = true
        searchField.maxStringLength = 16
        searchField.setTextColor(16777215)
        searchField.visible = true
        searchField.isFocused = true
        searchField.text = ""
    }

    private fun searchMaps(entry: String) {
        mapSearched.clear()
        mapLocalized.forEachIndexed { id, mapName ->
            if (mapName.lowercase().contains(entry.lowercase())) {
                maps[id]?.let { mapSearched.put(id, it) }
            }
        }
        createBtns()
    }

    private fun createBtns() {
        buttonList.clear()
        val posX = (width - xSize) / 2
        val posY = (height - ySize) / 2
        buttonList.add(GuiButton(0, posX + xSize - 50, posY - 19, 50, 20, "Search"))
        var count = 0
        for ((id, map) in mapSearched) {
            count++
            if (count > 5) break
            buttonList.add(
                GuiButton(
                    id, posX, posY + 20 * count, xSize, 20,
                    GT_LanguageManager.getTranslation(map.mUnlocalizedName)
                )
            )
        }
        updateScreen()
    }

    override fun keyTyped(typedChar: Char, keyCode: Int) {
        searchField.textboxKeyTyped(typedChar, keyCode)
        super.keyTyped(typedChar, keyCode)
    }

    override fun mouseClicked(mouseX: Int, mouseY: Int, mouseButton: Int) {
        searchField.mouseClicked(mouseX, mouseY, mouseButton)
        super.mouseClicked(mouseX, mouseY, mouseButton)
    }

    override fun updateScreen() {
        searchField.updateCursorCounter()
    }

    override fun drawScreen(mouseX: Int, mouseY: Int, partialTicks: Float) {
        drawDefaultBackground()
        searchField.drawTextBox()
        super.drawScreen(mouseX, mouseY, partialTicks)
    }

    override fun actionPerformed(btn: GuiButton) {
        super.actionPerformed(btn)
        when (btn.id) {
            0 -> searchMaps(searchField.text)
            else -> mapSearched[btn.id]?.let {
                ReciperMachine.SELECT_MAP = it
                PacketOpenRecipeEditor.commit(it.mNEIName)
                Minecraft.getMinecraft().thePlayer.closeScreen()
            }
        }
    }

    override fun doesGuiPauseGame(): Boolean {
        return false
    }
}