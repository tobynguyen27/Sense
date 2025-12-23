package dev.tobynguyen27.sense.sync.annotation

import dev.tobynguyen27.sense.sync.SenseBlockEntity

/**
 * Apply this to a field in your [SenseBlockEntity] will make it saved and loaded NBT data
 * automatically.
 */
@Target(AnnotationTarget.FIELD) annotation class Permanent(val key: String = "")
