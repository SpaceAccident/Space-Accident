package space.accident.main.extra.reciper

import codechicken.nei.recipe.StackInfo
import codechicken.nei.util.NBTJson
import com.google.gson.*
import net.minecraft.item.ItemStack

object RecipeJsonManager {

    val gson: Gson = GsonBuilder().setPrettyPrinting().create()

    inline fun createRecipe(
        inputs: List<ItemStack>,
        output: ItemStack,
        time: Int,
        voltage: Int,
        crossinline action: (recipeJson: String) -> Unit
    ) {
        val inputsJson = JsonArray().apply {
            inputs.forEach { add(it.toJsonObject()) }
        }
        val outJson = output.toJsonObject()

        val json = JsonObject().apply {
            add("inputs", inputsJson)
            add("output", outJson)
            add("time", time.json)
            add("voltage", voltage.json)
        }
        action(gson.toJson(json))
    }

    fun ItemStack.toJsonObject(): JsonElement {
        return StackInfo.itemStackToNBT(this, true).let {
            NBTJson.toJsonObject(it)
        }
    }

    val Number.json: JsonElement
        get() = JsonPrimitive(this)
}