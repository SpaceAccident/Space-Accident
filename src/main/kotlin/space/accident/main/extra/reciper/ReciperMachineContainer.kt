package space.accident.main.extra.reciper

import gregtech.api.enums.ItemList
import gregtech.api.gui.GT_ContainerMetaTile_Machine
import gregtech.api.gui.GT_Slot_Holo
import gregtech.api.interfaces.tileentity.IGregTechTileEntity
import gregtech.api.util.GT_Utility
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.entity.player.InventoryPlayer
import net.minecraft.inventory.Slot
import net.minecraft.item.ItemStack
import space.accident.main.extensions.hasKey
import space.accident.main.extensions.removeTag
import space.accident.main.extensions.sendMe
import space.accident.main.extensions.setTag
import space.accident.main.network.PacketInt
import space.accident.main.network.PacketInt.IUpdateInt
import java.util.*

class ReciperMachineContainer(
    inventoryPlayer: InventoryPlayer,
    gte: IGregTechTileEntity,
) : GT_ContainerMetaTile_Machine(inventoryPlayer, gte, 12, 86, true), IUpdateInt {

    companion object {
        const val ORE_DICT_TAG = "oredict"
    }

    override fun addSlots(aPlayerInventory: InventoryPlayer) {
        var indexSlot = 0
        (mTileEntity.metaTileEntity as ReciperMachine).let { reciper ->

            repeat(16) {
                addSlotToContainer(Slot(mTileEntity, indexSlot++, 8 + it % 4 * 18, 8 + (it / 4) % 4 * 18))
            }
            repeat(8) {
                addSlotToContainer(Slot(mTileEntity, indexSlot++, 122 + it % 4 * 18, 8 + (it / 4) % 4 * 18))
            }
            repeat(8) {
                addSlotToContainer(
                    GT_Slot_Holo(
                        mTileEntity,
                        indexSlot++,
                        8 + it % 4 * 18,
                        80 + (it / 4) % 4 * 18,
                        false,
                        true,
                        1
                    )
                )
            }
            repeat(16) {
                addSlotToContainer(
                    GT_Slot_Holo(
                        mTileEntity, indexSlot++, 122 + it % 4 * 18, 44 + (it / 4) % 4 * 18,
                        false, true, 1
                    )
                )
            }
            addSlotToContainer(GT_Slot_Holo(mTileEntity, indexSlot++, 92, 8, false, true, 1))
            addSlotToContainer(GT_Slot_Holo(mTileEntity, indexSlot++, 92, 8 + 36, false, true, 1))
            addSlotToContainer(GT_Slot_Holo(mTileEntity, indexSlot, 92, 8 + 36 + 36, false, true, 1))
        }
    }

    override fun getSlotCount(): Int {
        return 24
    }

    override fun getShiftClickSlotCount(): Int {
        return 24
    }

    override fun slotClick(index: Int, mouse: Int, hotkeys: Int, player: EntityPlayer): ItemStack? {
        try {
            (mTileEntity.metaTileEntity as ReciperMachine).let { recipeEditor ->
                if (index < 16) {
                    if (hotkeys == 1) {
                        if (mTileEntity.isClientSide) return null
                        recipeEditor.mInventory[index]?.let { stack ->
                            stack.stackSize += if (mouse == 1) 1 else if (stack.stackSize <= 0) 0 else -1
                            player.sendMe(stack.toString())
                            return stack
                        }
                    }
                }
                if (index in 16..23) {
                    recipeEditor.chanceEnabled = false
                    if (hotkeys == 1) {
                        recipeEditor.chanceEnabled = true
                        if (mTileEntity.isClientSide) return null
                        val chanceIndex = index - 16
                        recipeEditor.mInventory[index]?.let {
                            var chance: Int = recipeEditor.chance[chanceIndex] / 100
                            chance += if (mouse == 1) 1 else if (chance <= 0) 100 else -1
                            if (chance > 100) chance = 0
                            recipeEditor.chance[chanceIndex] = chance * 100
                            val percent = recipeEditor.chance[chanceIndex] / 100
                            val currentChance = recipeEditor.chance[chanceIndex]
                            player.sendMe("Chance product set: $percent% ($currentChance)")
                            return null
                        }
                    }
                }
                if (index <= 23) {
                    if (hotkeys == 3) {
                        recipeEditor.mInventory[index]?.let { stack ->
                            stack.hasKey(ORE_DICT_TAG) { has ->
                                when (has) {
                                    false -> {
                                        stack.setTag(ORE_DICT_TAG, ORE_DICT_TAG)
                                        player.sendMe("#$index OreDict Enabled")
                                    }
                                    true -> {
                                        stack.removeTag(ORE_DICT_TAG)
                                        player.sendMe("#$index OreDict Disabled")
                                    }
                                }
                            }
                        }
                        return null
                    }
                }
                if (index == 48) {
                    recipeEditor.saveRecipe(player)
                }
                if (index == 50) {
                    if (hotkeys != 1) {
                        recipeEditor.chanceEnabled = false
                        Arrays.fill(recipeEditor.mInventory, null)
                        recipeEditor.voltage = 0
                        recipeEditor.time = 0
                        recipeEditor.special = 0
                    }
                }
                if (index in 24..47) {
                    player.inventory.itemStack?.let { tStackHeld ->
                        GT_Utility.getFluidForFilledItem(tStackHeld, true)?.let {
                            recipeEditor.mInventory[index] = GT_Utility.getFluidDisplayStack(it, true)
                        }
                    } ?: ItemList.Display_Fluid.isStackEqual(recipeEditor.mInventory[index], true, true)
                        .let { if (it) recipeEditor.mInventory[index] = null }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return super.slotClick(index, mouse, hotkeys, player)
    }

    var timer = 0
    override fun detectAndSendChanges() {
        super.detectAndSendChanges()
        with(mTileEntity.metaTileEntity as ReciperMachine) {
            timer++
            if (timer % 20 == 0) {
                val player = mPlayerInventory.player
                if (player is EntityPlayerMP) {
                    PacketInt.commitToClient(
                        player, intArrayOf(voltage, time, special)
                    )
                }
                timer = 0
            }
        }
    }

    override fun update(values: IntArray) {
        with(mTileEntity.metaTileEntity as ReciperMachine) {
            when (values.size) {
                2 -> {
                    voltage = values[0]
                    time = values[1]
                    special = 0
                }
                3 -> {
                    voltage = values[0]
                    time = values[1]
                    special = values[2]
                }
            }
        }
    }
}