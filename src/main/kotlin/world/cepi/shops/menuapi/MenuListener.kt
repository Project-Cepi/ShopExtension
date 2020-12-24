package world.cepi.shops.menuapi

import net.minestom.server.MinecraftServer
import net.minestom.server.event.inventory.InventoryPreClickEvent
import net.minestom.server.inventory.click.ClickType

class MenuListener {

    val map = hashMapOf<MenuItem, (ClickType) -> Unit>()

    fun register() {
        MinecraftServer.getGlobalEventHandler().addEventCallback(InventoryPreClickEvent::class.java) { event ->
            val item = map.entries.firstOrNull { it.key.slot == event.slot && it.key.menu.inventory.isViewer(event.player) }

            if (item != null) {
                item.value.invoke(event.clickType)
                event.isCancelled = true
            }
        }
    }
}