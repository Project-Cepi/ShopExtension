package world.cepi.shops

import world.cepi.shops.shop.Shop

object ShopManager {

    private val shops: MutableMap<String, Shop> = mutableMapOf()

    fun keys() = shops.keys

    operator fun get(key: String): Shop? = shops[key]

    operator fun set(key: String, shop: Shop) {
        shops[key] = shop
    }

    fun has(key: String) = shops.containsKey(key)

    fun remove(key: String) {
        shops.remove(key)
    }

}