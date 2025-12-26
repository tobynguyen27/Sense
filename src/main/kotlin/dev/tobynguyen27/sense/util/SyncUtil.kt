package dev.tobynguyen27.sense.util

import java.util.Objects
import net.minecraft.core.BlockPos
import net.minecraft.world.item.ItemStack

object SyncUtil {
    fun copy(value: Any?): Any? {
        if (value is ItemStack) return value.copy()
        if (value is BlockPos) return value.immutable()

        return value
    }

    fun isChanged(old: Any?, new: Any?): Boolean {
        if (old == null && new == null) return false
        if (old == null || new == null) return true

        if (old is ItemStack && new is ItemStack) return !ItemStack.matches(old, new)

        return !Objects.deepEquals(old, new)
    }
}
