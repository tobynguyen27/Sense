package dev.tobynguyen27.sense.sync.accessor

import dev.tobynguyen27.sense.util.SyncUtil
import net.minecraft.core.BlockPos
import net.minecraft.nbt.CompoundTag

class BlockPosAccessor(
    val name: String,
    val getter: () -> BlockPos?,
    val setter: (BlockPos?) -> Unit,
    val reader: (CompoundTag, String) -> BlockPos?,
    val writer: (CompoundTag, String, BlockPos?) -> Unit,
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
