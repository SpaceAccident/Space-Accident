package space.accident.main.client

import cpw.mods.fml.client.registry.ClientRegistry
import cpw.mods.fml.relauncher.Side
import cpw.mods.fml.relauncher.SideOnly
import net.minecraft.client.settings.KeyBinding
import org.lwjgl.input.Keyboard

@SideOnly(Side.CLIENT)
object KeyBindings {

    val openRecipeEditor: KeyBinding = KeyBinding("Open Recipe Editor", Keyboard.KEY_P, "Space Accident").apply {
        ClientRegistry.registerKeyBinding(this)
    }

}