package dev.tobynguyen27.sense.sync.container

import dev.tobynguyen27.sense.sync.accessor.Accessor
import dev.tobynguyen27.sense.sync.annotation.Persisted
import dev.tobynguyen27.sense.sync.annotation.Synced
import dev.tobynguyen27.sense.sync.blockentity.AutoManagedBlockEntity
import dev.tobynguyen27.sense.sync.registry.AccessorProviderRegistries
import java.util.concurrent.ConcurrentHashMap
import net.minecraft.nbt.CompoundTag

class ManagedFieldContainer(val owner: AutoManagedBlockEntity) {
    companion object {
        val CONTAINER_CACHE = ConcurrentHashMap<Class<*>, List<ManagedField>>()

        fun getCachedFields(clazz: Class<*>): List<ManagedField> {
            return CONTAINER_CACHE.computeIfAbsent(clazz) {
                it.declaredFields
                    .onEach { field -> field.isAccessible = true }
                    .mapNotNull { field ->
                        val provider =
                            AccessorProviderRegistries.get(field) ?: return@mapNotNull null

                        val persistedAnnotation = field.getAnnotation(Persisted::class.java)
                        val syncedAnnotation = field.getAnnotation(Synced::class.java)

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

    init {
        val fields = getCachedFields(owner.javaClass)

        fields.forEach { field ->
            val accessor = field.provider.create(field.name, field.field, owner)
            if (field.isPersisted) persistedFields.add(accessor)
            if (field.isSynced) syncedFields.add(accessor)
        }
    }

    fun collectDirtyFields(): CompoundTag? {
        // We only return CompoundTag when we have at least 1 value changed. Minecraft doesn't
        // handle empty packet
        val tag = CompoundTag()
        var isDirty = false

        syncedFields.forEach {
            if (it.isChanged()) {
                it.saveNbt(tag)
                it.updateLastValue()
                isDirty = true
            }
        }

        return if (isDirty) {
            tag
        } else {
            null
        }
    }

    fun savePersistedFields(tag: CompoundTag) {
        persistedFields.forEach { it.saveNbt(tag) }
    }

    fun loadPersistedFields(tag: CompoundTag) {
        persistedFields.forEach { it.loadNbt(tag) }
    }

    fun saveSyncFields(tag: CompoundTag) {
        syncedFields.forEach { it.saveNbt(tag) }
    }

    fun loadSyncFields(tag: CompoundTag) {
        syncedFields.forEach { it.loadNbt(tag) }
    }
}
