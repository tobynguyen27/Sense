package dev.tobynguyen27.sense.sync.accessor

import dev.tobynguyen27.sense.util.SyncUtil
import net.minecraft.nbt.CompoundTag
import net.minecraft.resources.ResourceLocation

class ResourceLocationAccessor(
    val name: String,
    val getter: () -> ResourceLocation?,
    val setter: (ResourceLocation?) -> Unit,
    val reader: (CompoundTag, String) -> ResourceLocation?,
    val writer: (CompoundTag, String, ResourceLocation?) -> Unit,
) : Accessor {

    private var lastValue = SyncUtil.copy(getter())

    override fun isChanged(): Boolean {
        return SyncUtil.isChanged(lastValue, getter())
    }

    override fun updateLastValue() {
        lastValue = SyncUtil.copy(getter())
    }

    override fun saveNbt(tag: CompoundTag) {
        if (getter() == null) {
            tag.remove(name)
            return
        }
        writer(tag, name, getter())
    }

    override fun loadNbt(tag: CompoundTag) {
        if (tag.contains(name)) setter(reader(tag, name)) else setter(null)
    }
}
