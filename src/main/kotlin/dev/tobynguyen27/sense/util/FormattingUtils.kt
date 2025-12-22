package dev.tobynguyen27.sense.util

import java.util.Locale
import java.util.Locale.getDefault

object FormattingUtils {

    /**
     * Convert [string] to a readable string.
     *
     * `iron_ingot` to `Iron Ingot`
     */
    fun toEnglishName(string: String, locale: Locale = getDefault()): String {
        return string.lowercase(locale).split("_").joinToString(" ") {
            it.replaceFirstChar { c -> c.uppercaseChar() }
        }
    }

    fun capitalizeFirstLetter(string: String): String {
        return string.replaceFirstChar { it.uppercase() }
    }

    fun decapitalizeFirstLetter(string: String): String {
        return string.replaceFirstChar { it.lowercase() }
    }
}
