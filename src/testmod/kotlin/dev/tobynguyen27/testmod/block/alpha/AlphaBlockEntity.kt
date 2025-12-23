package dev.tobynguyen27.testmod.block.alpha

import dev.tobynguyen27.sense.sync.SenseBlockEntity
import dev.tobynguyen27.sense.sync.annotation.Permanent
import dev.tobynguyen27.sense.sync.container.ManagedFieldContainer
import dev.tobynguyen27.testmod.TestMod
import dev.tobynguyen27.testmod.registry.BlockRegistry
import net.minecraft.core.BlockPos
import net.minecraft.world.level.block.state.BlockState

class AlphaBlockEntity(blockPos: BlockPos, blockState: BlockState) :
    SenseBlockEntity(BlockRegistry.ALPHA_BLOCK_ENTITY, blockPos, blockState) {

    val container = ManagedFieldContainer(this)

    @Permanent var count = 0

    override fun setChanged() {
        super.setChanged()

        TestMod.LOGGER.info(count.toString())
    }

    override fun getFieldContainer(): ManagedFieldContainer = container
}
