package dev.tobynguyen27.testmod

import dev.tobynguyen27.testmod.registry.BlockRegistry
import net.fabricmc.api.ModInitializer
import org.slf4j.LoggerFactory

object TestMod : ModInitializer {

    val LOGGER = LoggerFactory.getLogger("Test Mod")

    override fun onInitialize() {
        BlockRegistry.register()
    }
}
