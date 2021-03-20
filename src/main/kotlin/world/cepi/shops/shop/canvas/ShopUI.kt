package world.cepi.shops.shop.canvas

import com.mattworzala.canvas.Props
import com.mattworzala.canvas.component
import com.mattworzala.canvas.extra.row
import net.minestom.server.item.ItemStack
import net.minestom.server.item.Material
import world.cepi.shops.shop.Shop

val ShopUI = component(9, 6) {


    row(0) {
        item = ItemStack(Material.WHITE_STAINED_GLASS_PANE, 1)
    }

    get(4).item {
        material = props.get<Shop>("shop").icon
        displayName
    }

    row(1..3) {
        item = ItemStack(Material.GRAY_STAINED_GLASS_PANE, 1)
    }

}