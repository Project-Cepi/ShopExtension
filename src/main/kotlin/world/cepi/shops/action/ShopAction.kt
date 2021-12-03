package world.cepi.shops.action

import kotlinx.serialization.Serializable
import net.minestom.server.entity.Entity
import net.minestom.server.entity.Player
import world.cepi.actions.Action
import world.cepi.shops.ShopManager

@Serializable
class ShopAction(val shopName: String) : Action() {

    override fun invoke(source: Entity, target: Entity?) {
        (target as? Player)?.let { ShopManager[shopName]?.render(it) }
    }

}