package dev.tobynguyen27.sense.sync.provider

import dev.tobynguyen27.sense.sync.accessor.Accessor
import dev.tobynguyen27.sense.sync.accessor.PrimitiveAccessor
import java.lang.reflect.Field
import net.minecraft.nbt.CompoundTag

object PrimitiveProvider : AccessorProvider {

    private val READERS =
        mapOf<Class<*>, (CompoundTag, String) -> Any>(
            Int::class.java to { tag, name -> tag.getInt(name) }
        )

    private val WRITERS =
        mapOf<Class<*>, (CompoundTag, String, Any) -> Unit>(
            Int::class.java to { tag, name, value -> tag.putInt(name, value as Int) }
        )

    override fun isSupported(field: Field): Boolean {
        return READERS.contains(field.type) // should I also check writer ?
    }

    override fun create(name: String, field: Field, owner: Any): Accessor {
        val type = field.type

        return PrimitiveAccessor(
            name,
            { field.get(owner) },
            { field.set(owner, it) },
            READERS[type]!!,
            WRITERS[type]!!,
        )
    }
}
