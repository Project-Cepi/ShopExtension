package world.cepi.shops.menuapi

import net.minestom.server.inventory.Inventory
import net.minestom.server.inventory.InventoryType
import net.minestom.server.item.ItemStack

class Menu(val name: String, val type: InventoryType) {
    val inventory = Inventory(type, name)

    fun setItem(slot: Int, item: ItemStack) : MenuItem {
        this.inventory.setItemStack(slot, item)
        return MenuItem(slot, item, this)
    }

    fun toInventory() : Inventory {
        return this.inventory
    }
}