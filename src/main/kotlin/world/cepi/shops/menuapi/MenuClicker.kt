package world.cepi.shops.menuapi

import net.minestom.server.inventory.click.ClickType

/** Represents a clicker object, containing cancellable and a lambda */
data class MenuClicker(
        /** The action to run when the item is clicked*/
        val consumer: (ClickType) -> Unit = { },

        /** Whether to cancel movement. */
        val toCancel: Boolean = true
)