package dev.tobynguyen27.sense.util

object BitUtils {

    /**
     * Get the [index]-th bit in [number]
     *
     * For example: Get bit at [index] = 3: `101010` -> 1
     *
     * **NOTE:** Returns 0 if the [index] is out of range
     */
    fun getBitAt(index: Int, number: Long): Long = (number shr index) and 1L

    /** Turn off the index-th in [number]. */
    fun turnOffBitAt(index: Int, number: Long): Long = number and (1L shl index).inv()

    /** Turn on the index-th in [number]. */
    fun turnOnBitAt(index: Int, number: Long): Long = number or (1L shl index)

    /** Fip the index-th in [number]. */
    fun flipBitAt(index: Int, number: Long): Long = number xor (1L shl index)
}
