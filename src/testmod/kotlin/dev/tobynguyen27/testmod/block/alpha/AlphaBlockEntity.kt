package dev.tobynguyen27.testmod.block.alpha

import dev.tobynguyen27.sense.sync.annotation.Persisted
import dev.tobynguyen27.sense.sync.blockentity.AutoPersistBlockEntity
import dev.tobynguyen27.sense.sync.container.ManagedFieldContainer
import dev.tobynguyen27.testmod.registry.BlockRegistry
import net.minecraft.core.BlockPos
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState

class AlphaBlockEntity(blockPos: BlockPos, blockState: BlockState) :
    BlockEntity(BlockRegistry.ALPHA_BLOCK_ENTITY, blockPos, blockState), AutoPersistBlockEntity {

    override val self: BlockEntity = this
    override val fieldContainer: ManagedFieldContainer by lazy { ManagedFieldContainer(this) }

    @Persisted var alpha: Byte = 2

    @Persisted var beta: Short = 10

    @Persisted var gamma = 0

    @Persisted var delta = 0L

    @Persisted var epsilon = 0F

    @Persisted var zeta = 0.0

    @Persisted var eta = true

    @Persisted var theta = "Theta"

    @Persisted var testPos: BlockPos = BlockPos.ZERO

    @Persisted var testRL: ResourceLocation? = null

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
        println(testPos.toString())
        println(testRL.toString())
    }
}
