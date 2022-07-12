package space.accident.main.extensions

import net.minecraft.client.Minecraft
import net.minecraft.client.gui.Gui
import java.awt.Color

fun Gui.setText(text: String, x: Int, y: Int, color: Color, dropShadow: Boolean = false) {
    Minecraft.getMinecraft().fontRenderer.drawString(text, x, y, color.hashCode(), dropShadow)
}