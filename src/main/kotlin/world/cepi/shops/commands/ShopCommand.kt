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
import world.cepi.kepi.messages.sendFormattedTranslatableMessage
import world.cepi.kepi.subcommands.Help
import world.cepi.kstom.command.addSyntax
import world.cepi.kstom.command.arguments.literal
import world.cepi.kstom.item.get
import world.cepi.shops.shop.Shop
import world.cepi.shops.shop.ShopItem
import java.util.concurrent.CompletableFuture.*
import java.util.function.Supplier

internal object ShopCommand: Command("shop") {

    private val shops: MutableMap<String, Shop> = mutableMapOf()

    init {
        val create = "create".literal()

        val item = "item".literal()

        val addItem = "add".literal()
        val removeItem = "remove".literal()

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

        val itemIndex = ArgumentType.Integer("itemIndex").min(0)

        val price = ArgumentType.Integer("price").min(0)
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

        addSyntax(item, addItem, shopID, price) { sender, args ->

            val player = sender as Player

            val shop = args.get(shopID)

            if (checkIsItem(player.itemInMainHand)) {
                val shopItem = player.itemInMainHand.meta.get<Item>(Item.key)!!

                shop.items.add(ShopItem(shopItem, args.get(price)))

                player.sendFormattedTranslatableMessage(
                    "shop",
                    "item.add",
                    Component.text(args.get(shopID).name, NamedTextColor.BLUE)
                )
            }
        }

        addSyntax(item, removeItem, shopID, itemIndex) { sender, args ->

            val player = sender as Player
            val shop = args.get(shopID)
            val index = args.get(itemIndex)

            if (shop.items.size < index) {
                player.sendFormattedTranslatableMessage("shop", "item.notfound")
                return@addSyntax
            }

            val shopItem = shop.items[index]
            shop.items.remove(shopItem)

            player.sendFormattedTranslatableMessage(
                "shop", "item.remove",
                Component.text(shop.name, NamedTextColor.BLUE)
            )
            return@addSyntax
        }

        addSubcommand(Help(
            Component.text("The shop command allows you to"),
            Component.text("create and manage shops."),
            Component.space(),
            Component.text("You can:"),
            Component.text("create, delete, open,", NamedTextColor.BLUE),
            Component.text("item add, and item remove", NamedTextColor.BLUE),
            Component.space(),
            Component.text("Create, delete, and open all take the ")
                .append(Component.text("shop name", NamedTextColor.YELLOW))
                .append(Component.text(" parameter.")),
            Component.space(),
            Component.text("The two special commands are as follows: "),
            Component.space(),
            Component.text("item add <shop name> <price>", NamedTextColor.YELLOW),
            Component.text("Which takes the item in your hand as the item."),
            Component.space(),
            Component.text("item remove <shop name> <item index>", NamedTextColor.YELLOW),
            Component.text("With the first item starting at 0")
        ))
    }

}