package dev.tobynguyen27.sense.sync.blockentity

import net.minecraft.nbt.CompoundTag

interface AutoPersistBlockEntity : AutoManagedBlockEntity {
    fun loadPersistedFields(tag: CompoundTag) {
        getFieldContainer().loadPersistedFields(tag)
    }

    fun savePersistedFields(tag: CompoundTag) {
        getFieldContainer().savePersistedFields(tag)
    }
}
