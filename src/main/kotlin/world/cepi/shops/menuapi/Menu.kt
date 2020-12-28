package world.cepi.shops.menuapi

import net.minestom.server.entity.Player
import net.minestom.server.inventory.Inventory
import net.minestom.server.inventory.InventoryType
import net.minestom.server.item.ItemStack
import net.minestom.server.item.Material

/** Wrapper class for Inventory. Holds MenuItems. */
class Menu(
    /** The name of the Menu -- displays to the player*/
    name: String = "Chest",

    /** The type of the Menu, barrel, chest with rows, etc. */
    type: InventoryType = InventoryType.CHEST_3_ROW
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
    fun set(slot: Int, item: ItemStack) : MenuItem {
        this.inventory.setItemStack(slot, item)
        return MenuItem(slot, item, this)
    }

    /**
     * Sets an item at a position in the inventory.
     *
     * @param slot The slot to set the item at
     * @param material The material to set at the slot
     *
     * @return A MenuItem which is intractable.
     */
    fun set(slot: Int, material: Material) : MenuItem {
        this.inventory.setItemStack(slot, ItemStack(material, 1))
        return MenuItem(slot, ItemStack(material, 1), this)
    }
}

fun menu(name: String, type: InventoryType, init: Menu.() -> Unit): Menu {
    val shape = Menu(name, type)
    shape.init()

    return shape
}

fun Player.openMenu(menu: Menu) = this.openInventory(menu.inventory)