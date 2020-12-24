package world.cepi.shops.menuapi

import net.minestom.server.entity.Player
import net.minestom.server.inventory.Inventory
import net.minestom.server.inventory.InventoryType
import net.minestom.server.item.ItemStack

/** Wrapper class for Inventory. Holds MenuItems. */
class Menu(
    /** The name of the Menu -- displays to the player*/
    val name: String,

    /** The type of the Menu, barrel, chest with rows, etc. */
    val type: InventoryType
) {

    /** The internal inventory object of the Menu */
    val inventory = Inventory(type, name)

    /**
     * Sets an item at a position in the inventory.
     *
     * @param slot The slot to set the item at
     * @param item The item to put in that slot
     *
     * @return A MenuItem which is intractable.
     */
    fun setItem(slot: Int, item: ItemStack) : MenuItem {
        this.inventory.setItemStack(slot, item)
        return MenuItem(slot, item, this)
    }
}

fun Player.openMenu(menu: Menu) = this.openInventory(menu.inventory)