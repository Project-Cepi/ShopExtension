package world.cepi.shops.menuapi

import net.minestom.server.entity.Player
import net.minestom.server.inventory.Inventory
import net.minestom.server.inventory.InventoryType
import net.minestom.server.inventory.click.ClickType
import net.minestom.server.item.ItemStack
import java.util.function.Consumer

class Menu(val name: String, val size: InventoryType) {
    val inventory = Inventory(size, name)
    init {
        MenuListener().map[inventory] = HashMap<Int, Consumer<ClickType>>()
    }

    fun setItem(slot: Int, item: ItemStack) : MenuItem{
        this.inventory.setItemStack(slot, item)
        return MenuItem(slot, item, this.inventory)
    }

    fun toInventory() : Inventory {
        return this.inventory
    }
}