package dev.tobynguyen27.testmod.block.beta

import dev.tobynguyen27.testmod.TestMod
import dev.tobynguyen27.testmod.registry.BlockRegistry
import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.minecraft.core.BlockPos
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.player.Player
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.BaseEntityBlock
import net.minecraft.world.level.block.RenderShape
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityTicker
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.material.Material
import net.minecraft.world.phys.BlockHitResult

class BetaBlock : BaseEntityBlock(FabricBlockSettings.of(Material.METAL)) {

    override fun use(
        state: BlockState,
        level: Level,
        pos: BlockPos,
        player: Player,
        hand: InteractionHand,
        hit: BlockHitResult,
    ): InteractionResult {
        val be = level.getBlockEntity(pos) as? BetaBlockEntity ?: return InteractionResult.FAIL
        if (!level.isClientSide) {
            be.alpha++
            be.setChanged()
        } else {
            //TestMod.LOGGER.info(be.alpha.toString())
        }

        return InteractionResult.SUCCESS
    }

    override fun newBlockEntity(pos: BlockPos, state: BlockState): BlockEntity {
        return BetaBlockEntity(pos, state)
    }

    override fun getRenderShape(state: BlockState): RenderShape {
        return RenderShape.MODEL
    }

    override fun <T : BlockEntity> getTicker(
        level: Level,
        state: BlockState,
        blockEntityType: BlockEntityType<T>,
    ): BlockEntityTicker<T>? {
        return if (level.isClientSide) {
            createTickerHelper(
                blockEntityType,
                BlockRegistry.BETA_BLOCK_ENTITY,
                BetaBlockLogical::clientTick,
            )
        } else {
            createTickerHelper(
                blockEntityType,
                BlockRegistry.BETA_BLOCK_ENTITY,
                BetaBlockLogical::serverTick,
            )
        }
    }
}
