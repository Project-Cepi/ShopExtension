package world.cepi.shops.commands

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.event.ClickEvent
import net.kyori.adventure.text.format.NamedTextColor
import net.minestom.server.command.builder.ArgumentCallback
import net.minestom.server.command.builder.Command
import net.minestom.server.command.builder.arguments.ArgumentType
import net.minestom.server.command.builder.exception.ArgumentSyntaxException
import net.minestom.server.command.builder.suggestion.SuggestionEntry
import net.minestom.server.entity.Player
import world.cepi.itemextension.item.Item
import world.cepi.itemextension.item.checkIsItem
import world.cepi.kepi.command.subcommand.applyHelp
import world.cepi.kepi.messages.sendFormattedTranslatableMessage
import world.cepi.kepi.messages.translations.formatTranslableMessage
import world.cepi.kstom.command.addSyntax
import world.cepi.kstom.command.arguments.literal
import world.cepi.kstom.command.arguments.suggest
import world.cepi.kstom.item.get
import world.cepi.shops.ShopManager
import world.cepi.shops.commands.subcommands.MetaShopSubcommand
import world.cepi.shops.shop.Shop
import world.cepi.shops.shop.ShopItem
import java.util.concurrent.CompletableFuture.*
import java.util.function.Supplier

internal object ShopCommand: Command("shop") {

    val shopID = ArgumentType.Word("shopID").map { input ->
        ShopManager[input] ?: throw ArgumentSyntaxException("Shop does not exists", input, 1)
    }.apply {
        callback = ArgumentCallback { sender, exception ->
            sender.sendFormattedTranslatableMessage(
                "shop", "exists.not",
                Component.text(exception.input, NamedTextColor.BLUE)
            )
        }
    }.suggest { _, _ ->
        ShopManager.keys().map { SuggestionEntry(it) }.toMutableList()
    }

    init {
        val create = "create".literal()
        val list = "list".literal()

        val item = "item".literal()

        val addItem = "add".literal()
        val removeItem = "remove".literal()

        val open = "open".literal()

        val newShopID = ArgumentType.Word("newShopID").map { input ->
            if (ShopManager.has(input)) throw ArgumentSyntaxException("Shop already exists", input, 1)
            input
        }.apply {
            callback = ArgumentCallback { sender, exception ->
                sender.sendFormattedTranslatableMessage(
                    "shop", "exists",
                    Component.text(exception.input, NamedTextColor.BLUE)
                )
            }
        }

        val itemIndex = ArgumentType.Integer("itemIndex").min(0)

        val price = ArgumentType.Integer("price").min(0)
        price.defaultValue = Supplier { 0 }

        val delete = "delete".literal()

        addSyntax(create, newShopID) { player, args ->

            val shop = Shop(args.get(newShopID))
            ShopManager[args.get(newShopID)] = shop
            player.sendFormattedTranslatableMessage(
                "shop", "create",
                Component.text(args.get(newShopID), NamedTextColor.BLUE)
            )
        }

        addSyntax(delete, shopID) { player, args ->

            ShopManager.remove(args.get(shopID).id)
            player.sendFormattedTranslatableMessage(
                "shop",
                "delete",
                Component.text(args.get(shopID).id, NamedTextColor.BLUE)
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
                    Component.text(args.get(shopID).id, NamedTextColor.BLUE)
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
                Component.text(shop.id, NamedTextColor.BLUE)
            )
            return@addSyntax
        }

        addSyntax(list) { sender ->
            sender.sendMessage(ShopManager.keys()
                .foldIndexed(Component.empty()) { index, acc, name ->
                    acc.append(
                        Component.text("- ", NamedTextColor.GRAY)
                            .append(
                                Component.text(name, NamedTextColor.BLUE)
                                    .hoverEvent(
                                        sender.formatTranslableMessage("common", "click.to_open")
                                            .color(NamedTextColor.GRAY)
                                    )
                                    .clickEvent(
                                        ClickEvent.runCommand(
                                            "/shop open $name"
                                        )
                                    )
                            )
                    ).let {
                        if (index != (ShopManager.size - 1))
                            it.append(Component.newline())
                        else
                            it
                    }
                })
        }

        applyHelp {
            """
                The shop command allows you to
                create and manage shops.
                
                You can:
                <blue>create, delete, open
                <blue>item add, <gray>and <blue>item remove
                
                Create, delete, and open all take the
                <yellow>shop name <gray>parameter.
                
                The two special commands are as follows:
                
                <yellow>item add (shop name) (price)
                Which takes the item in your hand as the item.
                
                <yellow>item remove (shop name) (item index)
                With the first item starting at 0.
            """.trimIndent()
        }

        addSubcommand(MetaShopSubcommand)
    }

}