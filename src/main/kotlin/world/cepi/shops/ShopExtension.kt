package world.cepi.shops

import net.minestom.server.MinecraftServer
import net.minestom.server.extensions.Extension;
import world.cepi.shops.commands.ShopCommand
import world.cepi.shops.menuapi.MenuListener

class ShopExtension : Extension() {

    override fun initialize() {
        MenuListener.register()
        MinecraftServer.getCommandManager().register(ShopCommand())
        logger.info("[ShopExtension] has been enabled!")
    }

    override fun terminate() {
        logger.info("[ShopExtension] has been disabled!")
    }

}