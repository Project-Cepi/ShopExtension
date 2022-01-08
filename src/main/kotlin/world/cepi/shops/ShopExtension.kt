package world.cepi.shops

import net.minestom.server.MinecraftServer
import net.minestom.server.extensions.Extension;
import world.cepi.actions.list.ActionManager
import world.cepi.kstom.command.register
import world.cepi.kstom.command.unregister
import world.cepi.kstom.util.log
import world.cepi.shops.action.ShopAction
import world.cepi.shops.commands.ShopCommand

class ShopExtension : Extension() {

    override fun initialize(): LoadStatus {
        ShopCommand.register()
        ActionManager.add<ShopAction>()

        log.info("[ShopExtension] has been enabled!")

        return LoadStatus.SUCCESS
    }

    override fun terminate() {
        ShopCommand.unregister()

        log.info("[ShopExtension] has been disabled!")
    }

}