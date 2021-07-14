package world.cepi.shops.commands.subcommands

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.minestom.server.command.builder.Command
import world.cepi.kepi.messages.sendFormattedTranslatableMessage
import world.cepi.kstom.command.arguments.generation.generateSyntaxes
import world.cepi.kstom.command.arguments.literal
import world.cepi.shops.commands.ShopCommand
import world.cepi.shops.meta.ShopMeta

internal object MetaShopSubcommand : Command("meta") {

    init {
        ShopMeta::class.sealedSubclasses.forEach {
            val syntaxes = generateSyntaxes(it)

            val displayName = it.simpleName!!.dropLast("meta".length).lowercase()

            val literal = displayName.literal()

            syntaxes.applySyntax(this,ShopCommand.shopID, literal) { instance ->
                val shop = context[ShopCommand.shopID]

                instance.apply(shop)

                sender.sendFormattedTranslatableMessage(
                    "shop", "meta.set",
                    Component.text(displayName, NamedTextColor.BLUE)
                )

            }
        }
    }

}