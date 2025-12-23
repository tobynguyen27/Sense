package dev.tobynguyen27.sense.sync.container

import dev.tobynguyen27.sense.sync.accessor.Accessor
import dev.tobynguyen27.sense.sync.accessor.IntAccessor
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
            val name = field.name
            val key = annotation.key.ifEmpty { name }

            when (type) {
                Int::class.java ->
                    managedFields.add(
                        IntAccessor(key, { field.get(owner) as Int }, { field.set(owner, it) })
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
