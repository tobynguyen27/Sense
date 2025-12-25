package dev.tobynguyen27.testmod.block.beta

import dev.tobynguyen27.sense.sync.annotation.Persisted
import dev.tobynguyen27.sense.sync.annotation.Synced
import dev.tobynguyen27.sense.sync.blockentity.AutoSyncBlockEntity
import dev.tobynguyen27.sense.sync.container.ManagedFieldContainer
import dev.tobynguyen27.testmod.registry.BlockRegistry
import net.minecraft.core.BlockPos
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.protocol.Packet
import net.minecraft.network.protocol.game.ClientGamePacketListener
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState

class BetaBlockEntity(blockPos: BlockPos, blockState: BlockState) :
    BlockEntity(BlockRegistry.BETA_BLOCK_ENTITY, blockPos, blockState), AutoSyncBlockEntity {

    val container = ManagedFieldContainer(this)

    @Synced @Persisted var alpha = 0

    override fun setChanged() {
        super.setChanged()

        // TestMod.LOGGER.info(alpha.toString())
    }

    override fun getUpdatePacket(): Packet<ClientGamePacketListener> {
        return ClientboundBlockEntityDataPacket.create(this)
    }

    override fun getUpdateTag(): CompoundTag {
        val tag = CompoundTag()
        getFieldContainer().saveSyncFields(tag)
        return tag
    }

    override fun getSelf(): BlockEntity {
        return this
    }

    override fun getFieldContainer(): ManagedFieldContainer = container
}
