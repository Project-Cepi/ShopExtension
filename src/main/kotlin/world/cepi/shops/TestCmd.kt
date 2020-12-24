package world.cepi.shops

import net.minestom.server.command.CommandSender
import net.minestom.server.command.builder.Command
import net.minestom.server.entity.Player
import net.minestom.server.inventory.InventoryType
import net.minestom.server.item.ItemStack
import net.minestom.server.item.Material
import world.cepi.shops.menuapi.Menu
import world.cepi.shops.menuapi.openMenu

class TestCmd : Command("menu") {
    init {
        setDefaultExecutor { sender: CommandSender, _ ->
            val player = sender as Player
            val menu = Menu("Test", InventoryType.CHEST_1_ROW)
            val item = ItemStack(Material.ACACIA_BOAT, 1)
            menu.setItem(3, item).onClick {
                player.sendMessage("test")
            }
            player.openMenu(menu)
        }
    }
}