package dev.tobynguyen27.sense.sync.container

import dev.tobynguyen27.sense.sync.provider.AccessorProvider
import java.lang.reflect.Field

data class ManagedField(
    val name: String,
    val field: Field,
    val provider: AccessorProvider,
    val isPersisted: Boolean,
    val isSynced: Boolean,
) {}
