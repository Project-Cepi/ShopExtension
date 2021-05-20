package world.cepi.shops.commands

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.minestom.server.command.CommandSender
import net.minestom.server.command.builder.ArgumentCallback
import net.minestom.server.command.builder.Command
import net.minestom.server.command.builder.arguments.ArgumentType
import net.minestom.server.command.builder.exception.ArgumentSyntaxException
import net.minestom.server.entity.Player
import world.cepi.itemextension.item.Item
import world.cepi.itemextension.item.checkIsItem
import world.cepi.kepi.messages.sendFormattedMessage
import world.cepi.kepi.messages.sendFormattedTranslatableMessage
import world.cepi.kstom.command.addSyntax
import world.cepi.kstom.command.arguments.literal
import world.cepi.kstom.item.get
import world.cepi.shops.shop.Shop
import world.cepi.shops.shop.ShopItem
import java.util.concurrent.CompletableFuture.*
import java.util.function.Supplier

object ShopCommand: Command("shop") {

    val shops: MutableMap<String, Shop> = mutableMapOf()

    init {
        val create = "create".literal()
        val addItem = "additem".literal()
        val removeItem = "removeItem".literal()
        val open = "open".literal()

        val newShopID = ArgumentType.Word("newShopID").map { input ->
            if (shops.containsKey(input)) throw ArgumentSyntaxException("Shop already exists", input, 1)
            input
        }.apply {
            callback = ArgumentCallback { sender, exception ->
                sender.sendFormattedTranslatableMessage(
                    "shop", "exists",
                    Component.text(exception.input, NamedTextColor.BLUE)
                )
            }
        }

        val shopID = ArgumentType.Word("shopID").map { input ->
            shops[input] ?: throw ArgumentSyntaxException("Shop does not exists", input, 1)
        }.apply {
            callback = ArgumentCallback { sender, exception ->
                sender.sendFormattedTranslatableMessage(
                    "shop", "exists.not",
                    Component.text(exception.input, NamedTextColor.BLUE)
                )
            }
        }

        val itemIndex = ArgumentType.Integer("itemIndex")

        val price = ArgumentType.Integer("price")
        price.defaultValue = Supplier { 0 }

        val delete = "delete".literal()

        addSyntax(create, newShopID) { player, args ->

            val shop = Shop()
            shops[args.get(newShopID)] = shop
            player.sendFormattedTranslatableMessage(
                "shop", "create",
                Component.text(args.get(newShopID), NamedTextColor.BLUE)
            )
        }

        addSyntax(delete, shopID) { player, args ->

            shops.remove(args.get(shopID).name)
            player.sendFormattedTranslatableMessage(
                "shop",
                "delete",
                Component.text(args.get(shopID).name, NamedTextColor.BLUE)
            )
        }

        addSyntax(open, shopID) { sender, args ->

            val player = sender as Player

            val shop = args.get(shopID)

            shop.render(player)
        }

        addSyntax(addItem, shopID, price) { sender, args ->

            val player = sender as Player

            val shop = args.get(shopID)

            if (checkIsItem(player.itemInMainHand)) {
                val item = player.itemInMainHand.meta.get<Item>(Item.key)!!

                shop.items.add(ShopItem(item, args.get(price), shop.counter))

                shop.counter += 1

                player.sendFormattedTranslatableMessage(
                    "shop",
                    "item.add",
                    Component.text(args.get(shopID).name, NamedTextColor.BLUE)
                )
            }
        }

        addSyntax(removeItem, shopID, itemIndex) { sender, args ->

            val player = sender as Player
            val shop = args.get(shopID)
            val index = args.get(itemIndex)

            val item = shop.items.firstOrNull {it.id == shop.items[index - 1].id }
            if (item == null) {
                player.sendFormattedTranslatableMessage("shop", "item.notfound")
                return@addSyntax
            }
            shop.items.remove(item)

            player.sendFormattedTranslatableMessage(
                "shop", "item.remove",
                Component.text(shop.name, NamedTextColor.BLUE)
            )
            return@addSyntax
        }
    }

    override fun onDynamicWrite(sender: CommandSender, text: String): Array<String> {
        return shops.keys.toTypedArray()
    }

}