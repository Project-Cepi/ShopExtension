package world.cepi.shops.menuapi

import net.minestom.server.MinecraftServer
import net.minestom.server.entity.Player
import net.minestom.server.event.inventory.InventoryClickEvent
import net.minestom.server.event.inventory.InventoryPreClickEvent
import net.minestom.server.inventory.Inventory
import net.minestom.server.inventory.click.ClickType
import java.util.function.Consumer

class MenuListener {

    val map = hashMapOf<Inventory, HashMap<Int, Consumer<ClickType>>>()

    fun register(playerInit: Player) {
        MinecraftServer.getGlobalEventHandler().addEventCallback(InventoryPreClickEvent::class.java) { event ->
            with(event) {
                m
            }
        }
    }
}