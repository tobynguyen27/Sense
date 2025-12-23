package dev.tobynguyen27.testmod.block.beta

import dev.tobynguyen27.sense.sync.SenseBlockEntity
import dev.tobynguyen27.sense.sync.annotation.Permanent
import dev.tobynguyen27.sense.sync.annotation.Synced
import dev.tobynguyen27.sense.sync.container.ManagedFieldContainer
import dev.tobynguyen27.testmod.TestMod
import dev.tobynguyen27.testmod.registry.BlockRegistry
import net.minecraft.core.BlockPos
import net.minecraft.world.level.block.state.BlockState

class BetaBlockEntity(blockPos: BlockPos, blockState: BlockState) :
    SenseBlockEntity(BlockRegistry.BETA_BLOCK_ENTITY, blockPos, blockState) {

    val container = ManagedFieldContainer(this)

    @Synced @Permanent var alpha = 0

    override fun setChanged() {
        super.setChanged()

        TestMod.LOGGER.info(alpha.toString())
    }

    override fun getFieldContainer(): ManagedFieldContainer = container
}
