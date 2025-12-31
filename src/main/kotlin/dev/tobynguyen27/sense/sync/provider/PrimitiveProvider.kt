package dev.tobynguyen27.sense.sync.provider

import dev.tobynguyen27.sense.sync.accessor.Accessor
import dev.tobynguyen27.sense.sync.accessor.PrimitiveAccessor
import dev.tobynguyen27.sense.sync.blockentity.AutoManagedBlockEntity
import kotlin.collections.get
import kotlin.reflect.KClass
import kotlin.reflect.KMutableProperty
import net.minecraft.nbt.CompoundTag

object PrimitiveProvider : AccessorProvider {

    private val READERS =
        mapOf<KClass<*>, (CompoundTag, String) -> Any?>(
            Byte::class to { tag, name -> tag.getByte(name) },
            Short::class to { tag, name -> tag.getShort(name) },
            Int::class to { tag, name -> tag.getInt(name) },
            Long::class to { tag, name -> tag.getLong(name) },
            Float::class to { tag, name -> tag.getFloat(name) },
            Double::class to { tag, name -> tag.getDouble(name) },
            Boolean::class to { tag, name -> tag.getBoolean(name) },
            String::class to { tag, name -> tag.getString(name) },
        )

    private val WRITERS =
        mapOf<KClass<*>, (CompoundTag, String, Any?) -> Unit>(
            Byte::class to
                { tag, name, value ->
                    if (value != null) tag.putByte(name, value as Byte)
                },
            Short::class to
                { tag, name, value ->
                    if (value != null) tag.putShort(name, value as Short)
                },
            Int::class to { tag, name, value -> if (value != null) tag.putInt(name, value as Int) },
            Long::class to
                { tag, name, value ->
                    if (value != null) tag.putLong(name, value as Long)
                },
            Float::class to
                { tag, name, value ->
                    if (value != null) tag.putFloat(name, value as Float)
                },
            Double::class to
                { tag, name, value ->
                    if (value != null) tag.putDouble(name, value as Double)
                },
            Boolean::class to
                { tag, name, value ->
                    if (value != null) tag.putBoolean(name, value as Boolean)
                },
            String::class to
                { tag, name, value ->
                    if (value != null) tag.putString(name, value as String)
                },
        )

    override fun isSupported(field: KMutableProperty<*>): Boolean {
        return READERS.contains(field.returnType.classifier)
    }

    override fun create(
        name: String,
        field: KMutableProperty<*>,
        owner: AutoManagedBlockEntity,
    ): Accessor {
        val type = field.returnType.classifier

        return PrimitiveAccessor(
            name,
            { field.getter.call(owner) },
            { field.setter.call(owner, it) },
            READERS[type]!!,
            WRITERS[type]!!,
        )
    }
}
