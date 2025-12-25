package dev.tobynguyen27.sense.sync.blockentity

import dev.tobynguyen27.sense.network.SenseNetwork

interface AutoSyncBlockEntity : AutoManagedBlockEntity {

    fun syncTick() {
        val tag = getFieldContainer().collectDirtyFields() ?: return

        SenseNetwork.sendSyncPacket(getSelf(), tag)
    }
}
