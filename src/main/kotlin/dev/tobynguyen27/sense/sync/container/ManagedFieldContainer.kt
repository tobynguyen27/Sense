package dev.tobynguyen27.sense.sync.container

import dev.tobynguyen27.sense.Sense
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
                            AccessorProviderRegistries.get(field)
                                ?: run {
                                    Sense.LOGGER.error(
                                        "There is no provider for field ${field.name} with type ${field.type.simpleName}"
                                    )
                                    return@mapNotNull null
                                }

                        val permanentAnnotation = field.getAnnotation(Permanent::class.java)
                        val syncedAnnotation = field.getAnnotation(Synced::class.java)

                        val hasPermanent = permanentAnnotation != null
                        val hasSynced = syncedAnnotation != null

                        if(!hasPermanent && !hasSynced) return@mapNotNull null

                        val name =
                            if (hasPermanent && permanentAnnotation.key.isNotEmpty())
                                permanentAnnotation.key
                            else field.name

                        ManagedField(
                            name,
                            field,
                            provider,
                            ManagedFieldFlags.Builder.apply {
                                    if (hasPermanent) with(ManagedFieldType.PERMANENT)
                                    if (hasSynced) with(ManagedFieldType.SYNCED)
                                }
                                .build(),
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
            if (field.types.has(ManagedFieldType.PERMANENT))
                permanentFields.add(field.provider.create(field.name, field.field, owner))
            if (field.types.has(ManagedFieldType.SYNCED))
                syncedFields.add(field.provider.create(field.name, field.field, owner))
        }
    }

    fun savePermanentFields(tag: CompoundTag) {
        permanentFields.forEach { it.saveNbt(tag) }
    }

    fun loadPermanentFields(tag: CompoundTag) {
        permanentFields.forEach { it.loadNbt(tag) }
    }
}
