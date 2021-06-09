package world.cepi.shops.shop

import kotlinx.serialization.Serializable
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration
import world.cepi.itemextension.item.Item

@Serializable
data class ShopItem(val item: Item, val price: Int = 0) {
    fun renderItem() = item.renderItem(1).let {
        it.withLore(it.lore + listOf(
            Component.space(),
            Component.text("Buy For: ", NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false),
            Component.text("$price shards", NamedTextColor.GOLD).decoration(TextDecoration.ITALIC, false)
        ))
    }

}