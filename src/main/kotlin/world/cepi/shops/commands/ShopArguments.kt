package world.cepi.shops.commands

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.minestom.server.command.builder.ArgumentCallback
import net.minestom.server.command.builder.arguments.ArgumentType
import net.minestom.server.command.builder.exception.ArgumentSyntaxException
import world.cepi.kepi.messages.sendFormattedTranslatableMessage
import world.cepi.kstom.command.arguments.suggest
import world.cepi.shops.ShopManager

object ShopArguments {

    val shopID = ArgumentType.Word("shopID").map { input ->
        ShopManager[input] ?: throw ArgumentSyntaxException("Shop does not exists", input, 1)
    }.apply {
        callback = ArgumentCallback { sender, exception ->
            sender.sendFormattedTranslatableMessage(
                "shop", "exists.not",
                Component.text(exception.input, NamedTextColor.BLUE)
            )
        }
    }.suggest {
        ShopManager.keys().toList()
    }

}