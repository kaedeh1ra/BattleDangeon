package ru.kaed.data.model

import ru.kaed.data.repository.Entity

class Item(
    override var x: Int,
    override var y: Int,
    val name: String,
    val effect: (Player) -> Unit
) : Entity {
    override val symbol: Char = 'I'

    fun use(player: Player) {
        println("Ты использовал $name")
        effect(player)
    }
}