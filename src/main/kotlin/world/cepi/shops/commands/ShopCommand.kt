package world.cepi.shops.commands

import net.kyori.adventure.text.Component
import net.minestom.server.command.CommandSender
import net.minestom.server.command.builder.Command
import net.minestom.server.command.builder.arguments.ArgumentType
import net.minestom.server.entity.Player
import world.cepi.itemextension.item.Item
import world.cepi.itemextension.item.checkIsItem
import world.cepi.kepi.messages.sendFormattedMessage
import world.cepi.kstom.command.addSyntax
import world.cepi.kstom.command.arguments.asSubcommand
import world.cepi.shops.shop.Shop
import world.cepi.shops.shop.ShopItem
import java.util.concurrent.CompletableFuture.*
import java.util.function.Supplier

object ShopCommand: Command("shop") {

    val shops: MutableMap<String, Shop> = mutableMapOf()

    init {
        val create = "create".asSubcommand()
        val addItem = "additem".asSubcommand()
        val removeItem = "removeItem".asSubcommand()
        val open = "open".asSubcommand()
        val shopID = ArgumentType.Word("shopID")
        val itemIndex = ArgumentType.Integer("itemIndex")

        val price = ArgumentType.Integer("price")
        price.defaultValue = Supplier { 0 }

        val delete = "delete".asSubcommand()

        addSyntax(create, shopID) { player, args ->

            if (shops.containsKey(args.get(shopID))) {
                player.sendFormattedMessage(shopAlreadyExists)
                return@addSyntax
            }

            val shop = Shop()
            shops[args.get(shopID)] = shop
            player.sendFormattedMessage(shopCreated)
        }

        addSyntax(delete, shopID) { player, args ->

            if (!shops.containsKey(args.get(shopID))) {
                player.sendFormattedMessage(shopDoesNotExists)
                return@addSyntax
            }

            shops.remove(args.get(shopID))
            player.sendFormattedMessage(shopDeleted)
            return@addSyntax
        }

        addSyntax(open, shopID) { sender, args ->

            val player = sender as Player

            val shop = shops[args.get(shopID)]

            if (shop == null) {
                player.sendFormattedMessage(shopDoesNotExists)
                return@addSyntax
            }

            shop.render(player)
        }

        addSyntax(addItem, shopID, price) { sender, args ->

            val player = sender as Player

            val shop = shops[args.get(shopID)]

            if (shop == null) {
                player.sendFormattedMessage(shopDoesNotExists)
                return@addSyntax
            }

            if (checkIsItem(player.itemInMainHand)) {
                val item = player.itemInMainHand.data!!.get<Item>(Item.key)!!

                shop.items.add(ShopItem(item, args.get(price), shop.counter))

                shop.counter += 1

                player.sendFormattedMessage(shopItemAdded, Component.text(args.get(shopID)))
            }
        }

        addSyntax(removeItem, shopID, itemIndex) { sender, args ->

            val player = sender as Player
            val shop = shops[args.get(shopID)]
            val index = args.get(itemIndex)
            if (shop == null) {
                player.sendFormattedMessage(shopDoesNotExists)
                return@addSyntax
            }
            var item = shop.items.firstOrNull {it.id == shop.items[index - 1].id}
            if (item == null) {
                player.sendFormattedMessage("An item with that index could not be found in this shop!")
                return@addSyntax
            }
            shop.items.remove(item)

            player.sendFormattedMessage("Item successfully removed from shop \"${shop.name}\"")
            return@addSyntax
        }
    }

    override fun onDynamicWrite(sender: CommandSender, text: String): Array<String> {
        return shops.keys.toTypedArray()
    }

}