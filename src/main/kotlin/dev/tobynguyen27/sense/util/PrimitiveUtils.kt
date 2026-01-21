package dev.tobynguyen27.sense.util

object PrimitiveUtils {

    /** Returns `0` if **true** else `1` */
    fun Boolean.toInt() = if (this) 0 else 1

    /** Returns `true` if **0** else `false` */
    fun Int.toBoolean() = this == 0
}
