package world.cepi.shops

import net.minestom.server.command.CommandSender
import net.minestom.server.command.builder.Command
import net.minestom.server.entity.Player
import net.minestom.server.inventory.InventoryType
import net.minestom.server.item.ItemStack
import net.minestom.server.item.Material
import world.cepi.shops.menuapi.menu

class TestCmd : Command("menu") {
    init {
        setDefaultExecutor { sender: CommandSender, _ ->
            val player = sender as Player

            menu("Test", InventoryType.CHEST_1_ROW) {
                set(3..7, ItemStack(Material.ACACIA_BOAT, 1)) { player.sendMessage("test") }
            }.open(player)
        }
    }
}