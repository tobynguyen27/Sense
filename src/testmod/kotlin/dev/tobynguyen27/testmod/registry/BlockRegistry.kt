package dev.tobynguyen27.testmod.registry

import dev.tobynguyen27.testmod.block.alpha.AlphaBlock
import dev.tobynguyen27.testmod.block.alpha.AlphaBlockEntity
import dev.tobynguyen27.testmod.block.beta.BetaBlock
import dev.tobynguyen27.testmod.block.beta.BetaBlockEntity
import dev.tobynguyen27.testmod.block.gamma.GammaBlock
import dev.tobynguyen27.testmod.block.gamma.GammaBlockEntity
import dev.tobynguyen27.testmod.util.Identifier
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.core.Registry
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.CreativeModeTab
import net.minecraft.world.level.block.entity.BlockEntityType

object BlockRegistry {

    val ALPHA_BLOCK = Registry.register(Registry.BLOCK, Identifier("alpha_block"), AlphaBlock())
    val ALPHA_BLOCK_ITEM =
        Registry.register(
            Registry.ITEM,
            Identifier("alpha_block"),
            BlockItem(ALPHA_BLOCK, FabricItemSettings().group(CreativeModeTab.TAB_MISC)),
        )
    val ALPHA_BLOCK_ENTITY =
        Registry.register(
            Registry.BLOCK_ENTITY_TYPE,
            Identifier("alpha_block"),
            BlockEntityType.Builder.of<AlphaBlockEntity>(::AlphaBlockEntity, ALPHA_BLOCK)
                .build(null),
        )

    val BETA_BLOCK = Registry.register(Registry.BLOCK, Identifier("beta_block"), BetaBlock())
    val BETA_BLOCK_ITEM =
        Registry.register(
            Registry.ITEM,
            Identifier("beta_block"),
            BlockItem(BETA_BLOCK, FabricItemSettings().group(CreativeModeTab.TAB_MISC)),
        )
    val BETA_BLOCK_ENTITY =
        Registry.register(
            Registry.BLOCK_ENTITY_TYPE,
            Identifier("beta_block"),
            BlockEntityType.Builder.of<BetaBlockEntity>(::BetaBlockEntity, BETA_BLOCK).build(null),
        )

    val GAMMA_BLOCK = Registry.register(Registry.BLOCK, Identifier("gamma_block"), GammaBlock())
    val GAMMA_BLOCK_ITEM =
        Registry.register(
            Registry.ITEM,
            Identifier("gamma_block"),
            BlockItem(GAMMA_BLOCK, FabricItemSettings().group(CreativeModeTab.TAB_MISC)),
        )
    val GAMMA_BLOCK_ENTITY =
        Registry.register(
            Registry.BLOCK_ENTITY_TYPE,
            Identifier("gamma_block"),
            BlockEntityType.Builder.of<GammaBlockEntity>(::GammaBlockEntity, GAMMA_BLOCK)
                .build(null),
        )

    fun register() {}
}
