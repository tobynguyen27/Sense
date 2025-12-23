package dev.tobynguyen27.sense.sync.registry

import dev.tobynguyen27.sense.sync.provider.AccessorProvider
import dev.tobynguyen27.sense.sync.provider.PrimitiveProvider
import java.lang.reflect.Field
import java.util.concurrent.CopyOnWriteArrayList

object AccessorProviderRegistries {

    private val REGISTRIES = CopyOnWriteArrayList<AccessorProvider>()

    init {
        REGISTRIES.add(PrimitiveProvider)
    }

    fun get(field: Field): AccessorProvider? {
        return REGISTRIES.firstOrNull { it.isSupported(field) }
    }

    fun register(provider: AccessorProvider) = REGISTRIES.add(provider)
}
