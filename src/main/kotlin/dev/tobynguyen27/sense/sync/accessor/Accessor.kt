package dev.tobynguyen27.sense.sync.accessor

import net.minecraft.nbt.CompoundTag

interface Accessor {

    fun isChanged(): Boolean

    fun saveNbt(tag: CompoundTag)

    fun loadNbt(tag: CompoundTag)
}
