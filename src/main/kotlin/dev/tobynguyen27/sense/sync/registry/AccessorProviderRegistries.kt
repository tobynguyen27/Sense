package dev.tobynguyen27.sense.sync.registry

import dev.tobynguyen27.sense.sync.provider.AccessorProvider
import dev.tobynguyen27.sense.sync.provider.BlockPosProvider
import dev.tobynguyen27.sense.sync.provider.EnergyStorageProvider
import dev.tobynguyen27.sense.sync.provider.PrimitiveProvider
import dev.tobynguyen27.sense.sync.provider.ResourceLocationProvider
import java.util.concurrent.CopyOnWriteArrayList
import kotlin.reflect.KMutableProperty

object AccessorProviderRegistries {

    private val REGISTRIES = CopyOnWriteArrayList<AccessorProvider>()

    init {
        register(PrimitiveProvider)
        register(BlockPosProvider)
        register(ResourceLocationProvider)
        register(EnergyStorageProvider)
    }

    fun get(field: KMutableProperty<*>): AccessorProvider? {
        return REGISTRIES.firstOrNull { it.isSupported(field) }
    }

    fun register(provider: AccessorProvider) = REGISTRIES.add(provider)
}
