package world.cepi.shops.menuapi

import net.minestom.server.MinecraftServer
import net.minestom.server.event.inventory.InventoryCloseEvent
import net.minestom.server.event.inventory.InventoryPreClickEvent
import world.cepi.kstom.addEventCallback

object MenuListener {

    fun register() {
        MinecraftServer.getGlobalEventHandler().addEventCallback(InventoryPreClickEvent::class) { event ->
            val item = MenuItem.items.firstOrNull { it.slot == event.slot && it.menu.inventory.isViewer(event.player) }

            item?.clicker?.let {
                it.consumer.invoke(event.clickType)
                event.isCancelled = it.toCancel
            }
        }

        MinecraftServer.getGlobalEventHandler().addEventCallback(InventoryCloseEvent::class) { event ->

        }
    }

}