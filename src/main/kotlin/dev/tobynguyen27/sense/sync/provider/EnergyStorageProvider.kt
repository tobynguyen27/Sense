package dev.tobynguyen27.sense.sync.provider

import dev.tobynguyen27.sense.sync.accessor.Accessor
import dev.tobynguyen27.sense.sync.accessor.EnergyStorageAccessor
import dev.tobynguyen27.sense.sync.blockentity.AutoManagedBlockEntity
import kotlin.reflect.KMutableProperty
import team.reborn.energy.api.base.SimpleEnergyStorage

object EnergyStorageProvider : AccessorProvider {
    override fun isSupported(field: KMutableProperty<*>): Boolean {
        return field.returnType.classifier == SimpleEnergyStorage::class
    }

    override fun create(
        name: String,
        field: KMutableProperty<*>,
        owner: AutoManagedBlockEntity,
    ): Accessor {
        return EnergyStorageAccessor(name) { field.getter.call(owner) as? SimpleEnergyStorage }
    }
}
