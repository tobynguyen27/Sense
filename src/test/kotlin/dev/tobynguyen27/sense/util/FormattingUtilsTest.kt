package dev.tobynguyen27.sense.util

import dev.tobynguyen27.sense.util.FormattingUtils.capitalizeFirstLetter
import dev.tobynguyen27.sense.util.FormattingUtils.decapitalizeFirstLetter
import dev.tobynguyen27.sense.util.FormattingUtils.toEnglishName
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class FormattingUtilsTest {

    @Test
    fun `test toEnglishName`() {
        val expected = "Hello World"

        assertEquals(expected, toEnglishName("hello_world"))
    }

    @Test
    fun `test capitalizeFirstLetter`() {
        val expected = "Hello"

        assertEquals(expected, capitalizeFirstLetter("Hello"))
    }

    @Test
    fun `test decapitalizeFirstLetter`() {
        val expected = "hello"
        assertEquals(expected, decapitalizeFirstLetter("Hello"))
    }
}
