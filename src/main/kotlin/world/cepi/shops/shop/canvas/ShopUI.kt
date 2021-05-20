package world.cepi.shops.shop.canvas

import com.mattworzala.canvas.extra.row
import com.mattworzala.canvas.fragment
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration
import net.minestom.server.item.ItemStack
import net.minestom.server.item.Material
import world.cepi.shops.shop.Shop

val ShopUI = fragment(9, 6) {

    val shop = this.data.get<Shop>("shop")!!

    row(0) {
        item = ItemStack.of(Material.WHITE_STAINED_GLASS_PANE)
    }

    this[4].item(shop.icon) {
        displayName(Component.text()
            .content(shop.name)
            .color(NamedTextColor.WHITE)
            .decoration(TextDecoration.ITALIC, false)
            .build())
    }

    row(1..3) {
        item = ItemStack.of(Material.GRAY_STAINED_GLASS_PANE)
    }

}