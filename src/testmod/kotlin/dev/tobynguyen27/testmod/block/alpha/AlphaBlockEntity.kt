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

    @Permanent
    var alpha: Byte = 2

    @Permanent
    var beta: Short = 10

    @Permanent
    var gamma = 0

    @Permanent
    var delta = 0L

    @Permanent
    var epsilon = 0F

    @Permanent
    var zeta = 0.0

    @Permanent
    var eta = true

    @Permanent
    var theta = "Theta"

    override fun setChanged() {
        super.setChanged()

        println(alpha)
        println(beta)
        println(gamma)
        println(delta)
        println(epsilon)
        println(zeta)
        println(eta)
        println(theta)
    }

    override fun getFieldContainer(): ManagedFieldContainer = container
}
