package dev.tobynguyen27.sense.sync.container

import dev.tobynguyen27.sense.sync.accessor.Accessor
import dev.tobynguyen27.sense.sync.annotation.Permanent
import dev.tobynguyen27.sense.sync.annotation.Synced
import dev.tobynguyen27.sense.sync.registry.AccessorProviderRegistries
import java.util.concurrent.ConcurrentHashMap
import net.minecraft.nbt.CompoundTag

class ManagedFieldContainer(val owner: ManagedFieldAware) {
    companion object {
        val CONTAINER_CACHE = ConcurrentHashMap<Class<*>, List<ManagedField>>()

        fun getCachedFields(clazz: Class<*>): List<ManagedField> {
            return CONTAINER_CACHE.computeIfAbsent(clazz) {
                it.declaredFields
                    .onEach { field -> field.isAccessible = true }
                    .mapNotNull { field ->
                        val provider =
                            AccessorProviderRegistries.get(field) ?: return@mapNotNull null

                        val permanentAnnotation = field.getAnnotation(Permanent::class.java)
                        val syncedAnnotation = field.getAnnotation(Synced::class.java)

                        val hasPermanent = permanentAnnotation != null
                        val hasSynced = syncedAnnotation != null

                        if (!hasPermanent && !hasSynced) return@mapNotNull null

                        val name =
                            if (hasPermanent && permanentAnnotation.key.isNotEmpty())
                                permanentAnnotation.key
                            else field.name

                        ManagedField(
                            name,
                            field,
                            provider,
                            hasPermanent,
                            hasSynced
                        )
                    }
                    .toList()
            }
        }
    }

    val permanentFields = mutableListOf<Accessor>()
    val syncedFields = mutableListOf<Accessor>()

    init {
        val fields = getCachedFields(owner.javaClass)

        fields.forEach { field ->
            val accessor = field.provider.create(field.name, field.field, owner)
            if (field.isPersisted) permanentFields.add(accessor)
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

    fun savePermanentFields(tag: CompoundTag) {
        permanentFields.forEach { it.saveNbt(tag) }
    }

    fun loadPermanentFields(tag: CompoundTag) {
        permanentFields.forEach { it.loadNbt(tag) }
    }

    fun writeSyncFields(tag: CompoundTag) {
        syncedFields.forEach { it.saveNbt(tag) }
    }

    fun readSyncedFields(tag: CompoundTag) {
        syncedFields.forEach { it.loadNbt(tag) }
    }
}
