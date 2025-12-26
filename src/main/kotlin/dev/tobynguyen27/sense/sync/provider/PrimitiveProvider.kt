package dev.tobynguyen27.sense.sync.provider

import dev.tobynguyen27.sense.sync.accessor.Accessor
import dev.tobynguyen27.sense.sync.accessor.PrimitiveAccessor
import java.lang.invoke.MethodHandles
import java.lang.reflect.Field
import net.minecraft.nbt.CompoundTag

object PrimitiveProvider : AccessorProvider {

    private val READERS =
        mapOf<Class<*>, (CompoundTag, String) -> Any>(
            Byte::class.java to { tag, name -> tag.getByte(name) },
            Short::class.java to { tag, name -> tag.getShort(name) },
            Int::class.java to { tag, name -> tag.getInt(name) },
            Long::class.java to { tag, name -> tag.getLong(name) },
            Float::class.java to { tag, name -> tag.getFloat(name) },
            Double::class.java to { tag, name -> tag.getDouble(name) },
            Boolean::class.java to { tag, name -> tag.getBoolean(name) },
            String::class.java to { tag, name -> tag.getString(name) },
        )

    private val WRITERS =
        mapOf<Class<*>, (CompoundTag, String, Any) -> Unit>(
            Byte::class.java to { tag, name, value -> tag.putByte(name, value as Byte) },
            Short::class.java to { tag, name, value -> tag.putShort(name, value as Short) },
            Int::class.java to { tag, name, value -> tag.putInt(name, value as Int) },
            Long::class.java to { tag, name, value -> tag.putLong(name, value as Long) },
            Float::class.java to { tag, name, value -> tag.putFloat(name, value as Float) },
            Double::class.java to { tag, name, value -> tag.putDouble(name, value as Double) },
            Boolean::class.java to { tag, name, value -> tag.putBoolean(name, value as Boolean) },
            String::class.java to { tag, name, value -> tag.putString(name, value as String) },
        )

    private val LOOKUP = MethodHandles.lookup()

    override fun isSupported(field: Field): Boolean {
        return READERS.contains(field.type)
    }

    override fun create(name: String, field: Field, owner: Any): Accessor {
        val type = field.type
        val getter = LOOKUP.unreflectGetter(field).bindTo(owner)
        val setter = LOOKUP.unreflectSetter(field).bindTo(owner)

        return PrimitiveAccessor(
            name,
            { getter.invoke() },
            { setter.invoke(it) },
            READERS[type]!!,
            WRITERS[type]!!,
        )
    }
}
