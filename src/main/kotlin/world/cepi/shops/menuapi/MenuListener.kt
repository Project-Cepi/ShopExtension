package world.cepi.shops.menuapi

import net.minestom.server.MinecraftServer
import net.minestom.server.event.inventory.InventoryPreClickEvent
import net.minestom.server.inventory.click.ClickType

class MenuListener {


    fun register() {
        MinecraftServer.getGlobalEventHandler().addEventCallback(InventoryPreClickEvent::class.java) { event ->
            MenuItem.items.forEach {
                println("e" + (it.slot == event.slot))
                println("d" + it.menu.inventory.isViewer(event.player))
            }

            val item = MenuItem.items.firstOrNull { it.slot == event.slot && it.menu.inventory.isViewer(event.player) }

            if (item != null) {
                item.clickers.forEach { it.invoke(event.clickType) }
                event.isCancelled = true
            }
        }
    }
}