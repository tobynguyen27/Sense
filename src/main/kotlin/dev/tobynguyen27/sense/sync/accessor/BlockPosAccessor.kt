package dev.tobynguyen27.sense.sync.accessor

import dev.tobynguyen27.sense.util.SyncUtils
import net.minecraft.core.BlockPos
import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.NbtUtils

class BlockPosAccessor(
    val name: String,
    val getter: () -> BlockPos?,
    val setter: (BlockPos?) -> Unit,
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
        tag.put(name, NbtUtils.writeBlockPos(getter()))
    }

    override fun loadNbt(tag: CompoundTag) {
        if (tag.contains(name)) setter(NbtUtils.readBlockPos(tag.getCompound(name)))
    }
}
