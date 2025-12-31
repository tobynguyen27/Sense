package dev.tobynguyen27.sense

import net.fabricmc.api.ModInitializer
import org.slf4j.Logger
import org.slf4j.LoggerFactory

object Sense : ModInitializer {

    const val MOD_ID = "sense"
    const val MOD_NAME = "Sense"

    val LOGGER: Logger = LoggerFactory.getLogger(MOD_NAME)

    override fun onInitialize() {
        LOGGER.info("Sense is waking up")
    }
}
