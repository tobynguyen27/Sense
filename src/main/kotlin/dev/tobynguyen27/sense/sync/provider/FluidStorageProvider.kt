package dev.tobynguyen27.sense.sync.provider

import dev.tobynguyen27.sense.sync.accessor.Accessor
import dev.tobynguyen27.sense.sync.accessor.FluidStorageAccessor
import dev.tobynguyen27.sense.sync.blockentity.AutoManagedBlockEntity
import kotlin.reflect.KMutableProperty
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleVariantStorage

object FluidStorageProvider : AccessorProvider {
    override fun isSupported(field: KMutableProperty<*>): Boolean {
        if (field.returnType.classifier != SingleVariantStorage::class) return false

        val genericType = field.returnType.arguments.firstOrNull()?.type
        return genericType?.classifier == FluidVariant::class
    }

    override fun create(
        name: String,
        field: KMutableProperty<*>,
        owner: AutoManagedBlockEntity,
    ): Accessor {
        return FluidStorageAccessor(name) {
            field.getter.call(owner) as? SingleVariantStorage<FluidVariant>
        }
    }
}
