package dev.tobynguyen27.sense.sync.blockentity

import dev.tobynguyen27.sense.network.SenseNetwork

interface AutoSyncBlockEntity : AutoManagedBlockEntity {

    fun syncTick() {
        val tag = fieldContainer.collectDirtyFields() ?: return

        SenseNetwork.sendSyncPacket(self, tag)
    }
}
