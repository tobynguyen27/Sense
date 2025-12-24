package dev.tobynguyen27.sense.sync.accessor

import dev.tobynguyen27.sense.util.SyncUtil
import net.minecraft.nbt.CompoundTag

class PrimitiveAccessor<T>(
    val name: String,
    val getter: () -> T,
    val setter: (T) -> Unit,
    val reader: (CompoundTag, String) -> T,
    val writer: (CompoundTag, String, T) -> Unit,
) : Accessor {

    private var lastValue = SyncUtil.copy(getter())

    override fun updateLastValue() {
        lastValue = SyncUtil.copy(getter())
    }

    override fun isChanged(): Boolean {
        return SyncUtil.isChanged(lastValue, getter())
    }

    override fun loadNbt(tag: CompoundTag) {
        if (tag.contains(name)) setter(reader(tag, name))
    }

    override fun saveNbt(tag: CompoundTag) {
        writer(tag, name, getter())
    }
}
