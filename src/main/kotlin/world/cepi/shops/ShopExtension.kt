package world.cepi.shops

import net.minestom.server.MinecraftServer
import net.minestom.server.extensions.Extension;
import world.cepi.shops.commands.ShopCommand

class ShopExtension : Extension() {

    override fun initialize() {
        MinecraftServer.getCommandManager().register(ShopCommand)
        logger.info("[ShopExtension] has been enabled!")
    }

    override fun terminate() {
        logger.info("[ShopExtension] has been disabled!")
    }

}