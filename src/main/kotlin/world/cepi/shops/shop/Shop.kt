package world.cepi.shops.shop

import com.mattworzala.canvas.ext.canvas
import net.kyori.adventure.text.Component
import net.minestom.server.entity.Player
import net.minestom.server.item.Material
import world.cepi.shops.shop.canvas.ShopUI

data class Shop(
    /** The ID of the shop. */
    val id: String,

    /** The human name of the shop*/
    var name: Component = Component.text("My Shop"),

    /** The display icon of the shop. */
    var icon: Material = Material.PAPER,

    /** All the items in a shop in a list. */
    val items: MutableList<ShopItem> = mutableListOf()
) {
    fun render(player: Player) = player.canvas.render {
        ShopUI(this, player)
    }

    operator fun get(index: Int): ShopItem = items[index]
}