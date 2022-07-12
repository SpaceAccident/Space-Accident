package space.accident.main.proxy

import cpw.mods.fml.common.event.*
import space.accident.main.common.Items.RECIPE_EDITOR
import space.accident.main.config.Config
import space.accident.main.extra.reciper.RecipeJsonManager
import space.accident.main.extra.reciper.ReciperMachine

open class CommonProxy {
    open fun preInit(event: FMLPreInitializationEvent) {
        Config.createConfig(event.suggestedConfigurationFile)
    }

    open fun init(event: FMLInitializationEvent) {
        RECIPE_EDITOR.set(ReciperMachine(16000, "Recipe Editor Machines").getStackForm(1))
    }

    open fun postInit(event: FMLPostInitializationEvent) {
        RecipeJsonManager.load() //Call only last
    }

    open fun serverAboutToStart(event: FMLServerAboutToStartEvent) {

    }

    open fun serverStarting(event: FMLServerStartingEvent) {

    }

    open fun serverStarted(event: FMLServerStartedEvent) {

    }

    open fun serverStopping(event: FMLServerStoppingEvent) {

    }

    open fun serverStopped(event: FMLServerStoppedEvent) {

    }
}