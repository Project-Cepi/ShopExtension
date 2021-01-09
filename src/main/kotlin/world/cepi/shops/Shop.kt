package world.cepi.shops

import net.minestom.server.entity.Player
import net.minestom.server.inventory.InventoryType
import net.minestom.server.item.ItemStack
import net.minestom.server.item.Material
import world.cepi.shops.menuapi.ItemBuilder
import world.cepi.shops.menuapi.menu

data class Shop(
    /** The ID of the shop, for storage. */
    val id: String,

    /** The display icon of the shop. */
    val icon: Material = Material.PAPER,

    /** The human name of the shop*/
    val name: String = "Shop",

    /** All the items in a shop in a list. */
    val items: MutableList<ShopItem> = mutableListOf()
) {
    fun render(player: Player) {
        menu(type = InventoryType.CHEST_6_ROW) {
            setRow(0, ItemStack(Material.WHITE_STAINED_GLASS_PANE, 1))
            set(4, ItemBuilder(icon, "Test").asItem())
        }.open(player)
    }
}