package space.accident.main.extra.reciper

import gregtech.api.gui.GT_ContainerMetaTile_Machine
import gregtech.api.interfaces.tileentity.IGregTechTileEntity
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.player.InventoryPlayer
import net.minecraft.inventory.Slot
import net.minecraft.item.ItemStack

class ReciperMachineContainer(
    inventoryPlayer: InventoryPlayer,
    gte: IGregTechTileEntity,
) : GT_ContainerMetaTile_Machine(inventoryPlayer, gte, true) {

    override fun addSlots(aPlayerInventory: InventoryPlayer) {
        var tStartIndex = 0
        (mTileEntity.metaTileEntity as ReciperMachine).let { reciper ->

            when (ReciperMachine.SELECT_MAP.mUsualInputCount) {
                0 -> Unit
                1 -> addSlotToContainer(Slot(mTileEntity, tStartIndex++, 53, 25))
                2 -> {
                    addSlotToContainer(Slot(mTileEntity, tStartIndex++, 35, 25))
                    addSlotToContainer(Slot(mTileEntity, tStartIndex++, 53, 25))
                }
                3 -> {
                    addSlotToContainer(Slot(mTileEntity, tStartIndex++, 17, 25))
                    addSlotToContainer(Slot(mTileEntity, tStartIndex++, 35, 25))
                    addSlotToContainer(Slot(mTileEntity, tStartIndex++, 53, 25))
                }
                4 -> {
                    addSlotToContainer(Slot(mTileEntity, tStartIndex++, 35, 16))
                    addSlotToContainer(Slot(mTileEntity, tStartIndex++, 53, 16))
                    addSlotToContainer(Slot(mTileEntity, tStartIndex++, 35, 34))
                    addSlotToContainer(Slot(mTileEntity, tStartIndex++, 53, 34))
                }
                5 -> {
                    addSlotToContainer(Slot(mTileEntity, tStartIndex++, 17, 16))
                    addSlotToContainer(Slot(mTileEntity, tStartIndex++, 35, 16))
                    addSlotToContainer(Slot(mTileEntity, tStartIndex++, 53, 16))
                    addSlotToContainer(Slot(mTileEntity, tStartIndex++, 35, 34))
                    addSlotToContainer(Slot(mTileEntity, tStartIndex++, 53, 34))
                }
                6 -> {
                    addSlotToContainer(Slot(mTileEntity, tStartIndex++, 17, 16))
                    addSlotToContainer(Slot(mTileEntity, tStartIndex++, 35, 16))
                    addSlotToContainer(Slot(mTileEntity, tStartIndex++, 53, 16))
                    addSlotToContainer(Slot(mTileEntity, tStartIndex++, 17, 34))
                    addSlotToContainer(Slot(mTileEntity, tStartIndex++, 35, 34))
                    addSlotToContainer(Slot(mTileEntity, tStartIndex++, 53, 34))
                }
                7 -> {
                    addSlotToContainer(Slot(mTileEntity, tStartIndex++, 17, 7))
                    addSlotToContainer(Slot(mTileEntity, tStartIndex++, 35, 7))
                    addSlotToContainer(Slot(mTileEntity, tStartIndex++, 53, 7))
                    addSlotToContainer(Slot(mTileEntity, tStartIndex++, 17, 25))
                    addSlotToContainer(Slot(mTileEntity, tStartIndex++, 35, 25))
                    addSlotToContainer(Slot(mTileEntity, tStartIndex++, 53, 25))
                    addSlotToContainer(Slot(mTileEntity, tStartIndex++, 17, 43))
                }
                8 -> {
                    addSlotToContainer(Slot(mTileEntity, tStartIndex++, 17, 7))
                    addSlotToContainer(Slot(mTileEntity, tStartIndex++, 35, 7))
                    addSlotToContainer(Slot(mTileEntity, tStartIndex++, 53, 7))
                    addSlotToContainer(Slot(mTileEntity, tStartIndex++, 17, 25))
                    addSlotToContainer(Slot(mTileEntity, tStartIndex++, 35, 25))
                    addSlotToContainer(Slot(mTileEntity, tStartIndex++, 53, 25))
                    addSlotToContainer(Slot(mTileEntity, tStartIndex++, 17, 43))
                    addSlotToContainer(Slot(mTileEntity, tStartIndex++, 35, 43))
                }
                else -> {
                    addSlotToContainer(Slot(mTileEntity, tStartIndex++, 17, 7))
                    addSlotToContainer(Slot(mTileEntity, tStartIndex++, 35, 7))
                    addSlotToContainer(Slot(mTileEntity, tStartIndex++, 53, 7))
                    addSlotToContainer(Slot(mTileEntity, tStartIndex++, 17, 25))
                    addSlotToContainer(Slot(mTileEntity, tStartIndex++, 35, 25))
                    addSlotToContainer(Slot(mTileEntity, tStartIndex++, 53, 25))
                    addSlotToContainer(Slot(mTileEntity, tStartIndex++, 17, 43))
                    addSlotToContainer(Slot(mTileEntity, tStartIndex++, 35, 43))
                    addSlotToContainer(Slot(mTileEntity, tStartIndex++, 53, 43))
                }
            }
            addSlotToContainer(Slot(mTileEntity, tStartIndex++, 53, 63))

            when (ReciperMachine.SELECT_MAP.mUsualOutputCount) {
                0 -> Unit
                1 -> addSlotToContainer(Slot(mTileEntity, tStartIndex++, 107, 25))
                2 -> {
                    addSlotToContainer(Slot(mTileEntity, tStartIndex++, 107, 25))
                    addSlotToContainer(Slot(mTileEntity, tStartIndex++, 125, 25))
                }
                3 -> {
                    addSlotToContainer(Slot(mTileEntity, tStartIndex++, 107, 25))
                    addSlotToContainer(Slot(mTileEntity, tStartIndex++, 125, 25))
                    addSlotToContainer(Slot(mTileEntity, tStartIndex++, 143, 25))
                }
                4 -> {
                    addSlotToContainer(Slot(mTileEntity, tStartIndex++, 107, 16))
                    addSlotToContainer(Slot(mTileEntity, tStartIndex++, 125, 16))
                    addSlotToContainer(Slot(mTileEntity, tStartIndex++, 107, 34))
                    addSlotToContainer(Slot(mTileEntity, tStartIndex++, 125, 34))
                }
                5 -> {
                    addSlotToContainer(Slot(mTileEntity, tStartIndex++, 107, 16))
                    addSlotToContainer(Slot(mTileEntity, tStartIndex++, 125, 16))
                    addSlotToContainer(Slot(mTileEntity, tStartIndex++, 143, 16))
                    addSlotToContainer(Slot(mTileEntity, tStartIndex++, 107, 34))
                    addSlotToContainer(Slot(mTileEntity, tStartIndex++, 125, 34))
                }
                6 -> {
                    addSlotToContainer(Slot(mTileEntity, tStartIndex++, 107, 16))
                    addSlotToContainer(Slot(mTileEntity, tStartIndex++, 125, 16))
                    addSlotToContainer(Slot(mTileEntity, tStartIndex++, 143, 16))
                    addSlotToContainer(Slot(mTileEntity, tStartIndex++, 107, 34))
                    addSlotToContainer(Slot(mTileEntity, tStartIndex++, 125, 34))
                    addSlotToContainer(Slot(mTileEntity, tStartIndex++, 143, 34))
                }
                7 -> {
                    addSlotToContainer(Slot(mTileEntity, tStartIndex++, 107, 7))
                    addSlotToContainer(Slot(mTileEntity, tStartIndex++, 125, 7))
                    addSlotToContainer(Slot(mTileEntity, tStartIndex++, 143, 7))
                    addSlotToContainer(Slot(mTileEntity, tStartIndex++, 107, 25))
                    addSlotToContainer(Slot(mTileEntity, tStartIndex++, 125, 25))
                    addSlotToContainer(Slot(mTileEntity, tStartIndex++, 143, 25))
                    addSlotToContainer(Slot(mTileEntity, tStartIndex++, 107, 43))
                }
                8 -> {
                    addSlotToContainer(Slot(mTileEntity, tStartIndex++, 107, 7))
                    addSlotToContainer(Slot(mTileEntity, tStartIndex++, 125, 7))
                    addSlotToContainer(Slot(mTileEntity, tStartIndex++, 143, 7))
                    addSlotToContainer(Slot(mTileEntity, tStartIndex++, 107, 25))
                    addSlotToContainer(Slot(mTileEntity, tStartIndex++, 125, 25))
                    addSlotToContainer(Slot(mTileEntity, tStartIndex++, 143, 25))
                    addSlotToContainer(Slot(mTileEntity, tStartIndex++, 107, 43))
                    addSlotToContainer(Slot(mTileEntity, tStartIndex++, 125, 43))
                }
                else -> {
                    addSlotToContainer(Slot(mTileEntity, tStartIndex++, 107, 7))
                    addSlotToContainer(Slot(mTileEntity, tStartIndex++, 125, 7))
                    addSlotToContainer(Slot(mTileEntity, tStartIndex++, 143, 7))
                    addSlotToContainer(Slot(mTileEntity, tStartIndex++, 107, 25))
                    addSlotToContainer(Slot(mTileEntity, tStartIndex++, 125, 25))
                    addSlotToContainer(Slot(mTileEntity, tStartIndex++, 143, 25))
                    addSlotToContainer(Slot(mTileEntity, tStartIndex++, 107, 43))
                    addSlotToContainer(Slot(mTileEntity, tStartIndex++, 125, 43))
                    addSlotToContainer(Slot(mTileEntity, tStartIndex++, 143, 43))
                }
            }
            addSlotToContainer(Slot(mTileEntity, tStartIndex++, 107, 63))

        }
    }

    override fun getSlotCount(): Int {
        return ReciperMachine.SELECT_MAP.mUsualInputCount + ReciperMachine.SELECT_MAP.mUsualOutputCount + 2
    }

    override fun getShiftClickSlotCount(): Int {
        return ReciperMachine.SELECT_MAP.mUsualInputCount + ReciperMachine.SELECT_MAP.mUsualOutputCount + 2
    }

    override fun slotClick(aSlotIndex: Int, aMouseclick: Int, aShifthold: Int, aPlayer: EntityPlayer?): ItemStack? {
        return super.slotClick(aSlotIndex, aMouseclick, aShifthold, aPlayer)
    }
}