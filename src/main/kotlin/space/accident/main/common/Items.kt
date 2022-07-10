package space.accident.main.common

import gregtech.api.enums.GT_Values
import gregtech.api.interfaces.IItemContainer
import gregtech.api.util.GT_ModHandler
import gregtech.api.util.GT_OreDictUnificator
import gregtech.api.util.GT_Utility
import net.minecraft.block.Block
import net.minecraft.item.Item
import net.minecraft.item.ItemStack

enum class Items : IItemContainer {

    RECIPE_EDITOR;

    private var mStack: ItemStack? = null
    private var mHasNotBeenSet = true

    override fun set(aItem: Item?): IItemContainer? {
        mHasNotBeenSet = false
        if (aItem == null) {
            return this
        }
        val aStack = ItemStack(aItem, 1, 0)
        mStack = GT_Utility.copyAmount(1, aStack)
        return this
    }

    override fun set(aStack: ItemStack?): IItemContainer? {
        mHasNotBeenSet = false
        mStack = GT_Utility.copyAmount(1, aStack)
        return this
    }

    override fun getItem(): Item? {
        if (mHasNotBeenSet) {
            throw IllegalAccessError(
                "The Enum '$name' has not been set to an Item at this time!"
            )
        }
        return if (GT_Utility.isStackInvalid(mStack)) {
            null
        } else mStack!!.item
    }

    override fun getBlock(): Block? {
        if (mHasNotBeenSet) {
            throw IllegalAccessError(
                "The Enum '$name' has not been set to an Item at this time!"
            )
        }
        return GT_Utility.getBlockFromItem(item)
    }

    override fun hasBeenSet(): Boolean {
        return !mHasNotBeenSet
    }

    override fun isStackEqual(aStack: Any?): Boolean {
        return isStackEqual(aStack, aWildcard = false, aIgnoreNBT = false)
    }

    override fun isStackEqual(aStack: Any?, aWildcard: Boolean, aIgnoreNBT: Boolean): Boolean {
        return !GT_Utility.isStackInvalid(aStack) && GT_Utility
            .areUnificationsEqual(aStack as ItemStack?, if (aWildcard) getWildcard(1) else get(1), aIgnoreNBT)
    }

    override fun get(aAmount: Long, vararg aReplacements: Any?): ItemStack {
        if (mHasNotBeenSet) {
            throw IllegalAccessError(
                "The Enum '$name' has not been set to an Item at this time!"
            )
        }
        return if (GT_Utility.isStackInvalid(mStack)) {
            GT_Utility.copyAmount(aAmount, *aReplacements)
        } else GT_Utility.copyAmount(aAmount, GT_OreDictUnificator.get(mStack))
    }

    override fun getWildcard(aAmount: Long, vararg aReplacements: Any?): ItemStack? {
        if (mHasNotBeenSet) {
            throw IllegalAccessError(
                "The Enum '$name' has not been set to an Item at this time!"
            )
        }
        return if (GT_Utility.isStackInvalid(mStack)) {
            GT_Utility.copyAmount(aAmount, *aReplacements)
        } else GT_Utility.copyAmountAndMetaData(aAmount, GT_Values.W.toLong(), GT_OreDictUnificator.get(mStack))
    }

    override fun getUndamaged(aAmount: Long, vararg aReplacements: Any?): ItemStack? {
        if (mHasNotBeenSet) {
            throw IllegalAccessError(
                "The Enum '$name' has not been set to an Item at this time!"
            )
        }
        return if (GT_Utility.isStackInvalid(mStack)) {
            GT_Utility.copyAmount(aAmount, *aReplacements)
        } else GT_Utility.copyAmountAndMetaData(aAmount, 0, GT_OreDictUnificator.get(mStack))
    }

    override fun getAlmostBroken(aAmount: Long, vararg aReplacements: Any?): ItemStack? {
        if (mHasNotBeenSet) {
            throw IllegalAccessError(
                "The Enum '$name' has not been set to an Item at this time!"
            )
        }
        return if (GT_Utility.isStackInvalid(mStack)) {
            GT_Utility.copyAmount(aAmount, *aReplacements)
        } else GT_Utility.copyAmountAndMetaData(
            aAmount, (mStack!!.maxDamage - 1).toLong(),
            GT_OreDictUnificator.get(mStack)
        )
    }

    override fun getWithName(aAmount: Long, aDisplayName: String?, vararg aReplacements: Any?): ItemStack? {
        val rStack = get(1, *aReplacements)
        if (GT_Utility.isStackInvalid(rStack)) {
            return null
        }
        rStack.setStackDisplayName(aDisplayName)
        return GT_Utility.copyAmount(aAmount, rStack)
    }

    override fun getWithCharge(aAmount: Long, aEnergy: Int, vararg aReplacements: Any?): ItemStack? {
        val rStack = get(1, *aReplacements)
        if (GT_Utility.isStackInvalid(rStack)) {
            return null
        }
        GT_ModHandler.chargeElectricItem(rStack, aEnergy, Int.MAX_VALUE, true, false)
        return GT_Utility.copyAmount(aAmount, rStack)
    }

    override fun getWithDamage(aAmount: Long, aMetaValue: Long, vararg aReplacements: Any?): ItemStack? {
        if (mHasNotBeenSet) {
            throw IllegalAccessError(
                "The Enum '$name' has not been set to an Item at this time!"
            )
        }
        return if (GT_Utility.isStackInvalid(mStack)) {
            GT_Utility.copyAmount(aAmount, *aReplacements)
        } else GT_Utility.copyAmountAndMetaData(aAmount, aMetaValue, GT_OreDictUnificator.get(mStack))
    }

    override fun registerOre(vararg aOreNames: Any?): IItemContainer {
        if (mHasNotBeenSet) {
            throw IllegalAccessError(
                "The Enum '$name' has not been set to an Item at this time!"
            )
        }
        for (tOreName in aOreNames) {
            GT_OreDictUnificator.registerOre(tOreName, get(1))
        }
        return this
    }

    override fun registerWildcardAsOre(vararg aOreNames: Any?): IItemContainer {
        if (mHasNotBeenSet) {
            throw IllegalAccessError(
                "The Enum '$name' has not been set to an Item at this time!"
            )
        }
        for (tOreName in aOreNames) {
            GT_OreDictUnificator.registerOre(tOreName, getWildcard(1))
        }
        return this
    }
}