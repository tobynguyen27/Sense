package dev.tobynguyen27.sense.sync.container

class ManagedFieldFlags(val flags: Int) {

    fun has(type: ManagedFieldType): Boolean {
        return (flags and (1 shl type.value) != 0)
    }

    object Builder {
        var flags = 0

        fun with(type: ManagedFieldType): Builder {
            flags = flags or (1 shl type.value)
            return this
        }

        fun with(vararg types: ManagedFieldType): Builder {
            types.forEach { with(it) }

            return this
        }

        fun build(): ManagedFieldFlags {
            return ManagedFieldFlags(flags)
        }
    }
}
