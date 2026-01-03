package dev.tobynguyen27.sense.sync.accessor

import dev.tobynguyen27.sense.util.SyncUtils
import net.minecraft.nbt.CompoundTag
import team.reborn.energy.api.base.SimpleEnergyStorage

class EnergyStorageAccessor(val name: String, val getter: () -> SimpleEnergyStorage?) : Accessor {

    private var lastValue = SyncUtils.copy(getter()?.amount)

    override fun isChanged(): Boolean {
        return SyncUtils.isChanged(lastValue, getter()?.amount)
    }

    override fun updateLastValue() {
        lastValue = SyncUtils.copy(getter()?.amount)
    }

    override fun saveNbt(tag: CompoundTag) {
        val value = getter()?.amount
        if (value == null) {
            tag.remove(name)
            return
        }

        tag.putLong(name, value)
    }

    override fun loadNbt(tag: CompoundTag) {
        if (tag.contains(name)) getter()?.amount = tag.getLong(name)
    }
}
