package dev.tobynguyen27.sense.sync.provider

import dev.tobynguyen27.sense.sync.accessor.Accessor
import dev.tobynguyen27.sense.sync.accessor.BlockPosAccessor
import java.lang.invoke.MethodHandles
import java.lang.reflect.Field
import net.minecraft.core.BlockPos
import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.NbtUtils

object BlockPosProvider : AccessorProvider {

    private val LOOKUP = MethodHandles.lookup()

    override fun isSupported(field: Field): Boolean {
        return field.type == BlockPos::class.java
    }

    override fun create(name: String, field: Field, owner: Any): Accessor {
        val getter = LOOKUP.unreflectGetter(field).bindTo(owner)
        val setter = LOOKUP.unreflectSetter(field).bindTo(owner)

        return BlockPosAccessor(
            name,
            { getter.invoke() as? BlockPos },
            { setter.invoke(it) },
            { tag, name -> NbtUtils.readBlockPos(tag.get(name) as CompoundTag) },
            { tag, name, pos -> if (pos != null) tag.put(name, NbtUtils.writeBlockPos(pos)) },
        )
    }
}
