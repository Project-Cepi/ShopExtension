package world.cepi.shops.meta

import world.cepi.shops.shop.Shop

sealed interface ShopMeta {

    fun apply(shop: Shop)

}