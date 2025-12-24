package dev.tobynguyen27.sense.sync

import dev.tobynguyen27.sense.network.SenseNetwork
import dev.tobynguyen27.sense.sync.container.ManagedFieldAware
import net.minecraft.core.BlockPos
import net.minecraft.nbt.CompoundTag
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState

abstract class SenseBlockEntity(
    blockEntityType: BlockEntityType<*>,
    blockPos: BlockPos,
    blockState: BlockState,
) : BlockEntity(blockEntityType, blockPos, blockState), ManagedFieldAware {

    fun syncTick() {
        val tag = getFieldContainer().collectDirtyFields()

        if (tag != null) {
            SenseNetwork.sendSyncPacket(this, tag)
        }
    }

    override fun saveAdditional(tag: CompoundTag) {
        getFieldContainer().savePermanentFields(tag)
        super.saveAdditional(tag)
    }

    override fun load(tag: CompoundTag) {
        getFieldContainer().loadPermanentFields(tag)
        super.load(tag)
    }
}
