package world.cepi.shops.menuapi

import net.minestom.server.inventory.click.ClickType
import net.minestom.server.item.ItemStack

/** Represents an item in a menu -- interactable. */
class MenuItem(val slot: Int, val item: ItemStack, val menu: Menu) {

    val clickers: MutableList<(ClickType) -> Unit> = mutableListOf()

    fun onClick(consumer: (ClickType) -> Unit, boolean: Boolean = true) {
        clickers.add(consumer)
        items.add(this)
    }

    companion object {
        val items: MutableList<MenuItem> = mutableListOf()
    }

}