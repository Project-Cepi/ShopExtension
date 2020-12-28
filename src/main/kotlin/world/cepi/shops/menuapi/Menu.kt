package world.cepi.shops.menuapi

import net.minestom.server.entity.Player
import net.minestom.server.inventory.Inventory
import net.minestom.server.inventory.InventoryType
import net.minestom.server.inventory.click.ClickType
import net.minestom.server.item.ItemStack
import net.minestom.server.item.Material

/** Wrapper class for [Inventory]. Holds [MenuItem]. */
class Menu(
    /** The name of the [Menu] -- displays to the player*/
    name: String = "Chest",

    /** The type of the Menu, barrel, chest with rows, etc. */
    type: InventoryType = InventoryType.CHEST_3_ROW
) {

    /** The internal inventory object of the Menu */
    val inventory = Inventory(type, name)

    /**
     * Sets an item at a position in the [Menu].
     *
     * @param slot The slot to set the item at
     * @param item The item to put in that slot
     * @param onClick Lambda that triggers the user clicks on the MenuItem.
     *
     * @return A MenuItem which is intractable.
     */
    fun set(slot: Int, item: ItemStack, onClick: (ClickType) -> Unit = { }) : MenuItem {
        this.inventory.setItemStack(slot, item)
        return MenuItem(slot, item, this).onClick(consumer = onClick)
    }

    /**
     * Sets an item at a position in the [Menu].
     *
     * @param slot The slot to set the item at
     * @param material The material to set at the slot
     * @param onClick Lambda that triggers the user clicks on the MenuItem.
     *
     * @return A MenuItem which is intractable.
     */
    fun set(slot: Int, material: Material, onClick: (ClickType) -> Unit = { }) : MenuItem {
        this.inventory.setItemStack(slot, ItemStack(material, 1))
        return MenuItem(slot, ItemStack(material, 1), this).onClick(consumer = onClick)
    }

    fun open(vararg players: Player) {
        players.forEach { it.openMenu(this) }
    }
}

/**
 * Lambda with resolver invoke method for creating menus
 *
 * @param name The name of the menu.
 * @param type The type of the menu.
 *
 * @return A [Menu] object
 */
inline fun menu(name: String = "Chest", type: InventoryType = InventoryType.CHEST_3_ROW, init: Menu.() -> Unit = { }): Menu {
    val menu = Menu(name, type)
    menu.init()

    return menu
}

fun Player.openMenu(menu: Menu) = this.openInventory(menu.inventory)