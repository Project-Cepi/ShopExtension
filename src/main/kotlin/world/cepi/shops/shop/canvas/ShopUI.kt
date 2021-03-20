package world.cepi.shops.shop.canvas

import com.mattworzala.canvas.Props
import com.mattworzala.canvas.component
import com.mattworzala.canvas.extra.row
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration
import net.minestom.server.item.ItemStack
import net.minestom.server.item.Material
import world.cepi.shops.shop.Shop

val ShopUI = component(9, 6) {

    val shop = props.get<Shop>("shop")

    row(0) {
        item = ItemStack(Material.WHITE_STAINED_GLASS_PANE, 1)
    }

    get(4).item {
        material = shop.icon
        displayName = Component.text()
            .content(shop.name)
            .color(NamedTextColor.WHITE)
            .decoration(TextDecoration.ITALIC, false)
            .build()
    }

    row(1..3) {
        item = ItemStack(Material.GRAY_STAINED_GLASS_PANE, 1)
    }

}