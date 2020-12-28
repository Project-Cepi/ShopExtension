package world.cepi.shops.menuapi

/** Container for a list of [MenuItem]s. */
class MenuItems {

    val items: MutableList<MenuItem> = mutableListOf()

    fun add(menuItem: MenuItem) {
        items.add(menuItem)
    }

}