package world.cepi.shops.commands

import net.minestom.server.chat.ChatColor
import net.minestom.server.command.CommandSender
import net.minestom.server.command.builder.Command
import net.minestom.server.command.builder.arguments.ArgumentType
import net.minestom.server.entity.Player
import world.cepi.itemextension.item.Item
import world.cepi.itemextension.item.checkIsItem
import world.cepi.kstom.command.arguments.asSubcommand
import world.cepi.kstom.command.addSyntax
import world.cepi.shops.shop.Shop
import world.cepi.shops.shop.ShopItem

object ShopCommand: Command("shop") {

    val shops: MutableList<Shop> = mutableListOf()

    init {
        val create = "create".asSubcommand()
        val addItem = "additem".asSubcommand()
        val edit = "edit".asSubcommand()
        val open = "open".asSubcommand()
        val shopName = ArgumentType.Word("shopID")
        val itemName = ArgumentType.Word("itemID")

        val price = ArgumentType.Integer("price")
        price.defaultValue = 0

        val existingShopName = ArgumentType.DynamicWord("shopname").fromRestrictions { shops.any { shop -> shop.name == it } }
        val delete = "delete".asSubcommand()

        addSyntax(create, shopName) { player, args ->

            if (shops.any { args.get(shopName) == it.name }) {
                player.sendMessage("${ChatColor.RED}A shop with that name already exists!")
                return@addSyntax
            }

            val shop = Shop(args.get(shopName))
            shops.add(shop)
            player.sendMessage("${ChatColor.BRIGHT_GREEN}Shop successfully created!")
        }

        addSyntax(delete, shopName) { player, args ->
            for (s in shops) {
                if (args.get(shopName) == s.name) {
                    shops.remove(s)
                    player.sendMessage("${ChatColor.BRIGHT_GREEN}Shop successfully deleted!")
                    return@addSyntax
                }
            }

            player.sendMessage("${ChatColor.RED}A shop with that name does not exist!")
            return@addSyntax
        }

        addSyntax(open, shopName) { sender, args ->

            val player = sender as Player

            val shop = shops.find { it.name == args.get(shopName) }

            if (shop == null) {
                player.sendMessage("${ChatColor.RED}A shop with that name does not exist!")
                return@addSyntax
            }

            shop.render(player)
        }

        addSyntax(addItem, shopName, price) { sender, args ->

            val player = sender as Player

            val shop = shops.find { it.name == args.get(shopName) }

            if (shop == null) {
                player.sendMessage("${ChatColor.RED}That shop does not exist!")
                return@addSyntax
            }

            if (checkIsItem(player.itemInMainHand)) {
                val item = player.itemInMainHand.data!!.get<Item>(Item.key)!!

                shop.items.add(ShopItem(item, args.get(price)))
            }
        }
    }

    override fun onDynamicWrite(sender: CommandSender, text: String): Array<String> {
        return shops.map { it.name }.toTypedArray()
    }

}