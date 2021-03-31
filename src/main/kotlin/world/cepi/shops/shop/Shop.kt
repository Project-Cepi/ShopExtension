package world.cepi.shops.shop

import com.mattworzala.canvas.MutableProps
import com.mattworzala.canvas.ext.canvas
import net.minestom.server.entity.Player
import net.minestom.server.item.Material
import world.cepi.shops.shop.canvas.ShopUI

data class Shop(
    /** The human name of the shop*/
    val name: String = "My Shop",

    /** The display icon of the shop. */
    val icon: Material = Material.PAPER,

    /** All the items in a shop in a list. */
    val items: MutableList<ShopItem> = mutableListOf()
) {
    var counter = 0
    fun render(player: Player) {
        val props = MutableProps(player)
        props["shop"] = this
        player.canvas.render(ShopUI, props)
    }

    operator fun get(index: Int): ShopItem = items[index]
}