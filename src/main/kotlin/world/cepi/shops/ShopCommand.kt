package world.cepi.shops

import net.minestom.server.command.builder.Command
import net.minestom.server.command.builder.arguments.ArgumentType
import world.cepi.kstom.addSyntax
import world.cepi.kstom.arguments.asSubcommand

class ShopCommand: Command("shop") {


    init {
        val create = "create".asSubcommand()
        val edit = "edit".asSubcommand()
        val open = "open".asSubcommand()
        val shopName = ArgumentType.Word("id")

        val existingShopName = ArgumentType.DynamicWord("shopid")
        val add = "add".asSubcommand()
        val remove = "remove".asSubcommand()
        val delete = "delete".asSubcommand()

        addSyntax(create, shopName) { ->

        }

    }

}