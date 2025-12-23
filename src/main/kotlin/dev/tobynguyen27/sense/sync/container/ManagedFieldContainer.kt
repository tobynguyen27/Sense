package dev.tobynguyen27.sense.sync.container

import dev.tobynguyen27.sense.sync.accessor.Accessor
import dev.tobynguyen27.sense.sync.accessor.PrimitiveAccessor
import dev.tobynguyen27.sense.sync.annotation.Permanent
import java.lang.reflect.Field
import java.util.concurrent.ConcurrentHashMap
import net.minecraft.nbt.CompoundTag

class ManagedFieldContainer(val owner: ManagedFieldAware) {
    companion object {
        val CONTAINER_CACHE = ConcurrentHashMap<Class<*>, List<Field>>()

        fun getCachedFields(clazz: Class<*>): List<Field> {
            return CONTAINER_CACHE.computeIfAbsent(clazz) {
                it.declaredFields
                    .filter { field -> field.isAnnotationPresent(Permanent::class.java) }
                    .onEach { field -> field.isAccessible = true }
                    .toList()
            }
        }
    }

    val managedFields = mutableListOf<Accessor>()

    init {
        val fields = getCachedFields(owner.javaClass)

        fields.forEach { field ->
            val annotation = field.getAnnotation(Permanent::class.java)
            val type = field.type
            val name = annotation.key.ifEmpty { field.name }

            @Suppress("UNCHECKED_CAST")
            fun <T> registerPrimitiveAccessor(
                name: String,
                reader: (CompoundTag, String) -> T,
                writer: (CompoundTag, String, T) -> Unit,
            ): PrimitiveAccessor<T> {
                return PrimitiveAccessor(
                    name,
                    { field.get(owner) as T },
                    { field.set(owner, it) },
                    reader,
                    writer,
                )
            }

            when (type) {
                Int::class.java ->
                    managedFields.add(
                        registerPrimitiveAccessor(name, CompoundTag::getInt, CompoundTag::putInt)
                    )
                Long::class.java ->
                    managedFields.add(
                        registerPrimitiveAccessor(name, CompoundTag::getLong, CompoundTag::putLong)
                    )
                Float::class.java ->
                    managedFields.add(
                        registerPrimitiveAccessor(
                            name,
                            CompoundTag::getFloat,
                            CompoundTag::putFloat,
                        )
                    )
                Double::class.java ->
                    managedFields.add(
                        registerPrimitiveAccessor(
                            name,
                            CompoundTag::getDouble,
                            CompoundTag::putDouble,
                        )
                    )
            }
        }
    }

    fun savePermanentFields(tag: CompoundTag) {
        managedFields.forEach { it.saveNbt(tag) }
    }

    fun loadPermanentFields(tag: CompoundTag) {
        managedFields.forEach { it.loadNbt(tag) }
    }
}
