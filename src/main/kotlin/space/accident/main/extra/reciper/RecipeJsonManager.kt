package space.accident.main.extra.reciper

import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import gregtech.api.util.GT_Recipe.GT_Recipe_Map
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import net.minecraft.item.ItemStack
import net.minecraftforge.fluids.FluidStack
import space.accident.main.extra.gson.gson
import java.io.File
import java.io.FileInputStream
import java.io.FileReader
import java.io.FileWriter
import java.lang.reflect.Type

object RecipeJsonManager {

    private val gsonType: Type = object : TypeToken<List<Recipe>>() {}.type

    data class Recipe(
        @SerializedName("in") val inItems: List<ItemStack>,
        @SerializedName("out") val outItems: List<ItemStack>,
        @SerializedName("in_f") val inFluids: List<FluidStack>,
        @SerializedName("out_f") val outFluids: List<FluidStack>,
        @SerializedName("time") val time: Int,
        @SerializedName("voltage") val voltage: Int,
        @SerializedName("special") val special: Int,
        @SerializedName("chance") val chance: List<Int>?,
        @SerializedName("optimize") val optimize: Boolean
    )

    fun save(map: GT_Recipe_Map, recipe: Recipe) {
        runBlocking(Dispatchers.IO) {
            val saveDir = File("config/SpaceAccident/recipes")
            try {
                saveDir.mkdirs()
                val file = "${map.mUnlocalizedName.replace(".", "_")}.json"
                val fileRecipe = File(saveDir.path, file)

                val recipes = kotlin.runCatching {
                    val json = FileInputStream(fileRecipe).bufferedReader().use { it.readText() }
                    gson.fromJson<List<Recipe>>(json, gsonType).toMutableList()
                }.getOrNull() ?: mutableListOf()

                recipes += recipe

                FileWriter(fileRecipe).use { writer ->
                    val gsonString = gson.toJson(recipes, gsonType)
                    writer.write(gsonString)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun load() {
        val saveDir = File("config/SpaceAccident/recipes")

        val files = saveDir.listFiles()
        files?.forEach { file ->
            try {
                val mapName = file.name.replace(".json", "").replace("_", ".")
                FileReader(file).buffered().use { stream ->
                    gson.fromJson<List<Recipe>>(stream, gsonType)?.let { recipes ->
                        GT_Recipe_Map.sMappings.find { it.mUnlocalizedName == mapName }?.let { map ->
                            for (recipeJson in recipes) {
                                map.addRecipe(
                                    recipeJson.optimize,
                                    recipeJson.inItems.toTypedArray(),
                                    recipeJson.outItems.toTypedArray(),
                                    null,
                                    recipeJson.chance?.toIntArray(),
                                    recipeJson.inFluids.toTypedArray(),
                                    recipeJson.outFluids.toTypedArray(),
                                    recipeJson.time,
                                    recipeJson.voltage,
                                    recipeJson.special
                                )
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    data class MatrixRecipe(
        val one: Pair<Char, ItemStack>? = null,
        val two: Pair<Char, ItemStack>? = null,
        val three: Pair<Char, ItemStack>? = null,
        val four: Pair<Char, ItemStack>? = null,
        val five: Pair<Char, ItemStack>? = null,
        val six: Pair<Char, ItemStack>? = null,
        val seven: Pair<Char, ItemStack>? = null,
        val eight: Pair<Char, ItemStack>? = null,
        val nine: Pair<Char, ItemStack>? = null,
    )

    data class ShapeRecipe(
        val first: String,
        val second: String,
        val third: String,
    )
}