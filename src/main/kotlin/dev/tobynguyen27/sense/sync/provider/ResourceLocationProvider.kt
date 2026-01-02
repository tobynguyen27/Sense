package dev.tobynguyen27.sense.sync.provider

import dev.tobynguyen27.sense.sync.accessor.Accessor
import dev.tobynguyen27.sense.sync.accessor.ResourceLocationAccessor
import dev.tobynguyen27.sense.sync.blockentity.AutoManagedBlockEntity
import kotlin.reflect.KMutableProperty
import net.minecraft.resources.ResourceLocation

object ResourceLocationProvider : AccessorProvider {
    override fun isSupported(field: KMutableProperty<*>): Boolean {
        return field.returnType.classifier == ResourceLocation::class
    }

    override fun create(
        name: String,
        field: KMutableProperty<*>,
        owner: AutoManagedBlockEntity,
    ): Accessor {
        return ResourceLocationAccessor(
            name,
            { field.getter.call(owner) as? ResourceLocation },
            { field.setter.call(owner, it) }
        )
    }
}
