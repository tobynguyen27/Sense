package dev.tobynguyen27.sense.block

import net.minecraft.world.level.block.BaseEntityBlock
import net.minecraft.world.level.block.RenderShape
import net.minecraft.world.level.block.state.BlockState

abstract class BlockWithEntity(properties: Properties) : BaseEntityBlock(properties) {

    override fun getRenderShape(state: BlockState): RenderShape = RenderShape.MODEL
}
