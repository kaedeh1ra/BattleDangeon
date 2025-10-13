package ru.kaed.data.repository

interface Combatant {
    var hp: Int
    var attackPower: Int

    fun attack(target: Combatant)
    fun takeDamage(damage: Int)
    fun isAlive(): Boolean = hp > 0
}