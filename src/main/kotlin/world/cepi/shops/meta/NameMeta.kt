package world.cepi.shops.meta

import world.cepi.kstom.adventure.asMini
import world.cepi.shops.shop.Shop

class NameMeta(val name: String) : ShopMeta {

    override fun apply(shop: Shop) {
        shop.name = name.asMini()
    }

}