package world.cepi.shops.commands

import net.minestom.server.command.builder.Command
import net.minestom.server.command.builder.arguments.ArgumentType

class ShopsCommand : Command("shops") {
    init {
        val create = ArgumentType.Word("create").from("create")
        val shopName = ArgumentType.Word("shopName").from("shopName")
        addSyntax(create, shopName) {sender, args ->

        }
    }
}