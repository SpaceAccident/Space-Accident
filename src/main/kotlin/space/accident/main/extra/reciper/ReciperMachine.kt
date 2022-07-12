package space.accident.main.extra.reciper

import gregtech.api.enums.Textures
import gregtech.api.interfaces.ITexture
import gregtech.api.interfaces.tileentity.IGregTechTileEntity
import gregtech.api.metatileentity.MetaTileEntity
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_BasicTank
import gregtech.api.render.TextureFactory
import gregtech.api.util.GT_Recipe.GT_Recipe_Map
import gregtech.api.util.GT_Utility
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.player.InventoryPlayer
import net.minecraft.item.ItemStack
import net.minecraftforge.fluids.FluidStack
import space.accident.main.extensions.sendMe

class ReciperMachine : GT_MetaTileEntity_BasicTank {

    companion object {
        var SELECT_MAP: GT_Recipe_Map = GT_Recipe_Map.sMappings.first()
    }

    var voltage = 0
    var time: Int = 0
    var special: Int = 0
    var chance = IntArray(16)
    var chanceEnabled = false

    constructor(
        id: Int, name: String
    ) : super(id, "allo", name, 1, 50, arrayOf(""))

    constructor(
        name: String, desc: Array<String>, textures: Array<Array<Array<ITexture>>>
    ) : super(name, 1, 50, desc, textures)


    override fun newMetaEntity(aTileEntity: IGregTechTileEntity?): MetaTileEntity {
        return ReciperMachine(mName, mDescriptionArray, mTextures)
    }


    fun saveRecipe(player: EntityPlayer) {
        val inputI = mutableListOf<ItemStack>()
        val outputI = mutableListOf<ItemStack>()
        val inputF = mutableListOf<FluidStack>()
        val outputF = mutableListOf<FluidStack>()
        try {
            repeat(mInventory.size) {
                mInventory[it]?.let { stack ->
                    if (it < 16) {
                        inputI += stack
                    } else if (it < 24) {
                        outputI += stack
                    } else if (it < 32) {
                        GT_Utility.getFluidFromDisplayStack(stack)?.let { fs ->
                            inputF += fs
                        }
                    } else if (it < 48) {
                        GT_Utility.getFluidFromDisplayStack(stack)?.let { fs ->
                            outputF += fs
                        }
                    }
                }
            }

            if (time == 0 || voltage == 0) {
                if (baseMetaTileEntity.isServerSide) {
                    player.sendMe("Error Recipe! Check Time or Voltage")
                }
                return
            }

            val newChance = IntArray(outputI.size)
            if (chance.size >= SELECT_MAP.mUsualOutputCount) {
                for (reChance in outputI.indices) {
                    newChance[reChance] = chance[reChance]
                }
            }

            if (SELECT_MAP.mUsualInputCount < inputI.size || SELECT_MAP.mUsualOutputCount < outputI.size) {
                if (baseMetaTileEntity.isServerSide) {
                    player.sendMe("Error Recipe! Check Inputs or Outputs (Maximum Inputs/Outputs)")
                }
                return
            }

            if (SELECT_MAP.mMinimalInputFluids > inputF.size || SELECT_MAP.mMinimalInputItems > inputI.size) {
                if (baseMetaTileEntity.isServerSide) {
                    player.sendMe("Error Recipe! Check Inputs (Minimum Inputs)")
                }
                return
            }

            val recipe = SELECT_MAP.addRecipe(
                false,
                inputI.toTypedArray(),
                outputI.toTypedArray(),
                if (chanceEnabled) newChance else null,
                inputF.toTypedArray(),
                outputF.toTypedArray(),
                time,
                voltage,
                special
            )
            if (recipe == null) {
                if (baseMetaTileEntity.isServerSide) {
                    player.sendMe("Error Recipe!")
                }
                return
            }

            val jsonRecipe = RecipeJsonManager.Recipe(
                optimize = false,
                time = time,
                voltage = voltage,
                special = special,
                chance = if (chanceEnabled) chance.toList() else null,
                inItems = inputI,
                outItems = outputI,
                inFluids = inputF,
                outFluids = outputF
            )

            RecipeJsonManager.save(SELECT_MAP, jsonRecipe)

            if (baseMetaTileEntity.isServerSide) {
                player.sendMe("Recipe Added")
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun getClientGUI(
        aID: Int,
        aPlayerInventory: InventoryPlayer,
        aBaseMetaTileEntity: IGregTechTileEntity
    ): Any {
        return ReciperMachineGui(aPlayerInventory, aBaseMetaTileEntity)
    }

    override fun getServerGUI(
        aID: Int,
        aPlayerInventory: InventoryPlayer,
        aBaseMetaTileEntity: IGregTechTileEntity
    ): Any {
        return ReciperMachineContainer(aPlayerInventory, aBaseMetaTileEntity)
    }

    override fun getTexture(
        aBaseMetaTileEntity: IGregTechTileEntity?, aSide: Byte, aFacing: Byte,
        aColorIndex: Byte, aActive: Boolean, aRedstone: Boolean
    ): Array<ITexture> {
        return if (aSide != aFacing) if (aSide.toInt() == 1) arrayOf(
            Textures.BlockIcons.MACHINE_CASINGS[mTier.toInt()][aColorIndex + 1],
            TextureFactory.of(Textures.BlockIcons.OVERLAY_STANK)
        ) else arrayOf(Textures.BlockIcons.MACHINE_CASINGS[mTier.toInt()][aColorIndex + 1]) else if (aActive) getTexturesActive(
            Textures.BlockIcons.MACHINE_CASINGS[mTier.toInt()][aColorIndex + 1]
        ) else getTexturesInactive(Textures.BlockIcons.MACHINE_CASINGS[mTier.toInt()][aColorIndex + 1])
    }

    private fun getTexturesActive(aBaseTexture: ITexture): Array<ITexture> {
        return arrayOf(
            aBaseTexture,
            TextureFactory.of(Textures.BlockIcons.OVERLAY_PIPE_OUT)
        )
    }

    private fun getTexturesInactive(aBaseTexture: ITexture): Array<ITexture> {
        return arrayOf(
            aBaseTexture,
            TextureFactory.of(Textures.BlockIcons.OVERLAY_PIPE_OUT)
        )
    }

    override fun onRightclick(aBaseMetaTileEntity: IGregTechTileEntity, aPlayer: EntityPlayer): Boolean {
        return if (aBaseMetaTileEntity.isClientSide) {
            true
        } else {
            aBaseMetaTileEntity.openGUI(aPlayer)
            true
        }
    }

    override fun getTextureSet(iTextures: Array<ITexture?>?): Array<Array<Array<ITexture?>?>?> {
        return arrayOfNulls<Array<Array<ITexture?>?>?>(0)
    }

    override fun isSimpleMachine(): Boolean {
        return true
    }

    override fun isFacingValid(aFacing: Byte): Boolean {
        return true
    }

    override fun isAccessAllowed(aPlayer: EntityPlayer?): Boolean {
        return true
    }

    override fun getUpdateData(): Byte {
        return 0
    }

    override fun doesFillContainers(): Boolean {
        return false
    }

    override fun doesEmptyContainers(): Boolean {
        return false
    }

    override fun canTankBeFilled(): Boolean {
        return false
    }

    override fun canTankBeEmptied(): Boolean {
        return false
    }

    override fun displaysItemStack(): Boolean {
        return false
    }

    override fun displaysStackSize(): Boolean {
        return false
    }

    override fun getCapacity(): Int {
        return 10000000
    }

}