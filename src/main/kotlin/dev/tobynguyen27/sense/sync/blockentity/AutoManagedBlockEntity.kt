package dev.tobynguyen27.sense.sync.blockentity

import dev.tobynguyen27.sense.sync.container.ManagedFieldContainer
import net.minecraft.world.level.block.entity.BlockEntity

interface AutoManagedBlockEntity {

    val self: BlockEntity
    val fieldContainer: ManagedFieldContainer
}
