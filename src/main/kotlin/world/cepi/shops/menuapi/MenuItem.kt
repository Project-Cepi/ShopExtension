package world.cepi.shops.menuapi

import net.minestom.server.inventory.click.ClickType
import net.minestom.server.item.ItemStack

class MenuItem(val slot: Int, val item: ItemStack, val menu: Menu) {
    fun onClick(consumer: (ClickType) -> Unit, boolean: Boolean = true) {
        MenuListener().map[this] = consumer
    }
}