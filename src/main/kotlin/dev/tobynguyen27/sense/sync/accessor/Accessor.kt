package dev.tobynguyen27.sense.sync.accessor

import net.minecraft.nbt.CompoundTag

interface Accessor {

    fun isChanged(): Boolean

    fun updateLastValue()

    fun saveNbt(tag: CompoundTag)

    fun loadNbt(tag: CompoundTag)
}
