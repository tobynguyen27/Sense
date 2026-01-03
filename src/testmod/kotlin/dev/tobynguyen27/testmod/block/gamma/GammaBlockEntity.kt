package dev.tobynguyen27.testmod.block.gamma

import dev.tobynguyen27.sense.sync.annotation.Persisted
import dev.tobynguyen27.sense.sync.annotation.Synced
import dev.tobynguyen27.sense.sync.blockentity.AutoPersistBlockEntity
import dev.tobynguyen27.sense.sync.blockentity.AutoSyncBlockEntity
import dev.tobynguyen27.sense.sync.container.ManagedFieldContainer
import dev.tobynguyen27.testmod.registry.BlockRegistry
import net.minecraft.core.BlockPos
import net.minecraft.network.protocol.Packet
import net.minecraft.network.protocol.game.ClientGamePacketListener
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import team.reborn.energy.api.base.SimpleEnergyStorage

class GammaBlockEntity(blockPos: BlockPos, blockState: BlockState) :
    BlockEntity(BlockRegistry.GAMMA_BLOCK_ENTITY, blockPos, blockState),
    AutoSyncBlockEntity,
    AutoPersistBlockEntity {

    @Synced
    @Persisted
    var energyStorage =
        object : SimpleEnergyStorage(100_000, 100_000, 100_000) {
            override fun onFinalCommit() {
                setChanged()
            }
        }

    override fun getUpdatePacket(): Packet<ClientGamePacketListener> {
        return ClientboundBlockEntityDataPacket.create(this)
    }

    override val self: BlockEntity = this
    override val fieldContainer: ManagedFieldContainer by lazy { ManagedFieldContainer(this) }
}
