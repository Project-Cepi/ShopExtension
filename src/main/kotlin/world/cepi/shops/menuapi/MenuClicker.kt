package world.cepi.shops.menuapi

import net.minestom.server.inventory.click.ClickType

/** Represents a clicker object, containing cancellable and a lambda */
data class MenuClicker(val consumer: (ClickType) -> Unit = { }, val toCancel: Boolean = true)