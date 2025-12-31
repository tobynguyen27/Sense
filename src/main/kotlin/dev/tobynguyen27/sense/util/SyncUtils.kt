package dev.tobynguyen27.sense.util

import java.util.Objects
import net.minecraft.core.BlockPos
import net.minecraft.world.item.ItemStack

object SyncUtils {
    fun copy(value: Any?): Any? = when(value) {
        is ItemStack -> value.copy()
        is BlockPos -> value.immutable()
        else -> value
    }

    fun isChanged(old: Any?, new: Any?): Boolean {
        if (old == null && new == null) return false
        if (old == null || new == null) return true

        if (old is ItemStack && new is ItemStack) return !ItemStack.matches(old, new)

        return !Objects.deepEquals(old, new)
    }
}
