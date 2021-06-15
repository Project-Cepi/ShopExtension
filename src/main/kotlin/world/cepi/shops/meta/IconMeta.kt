package world.cepi.shops.meta

import net.minestom.server.item.Material
import world.cepi.shops.shop.Shop

class IconMeta(val icon: Material): ShopMeta {

    override fun apply(shop: Shop) {
        shop.icon = icon
    }

}