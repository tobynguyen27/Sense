package dev.tobynguyen27.sense.sync.blockentity

import net.minecraft.nbt.CompoundTag

interface AutoPersistBlockEntity : AutoManagedBlockEntity {
    fun loadPersistedFields(tag: CompoundTag) {
        fieldContainer.persistedFields.forEach { it.loadNbt(tag) }
    }

    fun savePersistedFields(tag: CompoundTag) {
        fieldContainer.persistedFields.forEach { it.saveNbt(tag) }
    }
}
