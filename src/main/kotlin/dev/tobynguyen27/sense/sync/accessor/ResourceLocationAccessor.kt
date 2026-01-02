package dev.tobynguyen27.sense.sync.accessor

import dev.tobynguyen27.sense.util.SyncUtils
import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.NbtUtils
import net.minecraft.resources.ResourceLocation

class ResourceLocationAccessor(
    val name: String,
    val getter: () -> ResourceLocation?,
    val setter: (ResourceLocation) -> Unit,
) : Accessor {

    private var lastValue = SyncUtils.copy(getter())

    override fun isChanged(): Boolean {
        return SyncUtils.isChanged(lastValue, getter())
    }

    override fun updateLastValue() {
        lastValue = SyncUtils.copy(getter())
    }

    override fun saveNbt(tag: CompoundTag) {
        if (getter() == null) {
            tag.remove(name)
            return
        }
        tag.putString(name, getter().toString())
    }

    override fun loadNbt(tag: CompoundTag) {
        if (tag.contains(name)) setter(ResourceLocation(tag.getString(name)))
    }
}
