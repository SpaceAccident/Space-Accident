package space.accident.main.extra.reciper

import gregtech.api.enums.Textures
import gregtech.api.interfaces.ITexture
import gregtech.api.interfaces.tileentity.IGregTechTileEntity
import gregtech.api.metatileentity.MetaTileEntity
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_BasicTank
import gregtech.api.render.TextureFactory
import gregtech.api.util.GT_Recipe.GT_Recipe_Map
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.player.InventoryPlayer

class ReciperMachine : GT_MetaTileEntity_BasicTank {

    companion object {
        var SELECT_MAP: GT_Recipe_Map = GT_Recipe_Map.sMappings.first()
    }

    constructor(
        id: Int, name: String
    ) : super(id, "allo", name, 1, SELECT_MAP.mUsualInputCount + SELECT_MAP.mUsualOutputCount + 2, arrayOf(""))

    constructor(
        name: String, desc: Array<String>, textures: Array<Array<Array<ITexture>>>
    ) : super(name, 1, SELECT_MAP.mUsualInputCount + SELECT_MAP.mUsualOutputCount + 2, desc, textures)


    override fun newMetaEntity(aTileEntity: IGregTechTileEntity?): MetaTileEntity {
        return ReciperMachine(mName, mDescriptionArray, mTextures)
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
    ): Array<ITexture>? {
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

    override fun getTextureSet(iTextures: Array<ITexture?>?): Array<Array<Array<ITexture?>?>?>? {
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