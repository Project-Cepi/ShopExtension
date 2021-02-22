package world.cepi.shops.menuapi

import net.minestom.server.item.ItemStack
import net.minestom.server.item.Material
import world.cepi.kstom.asRich

/**
 * Makes a new [ItemStack] from the given parameters.
 *
 * @param material The [Material] that the [ItemStack] will be.
 * @param name The displayed name that the [ItemStack] will have. Default = [Material] name
 * @param amount The amount of items in the [ItemStack]. Default = 1
 */
class ItemBuilder(val material: Material, val name: String = material.name, val amount: Byte = 1) {
    fun asItem(): ItemStack {
        val itemStack = ItemStack(material, amount)
        itemStack.displayName = name.asRich()
        return itemStack
    }
}