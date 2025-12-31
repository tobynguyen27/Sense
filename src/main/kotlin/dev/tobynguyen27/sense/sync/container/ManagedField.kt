package dev.tobynguyen27.sense.sync.container

import dev.tobynguyen27.sense.sync.provider.AccessorProvider
import kotlin.reflect.KMutableProperty

data class ManagedField(
    val name: String,
    val field: KMutableProperty<*>,
    val provider: AccessorProvider,
    val isPersisted: Boolean,
    val isSynced: Boolean,
) {}
