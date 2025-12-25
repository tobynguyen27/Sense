package dev.tobynguyen27.testmod.block.beta

import net.minecraft.core.BlockPos
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.state.BlockState

object BetaBlockLogical {

    fun serverTick(
        level: Level,
        blockPos: BlockPos,
        blockState: BlockState,
        blockEntity: BetaBlockEntity,
    ) {
        if (level.gameTime % 40 != 0L) return

        // TestMod.LOGGER.info(blockEntity.alpha.toString())
        blockEntity.alpha++
        blockEntity.setChanged()

        blockEntity.syncTick()
    }

    fun clientTick(
        level: Level,
        blockPos: BlockPos,
        blockState: BlockState,
        blockEntity: BetaBlockEntity,
    ) {
        // if (level.gameTime % 40 != 0L) return

        // TestMod.LOGGER.info(blockEntity.alpha.toString())
    }
}
