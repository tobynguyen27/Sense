package dev.tobynguyen27.sense.sync.blockentity

import dev.tobynguyen27.sense.network.SenseNetwork
import net.minecraft.nbt.CompoundTag

interface AutoSyncBlockEntity : AutoManagedBlockEntity {

    fun syncTick() {
        val tag = fieldContainer.collectDirtyFields() ?: return

        SenseNetwork.sendSyncPacket(self, tag)
    }

    fun loadSyncFields(tag: CompoundTag) {
        fieldContainer.syncedFields.forEach { it.loadNbt(tag) }
    }

    fun saveNonPersistedSyncFields(tag: CompoundTag) {
        fieldContainer.nonPersistedSyncedFields.forEach { it.saveNbt(tag) }
    }
}
