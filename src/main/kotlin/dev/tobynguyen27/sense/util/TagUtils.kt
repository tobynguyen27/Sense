package dev.tobynguyen27.sense.util

import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.tags.TagKey

object TagUtils {
    fun <T> createCommonTag(name: String): TagKey<T> {
        return TagKey.create(
            ResourceKey.createRegistryKey<T>(ResourceLocation("c", name)),
            ResourceLocation("c", name),
        )
    }

    fun <T> createTag(id: String, name: String): TagKey<T> {
        return TagKey.create(
            ResourceKey.createRegistryKey<T>(ResourceLocation(id, name)),
            ResourceLocation(id, name),
        )
    }
}
