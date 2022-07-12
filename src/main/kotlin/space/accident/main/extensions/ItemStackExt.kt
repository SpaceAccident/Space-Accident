package space.accident.main.extensions

import codechicken.nei.recipe.StackInfo
import codechicken.nei.recipe.StackInfo.loadFromNBT
import codechicken.nei.util.NBTJson.toJsonObject
import codechicken.nei.util.NBTJson.toNbt
import com.google.gson.JsonElement
import net.minecraft.init.Items
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraftforge.oredict.OreDictionary

fun getStackOfAmountFromOreDict(oreDictName: String, amount: Int): ItemStack {
    val list = OreDictionary.getOres(oreDictName)
    if (list.isNotEmpty()) {
        val ret = list.first().copy()
        ret.stackSize = amount
        return ret
    }
    System.err.println("Failed to find $oreDictName in OreDict")
    return ItemStack(Items.written_book, amount)
}

fun ItemStack.toJson(): JsonElement {
    val nbt = StackInfo.itemStackToNBT(this, true)
    return toJsonObject(nbt)
}

fun JsonElement.toItemStack(): ItemStack {
    val nbt = toNbt(this) as NBTTagCompound
    return loadFromNBT(nbt)
}

fun ItemStack.isEmptyTag(): Boolean {
    return this.tagCompound != null
}

fun ItemStack.newTag(): NBTTagCompound {
    this.tagCompound = NBTTagCompound()
    return this.tagCompound
}

fun ItemStack.getTagOrCreate(): NBTTagCompound {
    return if (!hasTagCompound()) newTag() else tagCompound
}

fun ItemStack.removeTag(tag: String) {
    getTagOrCreate().removeTag(tag)
}

inline fun ItemStack.hasKey(value: String, crossinline func: (Boolean) -> Unit) {
    func(getTagOrCreate().hasKey(value))
}

fun ItemStack.setTag(tag: String, value: String) {
    getTagOrCreate().setString(tag, value)
}

fun ItemStack.setTag(tag: String, value: Int) {
    getTagOrCreate().setInteger(tag, value)
}

fun ItemStack.setTag(tag: String, value: Long) {
    getTagOrCreate().setLong(tag, value)
}



