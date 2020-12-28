package world.cepi.shops

import net.minestom.server.command.CommandSender
import net.minestom.server.command.builder.Command
import net.minestom.server.entity.Player
import net.minestom.server.inventory.InventoryType
import net.minestom.server.item.ItemStack
import net.minestom.server.item.Material
import world.cepi.shops.menuapi.menu
import world.cepi.shops.menuapi.openMenu

class TestCmd : Command("menu") {
    init {
        setDefaultExecutor { sender: CommandSender, _ ->
            val player = sender as Player

            menu("Test", InventoryType.CHEST_1_ROW) {
                set(3, Material.ACACIA_BOAT) { player.sendMessage("test") }
            }.open(player)
        }
    }
}