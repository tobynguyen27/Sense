package dev.tobynguyen27.testmod.block.alpha

import dev.tobynguyen27.testmod.TestMod
import dev.tobynguyen27.testmod.registry.BlockRegistry
import net.minecraft.core.BlockPos
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState

class AlphaBlockEntity(blockPos: BlockPos, blockState: BlockState): BlockEntity(BlockRegistry.ALPHA_BLOCK_ENTITY, blockPos, blockState) {

    var count = 0

    override fun setChanged() {
        super.setChanged()

        TestMod.LOGGER.info(count.toString())
    }

}
