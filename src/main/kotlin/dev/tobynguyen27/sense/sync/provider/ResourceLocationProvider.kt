package dev.tobynguyen27.sense.sync.provider

import dev.tobynguyen27.sense.sync.accessor.Accessor
import dev.tobynguyen27.sense.sync.accessor.ResourceLocationAccessor
import java.lang.invoke.MethodHandles
import java.lang.reflect.Field
import net.minecraft.resources.ResourceLocation

object ResourceLocationProvider : AccessorProvider {
    override fun isSupported(field: Field): Boolean {
        return field.type == ResourceLocation::class.java
    }

    private val LOOKUP = MethodHandles.lookup()

    override fun create(name: String, field: Field, owner: Any): Accessor {
        val getter = LOOKUP.unreflectGetter(field).bindTo(owner)
        val setter = LOOKUP.unreflectSetter(field).bindTo(owner)

        return ResourceLocationAccessor(
            name,
            { getter.invoke() as? ResourceLocation },
            { setter.invoke(it) },
            { tag, name -> ResourceLocation(tag.getString(name)) },
            { tag, name, resourceLocation ->
                if (resourceLocation != null) tag.putString(name, resourceLocation.toString())
            },
        )
    }
}
