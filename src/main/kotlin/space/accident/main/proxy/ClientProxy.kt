package space.accident.main.proxy

import cpw.mods.fml.common.event.*
import space.accident.main.event.ClientEvent

class ClientProxy : CommonProxy() {
    // preInit "Run before anything else. Read your config, create blocks, items,
    // etc, and register them with the GameRegistry."
    override fun preInit(event: FMLPreInitializationEvent) {
        super.preInit(event)
    }

    // load "Do your mod setup. Build whatever data structures you care about. Register recipes."
    override fun init(event: FMLInitializationEvent) {
        super.init(event)

        ClientEvent()
    }

    // postInit "Handle interaction with other mods, complete your setup based on this."
    override fun postInit(event: FMLPostInitializationEvent) {
        super.postInit(event)
    }

    override fun serverAboutToStart(event: FMLServerAboutToStartEvent) {
        super.serverAboutToStart(event)
    }

    // register server commands in this event handler
    override fun serverStarting(event: FMLServerStartingEvent) {
        super.serverStarting(event)
    }

    override fun serverStarted(event: FMLServerStartedEvent) {
        super.serverStarted(event)
    }

    override fun serverStopping(event: FMLServerStoppingEvent) {
        super.serverStopping(event)
    }

    override fun serverStopped(event: FMLServerStoppedEvent) {
        super.serverStopped(event)
    }
}