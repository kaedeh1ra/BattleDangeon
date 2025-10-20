package ru.kaed.data.model

import ru.kaed.data.repository.Combatant
import ru.kaed.data.repository.Entity
import kotlin.random.Random
import kotlin.random.nextInt

class Enemy(
    override var x: Int,
    override var y: Int,
    override var hp: Int = Random.nextInt(9, 16 + 1),
    override var attackPower: Int = Random.nextInt(3, 6 + 1)
) : Entity, Combatant {
    override val symbol: Char = 'E'

    override fun attack(target: Combatant) {
        target.takeDamage(attackPower)
        println("Враг атаковал на $attackPower урона")
    }

    override fun takeDamage(damage: Int) {
        hp -= damage
        println("У врага осталось $hp hp")
    }
}
