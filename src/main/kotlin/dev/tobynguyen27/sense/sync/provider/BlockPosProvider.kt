package dev.tobynguyen27.sense.sync.provider

import dev.tobynguyen27.sense.sync.accessor.Accessor
import dev.tobynguyen27.sense.sync.accessor.BlockPosAccessor
import dev.tobynguyen27.sense.sync.blockentity.AutoManagedBlockEntity
import kotlin.reflect.KMutableProperty
import net.minecraft.core.BlockPos
import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.NbtUtils

object BlockPosProvider : AccessorProvider {

    override fun isSupported(field: KMutableProperty<*>): Boolean {
        return field.returnType.classifier == BlockPos::class
    }

    override fun create(
        name: String,
        field: KMutableProperty<*>,
        owner: AutoManagedBlockEntity,
    ): Accessor {
        return BlockPosAccessor(
            name,
            { field.getter.call(owner) as? BlockPos },
            { field.setter.call(owner, it) },
            { tag, name -> NbtUtils.readBlockPos(tag.get(name) as CompoundTag) },
            { tag, name, pos -> if (pos != null) tag.put(name, NbtUtils.writeBlockPos(pos)) },
        )
    }
}
