package world.cepi.shops.commands

import net.minestom.server.command.CommandSender
import net.minestom.server.command.builder.Command
import net.minestom.server.command.builder.arguments.ArgumentType
import world.cepi.kstom.addSyntax
import world.cepi.kstom.arguments.asSubcommand
import world.cepi.shops.ShopObject.Shop

class ShopCommand: Command("shop") {

    companion object {
        val shops: MutableList<Shop> = mutableListOf()
    }

    init {
        val create = "create".asSubcommand()
        val edit = "edit".asSubcommand()
        val open = "open".asSubcommand()
        val shopName = ArgumentType.Word("id")

        val existingShopName = ArgumentType.DynamicWord("shopid").fromRestrictions { shops.any { shop -> shop.id == it} }
        val add = "add".asSubcommand()
        val remove = "remove".asSubcommand()
        val delete = "delete".asSubcommand()

        addSyntax(create, shopName) { player, args ->

            val shop = Shop(args.get(shopName))

            shops.add(shop)

        }

    }

    override fun onDynamicWrite(sender: CommandSender, text: String): Array<String> {
        return shops.map { it.id }.toTypedArray()
    }

}