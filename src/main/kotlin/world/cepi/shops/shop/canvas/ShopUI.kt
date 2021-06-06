package world.cepi.shops.shop.canvas

import com.mattworzala.canvas.extra.col
import com.mattworzala.canvas.extra.row
import com.mattworzala.canvas.fragment
import net.kyori.adventure.sound.Sound
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration
import net.minestom.server.entity.Player
import net.minestom.server.item.ItemStack
import net.minestom.server.item.Material
import net.minestom.server.sound.SoundEvent
import net.minestom.server.tag.Tag
import world.cepi.economy.EconomyHandler
import world.cepi.itemextension.item.Item
import world.cepi.itemextension.item.checkIsItem
import world.cepi.kstom.item.get
import world.cepi.kstom.item.lore
import world.cepi.kstom.item.withMeta
import world.cepi.kstom.util.component1
import world.cepi.kstom.util.component2
import world.cepi.kstom.util.component3
import world.cepi.shops.shop.Shop
import java.lang.StrictMath.floor

internal const val shopWidth = 7

internal val ShopUI = fragment(9, 6) {

    val shop = this.data.get<Shop>("shop")!!
    val player = this.data.get<Player>("player")!!

    row(0) {
        item = ItemStack.of(Material.GRAY_STAINED_GLASS_PANE).withDisplayName(Component.space())
    }

    this[4].item(shop.icon) {
        displayName(Component.text(shop.name)
            .color(NamedTextColor.WHITE)
            .decoration(TextDecoration.ITALIC, false))
    }

    row(5) {
        item = ItemStack.of(Material.WHITE_STAINED_GLASS_PANE).withDisplayName(Component.space())
    }

    this.slot(4, 5) {
        item(Material.EMERALD) {
            displayName(
                Component.text("Sell", NamedTextColor.GREEN)
                    .decoration(TextDecoration.ITALIC, false)
            )
        }

        onClick { event ->
            val (x, y, z) = player.position

            val heldItem = event.cursorItem

            val cantSellHandler = fun() {
                player.playSound(Sound.sound(
                    SoundEvent.BLOCK_NOTE_BLOCK_PLING,
                    Sound.Source.MASTER,
                    1f,
                    .5f
                ), x, y, z)
            }

            // We need to make sure they're actually holding an item
            if (heldItem == ItemStack.AIR) run {
                cantSellHandler()
                return@onClick
            }

            // Looking specifically only for cepi items
            if (!checkIsItem(heldItem)) run {
                cantSellHandler()
                return@onClick
            }

            val priceAmount = heldItem.meta.getTag(Tag.Integer("price")) ?: run {
                cantSellHandler()
                return@onClick
            }

            if (priceAmount == 0) {
                cantSellHandler()
                return@onClick
            }

            EconomyHandler[player] += priceAmount * heldItem.amount.toLong()

            event.cursorItem = ItemStack.AIR

            player.playSound(Sound.sound(
                SoundEvent.BLOCK_NOTE_BLOCK_PLING,
                Sound.Source.MASTER,
                1f,
                2f
            ), x, y, z)
        }
    }

    shop.items.forEachIndexed { index, shopItem ->
        val displayIndexRow = index % shopWidth
        val displayIndexCol = floor(index.toDouble() / (shopWidth)).toInt()

        this.slot(displayIndexRow + 1, displayIndexCol + 1) {
            item = shopItem.renderItem()

            onClick {

                val price = shopItem.price.toLong()

                val (x, y, z) = player.position

                if (EconomyHandler[player] < price) {
                    player.playSound(Sound.sound(
                        SoundEvent.BLOCK_NOTE_BLOCK_PLING,
                        Sound.Source.MASTER,
                        1f,
                        .5f
                    ), x, y, z)
                    return@onClick
                }

                player.playSound(Sound.sound(
                    SoundEvent.BLOCK_NOTE_BLOCK_PLING,
                    Sound.Source.MASTER,
                    1f,
                    2f
                ), x, y, z)

                player.inventory.addItemStack(shopItem.item.renderItem())

                EconomyHandler[player] -= price
            }
        }

    }

    col(0, 8) {
        item = ItemStack.builder(Material.GRAY_STAINED_GLASS_PANE).withMeta {
            customModelData(1)
            displayName(Component.space())
        }.build()
    }


}