package space.accident.main.config

import net.minecraftforge.common.config.Configuration
import java.io.File

object Config {

    //Category
    private const val generalCategory = "general"

    //Values
    var isStarted = true

    private inline fun onPostCreate(configFile: File?, crossinline action: (Configuration) -> Unit) {
        Configuration(configFile).let { config ->
            config.load()
            action(config)
            if (config.hasChanged()) {
                config.save()
            }
        }
    }

    fun createConfig(configFile: File?) {
        onPostCreate(configFile) { cfg ->
            isStarted = cfg[generalCategory, "isStarted", isStarted, "Config test"].boolean
        }
    }
}
