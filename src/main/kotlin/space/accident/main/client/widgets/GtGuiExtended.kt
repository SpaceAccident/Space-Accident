package space.accident.main.client.widgets

import gregtech.api.GregTech_API
import gregtech.api.enums.Dyes
import gregtech.api.gui.GT_ContainerMetaTile_Machine
import gregtech.api.gui.GT_GUIContainerMetaTile_Machine
import net.minecraft.client.gui.FontRenderer
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL12

open class GtGuiExtended(
    container: GT_ContainerMetaTile_Machine,
    guiPath: String
) : GT_GUIContainerMetaTile_Machine(container, guiPath) {

    override fun drawGuiContainerBackgroundLayer(par1: Float, par2: Int, par3: Int) {
        super.drawGuiContainerBackgroundLayer(par1, par2, par3)
        mContainer?.mTileEntity?.let { gte ->
            if (GregTech_API.sColoredGUI) {
                gte.colorization.let {
                    val color: Dyes = if (it.toInt() != -1) Dyes.get(it.toInt()) else Dyes.MACHINE_METAL
                    GL11.glColor3ub(color.mRGBa[0].toByte(), color.mRGBa[1].toByte(), color.mRGBa[2].toByte())
                }
            } else {
                GL11.glColor3ub(255.toByte(), 255.toByte(), 255.toByte())
            }
        }
    }

    fun addTooltip(mouseX: Int, mouseY: Int, xx: IntRange, yy: IntRange, strings: List<String>) {
        var mX = mouseX
        var mY = mouseY
        var startX = xx.first
        var startY = yy.first
        val renderPosX = mX
        val renderPosY = mY
        mX -= (width - xSize) / 2
        mY -= (height - ySize) / 2
        if (mContainer.mTileEntity != null) {
            if (mX < startX || mY < startY) return
            startY += xx.last
            if (mX < xx.last.let { startX += it; startX }) {
                if (mY < startY) {
                    renderText(strings, renderPosX, renderPosY, fontRendererObj)
                }
            }
        }
    }

    fun renderText(strings: List<*>, x: Int, y: Int, font: FontRenderer) {
        if (!strings.isEmpty()) {
            GL11.glDisable(GL12.GL_RESCALE_NORMAL)
            GL11.glDisable(GL11.GL_LIGHTING)
            GL11.glDisable(GL11.GL_DEPTH_TEST)
            var k = 0
            for (aP in strings) {
                val s = aP as String?
                val l = font.getStringWidth(s)
                if (l > k) {
                    k = l
                }
            }
            var x2 = x + 12
            var y2 = y - 12
            var i1 = 8
            if (strings.size > 1) {
                i1 += 2 + (strings.size - 1) * 10
            }
            if (x2 + k > width) {
                x2 -= 28 + k
            }
            if (y2 + i1 + 6 > height) {
                y2 = height - i1 - 6
            }
            val j1 = -0x10000000 //Фон тултипа
            drawGradientRect(x2 - 3, y2 - 4, x2 + k + 3, y2 - 3, j1, j1)
            drawGradientRect(x2 - 3, y2 + i1 + 3, x2 + k + 3, y2 + i1 + 4, j1, j1)
            drawGradientRect(x2 - 3, y2 - 3, x2 + k + 3, y2 + i1 + 3, j1, j1)
            drawGradientRect(x2 - 4, y2 - 3, x2 - 3, y2 + i1 + 3, j1, j1)
            drawGradientRect(x2 + k + 3, y2 - 3, x2 + k + 4, y2 + i1 + 3, j1, j1)
            val k1 = -0xffefc0 //Обводка тултипа
            val l1 = k1 and 0xfefefe shr 1 or (k1 and -0x1000000) //Затемнение обводки
            drawGradientRect(x2 - 3, y2 - 3 + 1, x2 - 3 + 1, y2 + i1 + 3 - 1, k1, l1)
            drawGradientRect(x2 + k + 2, y2 - 3 + 1, x2 + k + 3, y2 + i1 + 3 - 1, k1, l1)
            drawGradientRect(x2 - 3, y2 - 3, x2 + k + 3, y2 - 3 + 1, k1, k1)
            drawGradientRect(x2 - 3, y2 + i1 + 2, x2 + k + 3, y2 + i1 + 3, l1, l1)
            for (i2 in strings.indices) {
                val s1 = strings[i2] as String
                font.drawStringWithShadow(s1, x2, y2, -1)
                if (i2 == 0) {
                    y2 += 2
                }
                y2 += 10
            }
            GL11.glEnable(GL11.GL_LIGHTING)
            GL11.glEnable(GL11.GL_DEPTH_TEST)
            GL11.glEnable(GL12.GL_RESCALE_NORMAL)
        }
    }

}