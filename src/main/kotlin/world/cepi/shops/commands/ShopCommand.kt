package world.cepi.shops.commands

import net.minestom.server.chat.ChatColor
import net.minestom.server.command.CommandSender
import net.minestom.server.command.builder.Command
import net.minestom.server.command.builder.arguments.ArgumentType
import net.minestom.server.item.Material
import world.cepi.kstom.addSyntax
import world.cepi.kstom.arguments.asSubcommand
import world.cepi.shops.ShopObject.ActionType
import world.cepi.shops.ShopObject.Shop
import world.cepi.shops.ShopObject.ShopItem
import world.cepi.shops.menuapi.ItemBuilder

class ShopCommand: Command("shop") {

    companion object {
        val shops: MutableList<Shop> = mutableListOf()
    }

    init {
        val create = "create".asSubcommand()
        val addItem = "additem".asSubcommand()
        val edit = "edit".asSubcommand()
        val open = "open".asSubcommand()
        val shopName = ArgumentType.Word("shopID")
        val itemName = ArgumentType.Word("itemID")

        val existingShopName = ArgumentType.DynamicWord("shopname").fromRestrictions { shops.any { shop -> shop.name == it} }
        val add = "add".asSubcommand()
        val remove = "remove".asSubcommand()
        val delete = "delete".asSubcommand()

        addSyntax(create, shopName) { player, args ->
            for (s in shops) {
                if (args.get(shopName).equals(s.name)) {
                    player.sendMessage("${ChatColor.RED}A shop with that name already exists!")
                    return@addSyntax
                }
            }
            val shop = Shop(args.get(shopName))
            shops.add(shop)
            player.sendMessage("${ChatColor.BRIGHT_GREEN}Shop successfully created!")
        }
        addSyntax(delete, shopName) {player, args ->
            for (s in shops) {
                if (args.get(shopName).equals(s.name)) {
                    shops.remove(s)
                    player.sendMessage("${ChatColor.BRIGHT_GREEN}Shop successfully deleted!")
                    return@addSyntax
                }
            }
            player.sendMessage("${ChatColor.RED}A shop with that name does not exist!")
            return@addSyntax
        }
        addSyntax(open, shopName) {player, args ->
            val shop = shops.find {it.name == args.get(shopName) }
            shop?.render(player.asPlayer())
        }
        addSyntax(addItem, itemName, shopName) {player, args ->
            var shop = shops.find { it.name == args.get(shopName) }
            val item = ShopItem(ItemBuilder(Material.IRON_AXE).asItem(), 0, ActionType.BUY)
            if (shop == null) player.sendMessage("${ChatColor.RED}That shop does not exist!")
            shop?.items?.add(item)
            player.sendMessage(shop.toString())
        }
    }

    override fun onDynamicWrite(sender: CommandSender, text: String): Array<String> {
        return shops.map { it.name }.toTypedArray()
    }

}