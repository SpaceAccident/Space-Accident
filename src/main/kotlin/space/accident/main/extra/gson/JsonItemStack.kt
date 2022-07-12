package space.accident.main.extra.gson

import com.google.gson.*
import gregtech.api.enums.Materials
import gregtech.api.enums.OrePrefixes
import gregtech.api.util.GT_OreDictUnificator
import net.minecraft.item.ItemStack
import space.accident.main.extensions.getStackOfAmountFromOreDict
import space.accident.main.extensions.toItemStack
import space.accident.main.extensions.toJson
import java.lang.reflect.Type

class JsonItemStack : JsonSerializer<ItemStack>, JsonDeserializer<ItemStack> {

    override fun serialize(src: ItemStack, typeOfSrc: Type?, context: JsonSerializationContext): JsonElement {
        return src.toJson()
    }

    @Throws(JsonParseException::class)
    override fun deserialize(json: JsonElement, typeOfT: Type?, context: JsonDeserializationContext): ItemStack {
        val obj = json.asJsonObject
        var stack = json.toItemStack()
        if (obj.has("oredict")) {
            val ore = obj["oredict"].asString
//            if (ore.startsWith("craftingTool")) {
//                stack = ItemStack(Items.written_book, stack.stackSize)
//                OreDictionary.registerOre(ore, stack)
//                return stack
//            }
            val prefixes = OrePrefixes.getOrePrefix(ore)
            val material = OrePrefixes.getMaterial(ore)
            stack = if (prefixes != null && material !== Materials._NULL) {
                GT_OreDictUnificator.get(prefixes, material, stack.stackSize.toLong())
            } else {
                getStackOfAmountFromOreDict(ore, stack.stackSize)
            }
        }
        return stack
    }
}