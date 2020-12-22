package world.cepi.shops.menuapi

import net.minestom.server.command.CommandSender
import net.minestom.server.command.builder.Arguments
import net.minestom.server.command.builder.Command
import net.minestom.server.entity.Player
import net.minestom.server.inventory.InventoryType
import net.minestom.server.inventory.click.ClickType
import net.minestom.server.item.ItemStack
import net.minestom.server.item.Material

class TestCmd : Command("menu") {
    init {
        setDefaultExecutor { sender: CommandSender, args: Arguments ->
            val player = sender as Player
            val menu = Menu("Test", InventoryType.CHEST_1_ROW)
            val item = ItemStack(Material.ACACIA_BOAT, 1)
            menu.setItem(3, item).onClick({ click ->
                player.sendMessage("test")
            }, true)
            player.openInventory(menu.toInventory())
        }
    }
}