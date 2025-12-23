package dev.tobynguyen27.sense.sync.container

import dev.tobynguyen27.sense.Sense
import dev.tobynguyen27.sense.sync.accessor.Accessor
import dev.tobynguyen27.sense.sync.annotation.Permanent
import dev.tobynguyen27.sense.sync.registry.AccessorProviderRegistries
import java.util.concurrent.ConcurrentHashMap
import kotlin.sequences.ifEmpty
import net.minecraft.nbt.CompoundTag

class ManagedFieldContainer(val owner: ManagedFieldAware) {
    companion object {
        val CONTAINER_CACHE = ConcurrentHashMap<Class<*>, List<ManagedField>>()

        fun getCachedFields(clazz: Class<*>): List<ManagedField> {
            return CONTAINER_CACHE.computeIfAbsent(clazz) {
                it.declaredFields
                    .filter { field -> field.isAnnotationPresent(Permanent::class.java) }
                    .onEach { field -> field.isAccessible = true }
                    .mapNotNull { field ->
                        val annotation = field.getAnnotation(Permanent::class.java)
                        val name = annotation.key.ifEmpty { field.name }
                        val provider = AccessorProviderRegistries.get(field)

                        if (provider == null) {
                            Sense.LOGGER.error(
                                "There is no provider for field ${field.name} with type ${field.type.simpleName}"
                            )
                            null
                        } else {
                            ManagedField(name, field, provider)
                        }
                    }
                    .toList()
            }
        }
    }

    val managedFields = mutableListOf<Accessor>()

    init {
        val fields = getCachedFields(owner.javaClass)

        fields.forEach { field ->
            managedFields.add(field.provider.create(field.name, field.field, owner))
        }
    }

    fun savePermanentFields(tag: CompoundTag) {
        managedFields.forEach { it.saveNbt(tag) }
    }

    fun loadPermanentFields(tag: CompoundTag) {
        managedFields.forEach { it.loadNbt(tag) }
    }
}
