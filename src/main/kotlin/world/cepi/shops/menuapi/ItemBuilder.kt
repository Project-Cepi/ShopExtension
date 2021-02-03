package world.cepi.shops.menuapi

import net.minestom.server.item.ItemStack
import net.minestom.server.item.Material
import world.cepi.kstom.asRich

class ItemBuilder(val material: Material, val name: String = material.name, val amount: Byte = 1) {
    fun asItem(): ItemStack {
        val itemStack = ItemStack(material, amount)
        itemStack.displayName = name.asRich()
        return itemStack
    }
}