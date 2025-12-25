package dev.tobynguyen27.sense.sync.blockentity

import dev.tobynguyen27.sense.sync.container.ManagedFieldContainer
import net.minecraft.world.level.block.entity.BlockEntity

interface AutoManagedBlockEntity {

    fun getSelf(): BlockEntity

    fun getFieldContainer(): ManagedFieldContainer
}
