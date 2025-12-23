package dev.tobynguyen27.sense.sync.provider

import dev.tobynguyen27.sense.sync.accessor.Accessor
import java.lang.reflect.Field

interface AccessorProvider {

    fun isSupported(field: Field): Boolean

    fun create(name: String, field: Field, owner: Any): Accessor
}
