package world.cepi.shops

import net.minestom.server.MinecraftServer
import net.minestom.server.extensions.Extension;
import world.cepi.actions.list.ActionManager
import world.cepi.kstom.command.register
import world.cepi.kstom.command.unregister
import world.cepi.shops.action.ShopAction
import world.cepi.shops.commands.ShopCommand

class ShopExtension : Extension() {

    override fun initialize() {
        ShopCommand.register()
        ActionManager.add<ShopAction>()
        logger.info("[ShopExtension] has been enabled!")
    }

    override fun terminate() {
        ShopCommand.unregister()
        logger.info("[ShopExtension] has been disabled!")
    }

}