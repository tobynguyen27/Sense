package dev.tobynguyen27.testmod.block.alpha

import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.minecraft.core.BlockPos
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.player.Player
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.BaseEntityBlock
import net.minecraft.world.level.block.RenderShape
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.material.Material
import net.minecraft.world.phys.BlockHitResult

class AlphaBlock : BaseEntityBlock(FabricBlockSettings.of(Material.METAL)) {

    override fun use(
        state: BlockState,
        level: Level,
        pos: BlockPos,
        player: Player,
        hand: InteractionHand,
        hit: BlockHitResult,
    ): InteractionResult {
        if (!level.isClientSide) {
            val be = level.getBlockEntity(pos) as? AlphaBlockEntity ?: return InteractionResult.FAIL

            with(be) {
                alpha++
                beta++
                gamma++
                delta++
                epsilon++
                zeta++
                eta = !eta
                theta += theta
                testPos = testPos.offset(1.0, 1.0, 1.0)

                testRL =
                    if (testRL == null) ResourceLocation("init", "init")
                    else ResourceLocation("init", alpha.toString())

                setChanged()
            }
        }

        return InteractionResult.SUCCESS
    }

    override fun newBlockEntity(pos: BlockPos, state: BlockState): BlockEntity {
        return AlphaBlockEntity(pos, state)
    }

    override fun getRenderShape(state: BlockState): RenderShape {
        return RenderShape.MODEL
    }
}
