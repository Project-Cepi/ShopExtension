package world.cepi.shops

data class Shop(val id: String, val items: MutableList<ShopItem> = mutableListOf())