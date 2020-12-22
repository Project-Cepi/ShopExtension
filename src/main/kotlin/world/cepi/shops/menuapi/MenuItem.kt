package world.cepi.shops.menuapi

import net.minestom.server.inventory.Inventory
import net.minestom.server.inventory.click.ClickType
import net.minestom.server.item.ItemStack
import java.util.function.Consumer

class MenuItem(val slot: Int, val item: ItemStack, val inv: Inventory) {
    fun onClick(consumer: Consumer<ClickType>, boolean: Boolean) {
        MenuListener().map[inv]?.set(slot, consumer)
        consumer.invoke
    }
}