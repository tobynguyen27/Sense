package dev.tobynguyen27.sense.sync.accessor

import dev.tobynguyen27.sense.util.SyncUtils
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleVariantStorage
import net.minecraft.nbt.CompoundTag

class FluidStorageAccessor(
    val name: String,
    val getter: () -> SingleVariantStorage<FluidVariant>?,
) : Accessor {

    companion object {
        private const val AMOUNT_TAG = "amount"
        private const val VARIANT_TAG = "variant"
    }

    private fun storage() = getter()

    private var lastValue = SyncUtils.copy(storage()?.amount)
    private var lastFluid = SyncUtils.copy(storage()?.variant)

    override fun isChanged(): Boolean {
        val storage = storage()

        return SyncUtils.isChanged(lastValue, storage?.amount) ||
            SyncUtils.isChanged(lastFluid, storage?.variant)
    }

    override fun updateLastValue() {
        val storage = storage()

        lastValue = SyncUtils.copy(storage?.amount)
        lastFluid = SyncUtils.copy(storage?.variant)
    }

    override fun saveNbt(tag: CompoundTag) {
        val storage = storage()
        val value = storage?.amount
        val fluid = storage?.variant

        if (value == null || fluid == null) {
            tag.remove(name)
            return
        }

        val t = CompoundTag()
        t.putLong(AMOUNT_TAG, value)
        t.put(VARIANT_TAG, fluid.toNbt())

        tag.put(name, t)
    }

    override fun loadNbt(tag: CompoundTag) {
        if (tag.contains(name)) {
            val t = tag.getCompound(name)
            val storage = storage()

            if (storage != null) {
                storage.amount = t.getLong(AMOUNT_TAG)
                storage.variant = FluidVariant.fromNbt(t.getCompound(VARIANT_TAG))
            }
        }
    }
}
