package dev.tobynguyen27.sense.sync.accessor

import net.minecraft.nbt.CompoundTag

class IntAccessor(val key: String, val getter: () -> Int, val setter: (Int) -> Unit) : Accessor {
    override fun saveNbt(tag: CompoundTag) {
        tag.putInt(key, getter())
    }

    override fun loadNbt(tag: CompoundTag) {
        setter(tag.getInt(key))
    }
}
