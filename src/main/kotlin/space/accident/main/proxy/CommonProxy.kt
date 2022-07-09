package space.accident.main.proxy

import cpw.mods.fml.common.event.*
import space.accident.main.config.Config

open class CommonProxy {
    // preInit "Run before anything else. Read your config, create blocks, items,
    // etc, and register them with the GameRegistry."
    open fun preInit(event: FMLPreInitializationEvent) {
        Config.createConfig(event.suggestedConfigurationFile)
    }

    // load "Do your mod setup. Build whatever data structures you care about. Register recipes."
    open fun init(event: FMLInitializationEvent) {

    }

    // postInit "Handle interaction with other mods, complete your setup based on this."
    open fun postInit(event: FMLPostInitializationEvent) {

    }

    open fun serverAboutToStart(event: FMLServerAboutToStartEvent) {

    }

    // register server commands in this event handler
    open fun serverStarting(event: FMLServerStartingEvent) {

    }

    open fun serverStarted(event: FMLServerStartedEvent) {

    }

    open fun serverStopping(event: FMLServerStoppingEvent) {

    }

    open fun serverStopped(event: FMLServerStoppedEvent) {

    }
}