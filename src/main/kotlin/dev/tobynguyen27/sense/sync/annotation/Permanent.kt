package dev.tobynguyen27.sense.sync.annotation

/**
 * Apply this will make your block entity saved and loaded NBT data
 * automatically.
 */
@Target(AnnotationTarget.FIELD) annotation class Permanent(val key: String = "")
