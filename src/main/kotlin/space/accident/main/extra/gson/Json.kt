package space.accident.main.extra.gson

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import net.minecraft.item.ItemStack
import net.minecraftforge.fluids.FluidStack

val gson: Gson = getRecipeGson().create()

fun getRecipeGson(): GsonBuilder {
    val builder = GsonBuilder().setPrettyPrinting()
    builder.registerTypeAdapter(ItemStack::class.java, JsonItemStack())
    builder.registerTypeAdapter(FluidStack::class.java, JsonFluidStack())
    return builder
}