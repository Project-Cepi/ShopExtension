package world.cepi.shops

import net.minestom.server.MinecraftServer
import net.minestom.server.extensions.Extension;
import world.cepi.shops.menuapi.MenuListener

class ShopExtension : Extension() {

    override fun initialize() {
        MinecraftServer.getCommandManager().register(TestCmd())
        MenuListener.register()
        logger.info("[ShopExtension] has been enabled!")
    }

    override fun terminate() {
        logger.info("[ShopExtension] has been disabled!")
    }

}