package world.cepi.shops.menuapi

import net.minestom.server.inventory.click.ClickType
import net.minestom.server.item.ItemStack

/** Represents an [ItemStack] in a menu -- intractable. */
class MenuItem(val slot: Int = 0, val item: ItemStack, val menu: Menu) {

    /** The internal clicker object */
    var clicker: MenuClicker = MenuClicker()

    /**
     * Register a lambda to run when an item is clicked
     *
     * @param consumer The consumer to use, takes in a ClickType
     * @param toCancel Whether to stop the player from picking up the item at this time
     */
    fun onClick(toCancel: Boolean = true, consumer: (ClickType) -> Unit): MenuItem {
        clicker = MenuClicker(consumer, toCancel)
        items.add(this)
        return this
    }

    companion object {

        /** A list of all the items registered with a clicker. */
        val items: MutableList<MenuItem> = mutableListOf()
    }

}