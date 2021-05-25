package world.cepi.shops.shop.canvas

import com.mattworzala.canvas.extra.col
import com.mattworzala.canvas.extra.row
import com.mattworzala.canvas.fragment
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration
import net.minestom.server.item.ItemStack
import net.minestom.server.item.Material
import world.cepi.kstom.item.withMeta
import world.cepi.shops.shop.Shop
import java.lang.StrictMath.floor

internal const val shopWidth = 7

internal val ShopUI = fragment(9, 6) {

    val shop = this.data.get<Shop>("shop")!!

    row(0) {
        item = ItemStack.of(Material.GRAY_STAINED_GLASS_PANE)
    }

    this[4].item(shop.icon) {
        displayName(Component.text(shop.name)
            .color(NamedTextColor.WHITE)
            .decoration(TextDecoration.ITALIC, false))
    }

    row(5) {
        item = ItemStack.of(Material.WHITE_STAINED_GLASS_PANE)
    }

    this.slot(4, 5) {
        item(Material.EMERALD) {
            displayName(
                Component.text("Sell", NamedTextColor.GREEN)
                    .decoration(TextDecoration.ITALIC, false)
            )
        }
    }

    shop.items.forEachIndexed { index, shopItem ->
        val displayIndexRow = index % shopWidth
        val displayIndexCol = floor(index.toDouble().coerceAtLeast(1.0) / (shopWidth)).toInt()

        this.slot(displayIndexRow + 1, displayIndexCol + 1) {
            item = shopItem.item.renderItem(1)
        }

    }

    col(0, 8) {
        item = ItemStack.builder(Material.GRAY_STAINED_GLASS_PANE).withMeta {
            customModelData(1)
        }.build()
    }


}