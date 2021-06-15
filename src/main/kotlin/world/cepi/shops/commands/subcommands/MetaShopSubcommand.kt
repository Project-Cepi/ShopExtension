package world.cepi.shops.commands.subcommands

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.minestom.server.command.builder.Command
import world.cepi.kepi.messages.sendFormattedTranslatableMessage
import world.cepi.kstom.command.addSyntax
import world.cepi.kstom.command.arguments.argumentsFromClass
import world.cepi.kstom.command.arguments.literal
import world.cepi.shops.commands.ShopCommand
import world.cepi.shops.meta.ShopMeta

internal object MetaShopSubcommand : Command("meta") {

    init {
        ShopMeta::class.sealedSubclasses.forEach {
            val clazzArgs = argumentsFromClass(it)

            val displayName = it.simpleName!!.dropLast("meta".length).toLowerCase()

            val literal = displayName.literal()

            addSyntax(ShopCommand.shopID, literal, *clazzArgs.args) { sender, args ->
                val shop = args[ShopCommand.shopID]

                val instance = clazzArgs.createInstance(args, sender)

                instance.apply(shop)

                sender.sendFormattedTranslatableMessage(
                    "shop", "meta.set",
                    Component.text(displayName, NamedTextColor.BLUE)
                )

            }
        }
    }

}