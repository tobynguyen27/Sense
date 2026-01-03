package dev.tobynguyen27.testmod.block.gamma

import net.minecraft.core.BlockPos
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.state.BlockState

object GammaBlockLogical {

    fun serverTick(
        level: Level,
        blockPos: BlockPos,
        blockState: BlockState,
        blockEntity: GammaBlockEntity,
    ) {
        if (level.gameTime % 40 != 0L) return

        // TestMod.LOGGER.info(blockEntity.alpha.toString())
        blockEntity.energyStorage.amount++
        blockEntity.setChanged()

        blockEntity.syncTick()
    }

    fun clientTick(
        level: Level,
        blockPos: BlockPos,
        blockState: BlockState,
        blockEntity: GammaBlockEntity,
    ) {
        // if (level.gameTime % 40 != 0L) return

        // TestMod.LOGGER.info(blockEntity.alpha.toString())
    }
}
