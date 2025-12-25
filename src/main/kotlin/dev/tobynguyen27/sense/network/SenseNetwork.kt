package dev.tobynguyen27.sense.network

import dev.tobynguyen27.sense.sync.blockentity.AutoManagedBlockEntity
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs
import net.fabricmc.fabric.api.networking.v1.PlayerLookup
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking
import net.minecraft.nbt.CompoundTag
import net.minecraft.world.level.block.entity.BlockEntity

object SenseNetwork {

    fun sendSyncPacket(blockEntity: BlockEntity, tag: CompoundTag) {

        val buf = PacketByteBufs.create()
        buf.writeBlockPos(blockEntity.blockPos)
        buf.writeNbt(tag)

        PlayerLookup.tracking(blockEntity).forEach {
            ServerPlayNetworking.send(it, Packets.SYNC_DATA_PACKET, buf)
        }
    }

    fun handleSyncPacket() {
        ClientPlayNetworking.registerGlobalReceiver(Packets.SYNC_DATA_PACKET) { minecraft, _, buf, _
            ->
            val blockPos = buf.readBlockPos()
            val tag = buf.readNbt() ?: CompoundTag()

            minecraft.execute {
                val level = minecraft.level ?: return@execute
                val blockEntity = level.getBlockEntity(blockPos)

                if (blockEntity is AutoManagedBlockEntity) {
                    blockEntity.getFieldContainer().readSyncedFields(tag)
                }
            }
        }
    }
}
