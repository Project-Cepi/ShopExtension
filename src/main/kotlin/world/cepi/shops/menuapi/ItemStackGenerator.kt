package world.cepi.shops.menuapi

import net.minestom.server.entity.Player
import net.minestom.server.item.ItemStack

class ItemStackGenerator(val generator: (Menu, Player) -> ItemStack)