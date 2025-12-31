package dev.tobynguyen27.sense.sync.provider

import dev.tobynguyen27.sense.sync.accessor.Accessor
import dev.tobynguyen27.sense.sync.blockentity.AutoManagedBlockEntity
import kotlin.reflect.KMutableProperty

interface AccessorProvider {

    fun isSupported(field: KMutableProperty<*>): Boolean

    fun create(name: String, field: KMutableProperty<*>, owner: AutoManagedBlockEntity): Accessor
}
