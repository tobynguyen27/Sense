package dev.tobynguyen27.sense.client

import dev.tobynguyen27.sense.network.SenseNetwork
import net.fabricmc.api.ClientModInitializer

class SenseClient : ClientModInitializer {
    override fun onInitializeClient() {
        SenseNetwork.handleSyncPacket()
    }
}
