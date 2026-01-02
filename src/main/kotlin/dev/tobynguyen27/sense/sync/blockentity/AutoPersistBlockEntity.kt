package dev.tobynguyen27.sense.sync.blockentity

import net.minecraft.nbt.CompoundTag

interface AutoPersistBlockEntity : AutoManagedBlockEntity {
    fun loadPersistedFields(tag: CompoundTag) {
        fieldContainer.loadPersistedFields(tag)
    }

    fun savePersistedFields(tag: CompoundTag) {
        fieldContainer.savePersistedFields(tag)
    }
}
