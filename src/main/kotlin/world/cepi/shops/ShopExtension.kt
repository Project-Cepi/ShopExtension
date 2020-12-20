package world.cepi.shops

import net.minestom.server.extensions.Extension;

class ShopExtension : Extension() {

    override fun initialize() {
        logger.info("[ExampleExtension] has been enabled!")
    }

    override fun terminate() {
        logger.info("[ExampleExtension] has been disabled!")
    }

}