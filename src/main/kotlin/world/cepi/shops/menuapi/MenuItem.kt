package world.cepi.shops.menuapi

import net.minestom.server.inventory.click.ClickType
import net.minestom.server.item.ItemStack

/** Represents an item in a menu -- interactable. */
class MenuItem(val slot: Int, val item: ItemStack, val menu: Menu) {

    /** The internal clicker object */
    var clicker: MenuClicker? = null

    /**
     * Register a lambda to run when an item is clicked
     *
     * @param consumer The consumer to use, takes in a ClickType
     * @param toCancel Whether to stop the player from picking up the item at this time
     */
    fun onClick(consumer: (ClickType) -> Unit, toCancel: Boolean = true) {
        clicker = MenuClicker(consumer, toCancel)
        items.add(this)
    }

    /**
     * Register a lambda to run when an item is clicked
     *
     * @param consumer The consumer to use, takes in a ClickType
     */
    fun onClick(consumer: (ClickType) -> Unit) {
        clicker = MenuClicker(consumer)
        items.add(this)
    }

    companion object {

        /** A list of all the items registered with a clicker. */
        val items: MutableList<MenuItem> = mutableListOf()
    }

}