package space.accident.main.client.widgets

import net.minecraft.client.gui.FontRenderer
import net.minecraft.client.gui.GuiTextField

class GuiIntegerBox @JvmOverloads constructor(
    fontRenderer: FontRenderer,
    x: Int,
    y: Int,
    width: Int,
    height: Int,
    private val maxValue: Int = Int.MAX_VALUE
) :
    GuiTextField(fontRenderer, x, y, width, height) {
    override fun writeText(selectedText: String) {
        val original = text
        super.writeText(selectedText)
        try {
            val i = text.toInt()
            if (i > maxValue) {
                text = maxValue.toString()
            } else if (i < 0) {
                text = "0"
            }
        } catch (e: NumberFormatException) {
            text = original
        }
    }

    override fun setText(str: String) {
        var s = str
        try {
            val i = s.toInt()
            if (i > maxValue) {
                s = maxValue.toString()
            } else if (i < 0) {
                s = "0"
            }
        } catch (e: NumberFormatException) {
            s = maxValue.toString()
        }
        super.setText(s)
    }
}