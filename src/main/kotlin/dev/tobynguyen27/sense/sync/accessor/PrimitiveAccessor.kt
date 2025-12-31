package dev.tobynguyen27.sense.sync.accessor

import dev.tobynguyen27.sense.util.SyncUtils
import net.minecraft.nbt.CompoundTag

class PrimitiveAccessor<T>(
    val name: String,
    val getter: () -> T?,
    val setter: (T?) -> Unit,
    val reader: (CompoundTag, String) -> T?,
    val writer: (CompoundTag, String, T?) -> Unit,
) : Accessor {

    private var lastValue = SyncUtils.copy(getter())

    override fun updateLastValue() {
        lastValue = SyncUtils.copy(getter())
    }

    override fun isChanged(): Boolean {
        return SyncUtils.isChanged(lastValue, getter())
    }

    override fun loadNbt(tag: CompoundTag) {
        if (tag.contains(name)) setter(reader(tag, name)) else setter(null)
    }

    override fun saveNbt(tag: CompoundTag) {
        if (getter() == null) {
            tag.remove(name)
            return
        }
        writer(tag, name, getter())
    }
}
