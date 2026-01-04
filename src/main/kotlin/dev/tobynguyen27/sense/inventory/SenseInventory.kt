package dev.tobynguyen27.sense.inventory

import net.minecraft.core.NonNullList
import net.minecraft.world.Container
import net.minecraft.world.ContainerHelper
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack

interface SenseInventory : Container {
    /**
     * Retrieves the item list of this inventory. Must return the same instance every time it's
     * called.
     */
    val items: NonNullList<ItemStack>

    /** The amount of slots in our container. */
    override fun getContainerSize(): Int = items.size

    /** Whether the container is considered empty. */
    override fun isEmpty(): Boolean = items.all { it.isEmpty }

    /** Return the item stack in the specified slot. */
    override fun getItem(slot: Int): ItemStack = items[slot]

    /**
     * Call this when changes are done to the container, i.e. when item stacks are added, modified,
     * or removed. For example, you could call BlockEntity#setChanged here.
     */
    override fun setChanged()

    /**
     * Remove the specified amount of items from the given slot, returning the stack that was just
     * removed.
     */
    override fun removeItem(slot: Int, amount: Int): ItemStack {
        val stack = ContainerHelper.removeItem(items, slot, amount)

        if (!stack.isEmpty) {
            setChanged()
        }

        return stack
    }

    /** Remove all items from the specified slot, returning the stack that was just removed. */
    override fun removeItemNoUpdate(slot: Int): ItemStack {
        val stack = ContainerHelper.takeItem(items, slot)

        setChanged()

        return stack
    }

    /**
     * Set the given item stack in the given slot. Limit to the max stack size of the container
     * first.
     */
    override fun setItem(slot: Int, stack: ItemStack) {
        items[slot] = stack

        if (stack.count > stack.maxStackSize) {
            stack.count = stack.maxStackSize
        }

        setChanged()
    }

    /** Clear the internal storage, setting all slots to empty again. */
    override fun clearContent() {
        items.clear()
        setChanged()
    }

    /** @return true if the player can use the inventory, false otherwise. */
    override fun stillValid(player: Player): Boolean = true
}
