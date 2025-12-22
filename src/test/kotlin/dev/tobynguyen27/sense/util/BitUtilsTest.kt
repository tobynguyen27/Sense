package dev.tobynguyen27.sense.util

import dev.tobynguyen27.sense.util.BitUtils.flipBitAt
import dev.tobynguyen27.sense.util.BitUtils.getBitAt
import dev.tobynguyen27.sense.util.BitUtils.turnOffBitAt
import dev.tobynguyen27.sense.util.BitUtils.turnOnBitAt
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class BitUtilsTest {

    val bits = 50L // 110011

    @Test
    fun `test getBitAt`() {
        val expected = 1L

        assertEquals(expected, getBitAt(4, bits))
    }

    @Test
    fun `test turnOnBitAt`() {
        val expected = 58L

        assertEquals(expected, turnOnBitAt(3, bits))
    }

    @Test
    fun `test turnOffBitAt`() {
        val expected = 50L

        assertEquals(expected, turnOffBitAt(3, bits))
    }

    @Test
    fun `test flipBitAt`() {
        val expected = 51L

        assertEquals(expected, flipBitAt(0, bits))
    }
}
