package ru.kaed.data.model

import ru.kaed.data.repository.Combatant
import ru.kaed.data.repository.Entity

class Enemy(
    override var x: Int,
    override var y: Int,
    override var hp: Int = 10,
    override var attackPower: Int = 3
) : Entity, Combatant {
    override val symbol: Char = 'E'

    override fun attack(target: Combatant) {
        target.takeDamage(attackPower)
        println("Враг атаковал на ${attackPower} урона")
    }

    override fun takeDamage(damage: Int) {
        hp -= damage
        println("У врага осталось $hp hp")
    }
}