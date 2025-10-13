package ru.kaed.data.model

import ru.kaed.data.repository.Combatant
import ru.kaed.data.repository.Entity

class Player(
    override var x: Int,
    override var y: Int,
    override var hp: Int = 30,
    override var attackPower: Int = 5
) : Entity, Combatant {
    override val symbol: Char = '@'
    val inventory = mutableListOf<Item>()

    override fun attack(target: Combatant) {
        println("Ты атаковал моба на $attackPower урона")
        target.takeDamage(attackPower)
    }

    override fun takeDamage(damage: Int) {
        hp -= damage
        println("Ты был атакован на $damage урона(( \nУ тебя осталось $hp hp")
    }
}