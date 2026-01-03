package dev.tobynguyen27.sense.sync.container

import dev.tobynguyen27.sense.sync.accessor.Accessor
import dev.tobynguyen27.sense.sync.annotation.Persisted
import dev.tobynguyen27.sense.sync.annotation.Synced
import dev.tobynguyen27.sense.sync.blockentity.AutoManagedBlockEntity
import dev.tobynguyen27.sense.sync.registry.AccessorProviderRegistries
import java.util.concurrent.ConcurrentHashMap
import kotlin.reflect.KClass
import kotlin.reflect.KMutableProperty
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.memberProperties
import net.minecraft.nbt.CompoundTag

class ManagedFieldContainer(val owner: AutoManagedBlockEntity) {
    companion object {
        val CONTAINER_CACHE = ConcurrentHashMap<KClass<*>, List<ManagedField>>()

        fun getCachedFields(clazz: KClass<*>): List<ManagedField> {
            return CONTAINER_CACHE.computeIfAbsent(clazz) {
                it.memberProperties
                    .filterIsInstance<KMutableProperty<*>>()
                    .mapNotNull { field ->
                        val provider =
                            AccessorProviderRegistries.get(field) ?: return@mapNotNull null

                        val persistedAnnotation = field.findAnnotation<Persisted>()
                        val syncedAnnotation = field.findAnnotation<Synced>()

                        val hasPersisted = persistedAnnotation != null
                        val hasSynced = syncedAnnotation != null

                        if (!hasPersisted && !hasSynced) return@mapNotNull null

                        val name =
                            if (hasPersisted && persistedAnnotation.key.isNotEmpty())
                                persistedAnnotation.key
                            else field.name

                        ManagedField(name, field, provider, hasPersisted, hasSynced)
                    }
                    .toList()
            }
        }
    }

    val persistedFields = mutableListOf<Accessor>()
    val syncedFields = mutableListOf<Accessor>()
    val nonPersistedSyncedFields = mutableListOf<Accessor>()

    init {
        val fields = getCachedFields(owner::class)

        fields.forEach { field ->
            val accessor = field.provider.create(field.name, field.field, owner)
            if (field.isPersisted) persistedFields.add(accessor)
            if (field.isSynced) syncedFields.add(accessor)
            if (field.isSynced && !field.isPersisted) nonPersistedSyncedFields.add(accessor)
        }
    }

    fun collectDirtyFields(): CompoundTag? {
        // We only return CompoundTag when we have at least 1 value changed. Minecraft doesn't
        // handle empty packet
        val dirtyFields = syncedFields.filter { it.isChanged() }
        if (dirtyFields.isEmpty()) return null

        val tag = CompoundTag()

        dirtyFields.forEach {
            it.saveNbt(tag)
            it.updateLastValue()
        }

        return tag
    }
}
