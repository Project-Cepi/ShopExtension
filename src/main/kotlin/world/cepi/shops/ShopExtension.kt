package world.cepi.shops

import net.minestom.server.MinecraftServer
import net.minestom.server.extensions.Extension;
import world.cepi.shops.menuapi.MenuListener
import world.cepi.shops.menuapi.TestCmd

class ShopExtension : Extension() {

    override fun initialize() {
        MinecraftServer.getCommandManager().register(TestCmd())
        MenuListener().register()
        logger.info("[ExampleExtension] has been enabled!")
    }

    override fun terminate() {
        logger.info("[ExampleExtension] has been disabled!")
    }

}