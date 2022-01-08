package world.cepi.shops.commands.subcommands

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.minestom.server.command.builder.Command
import world.cepi.kepi.messages.sendFormattedTranslatableMessage
import world.cepi.kstom.command.arguments.generation.ClassArgumentGenerator.Companion.syntaxesFrom
import world.cepi.kstom.command.arguments.generation.generateSyntaxes
import world.cepi.kstom.command.arguments.literal
import world.cepi.kstom.command.kommand.Kommand
import world.cepi.shops.commands.ShopArguments
import world.cepi.shops.commands.ShopCommand
import world.cepi.shops.meta.ShopMeta

internal object MetaShopSubcommand : Kommand({

    ShopMeta::class.sealedSubclasses.forEach {
        val displayName = it.simpleName!!.dropLast("meta".length).lowercase()

        val literal = displayName.literal()

        syntaxesFrom(it, ShopArguments.shopID, literal) { instance ->
            val shop = !ShopArguments.shopID

            instance.apply(shop)

            sender.sendFormattedTranslatableMessage(
                "shop", "meta.set",
                Component.text(displayName, NamedTextColor.BLUE)
            )

        }
    }

}, "meta")