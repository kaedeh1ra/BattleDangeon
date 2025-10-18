package ru.kaed.data.model

import ru.kaed.data.repository.Entity
import kotlin.random.Random

data class Item(
    override var x: Int, override var y: Int, val effect: ItemEffect
) : Entity {
    override val symbol: Char = 'I'

    val visibleName: String
        get() = throw UnsupportedOperationException("Используй getVisibleName(player)")

    fun getVisibleName(player: Player): String {
        return when (effect.potionType) {
            PotionType.HEALTH -> when (player.healthPotionIdentified) {
                null -> "Зелье здоровья"
                true -> "Зелье здоровья (яд)"
                false -> "Зелье здоровья (лечение)"
            }

            PotionType.ATTACK -> when (player.attackPotionIdentified) {
                null -> "Зелье атаки"
                true -> "Зелье атаки (дебафф)"
                false -> "Зелье атаки (бафф)"
            }
        }
    }

    fun use(player: Player) {
        val message = effect.apply(player)

        if (effect.isNegative) {
            when (effect.potionType) {
                PotionType.HEALTH -> player.healthPotionIdentified = true
                PotionType.ATTACK -> player.attackPotionIdentified = true
            }
        }

        println("Вы использовали ${getVisibleName(player)}. $message")
    }
}

object ItemFactory {

    private const val HP_POTION_VISIBLE_PROB = 0.5
    private const val POSITIVE_HP_PROB = 0.7
    private const val POSITIVE_ATK_PROB = 0.7
    private val HEAL_RANGE = 3..10
    private val POISON_RANGE = 2..6
    private val BUFF_ATTACK_RANGE = 1..3
    private val DEBUFF_ATTACK_RANGE = 1..3

    fun createRandomItem(x: Int, y: Int): Item {
        val showHpPotion = Random.nextDouble() < HP_POTION_VISIBLE_PROB

        val effect: ItemEffect = if (showHpPotion) {
            if (Random.nextDouble() < POSITIVE_HP_PROB) ItemEffect.Heal(
                Random.nextInt(
                    HEAL_RANGE.first, HEAL_RANGE.last + 1
                )
            )
            else ItemEffect.Poison(Random.nextInt(POISON_RANGE.first, POISON_RANGE.last + 1))

        } else {
            if (Random.nextDouble() < POSITIVE_ATK_PROB) ItemEffect.BuffAttack(
                Random.nextInt(
                    BUFF_ATTACK_RANGE.first, BUFF_ATTACK_RANGE.last + 1
                )
            )
            else ItemEffect.DebuffAttack(Random.nextInt(DEBUFF_ATTACK_RANGE.first, DEBUFF_ATTACK_RANGE.last + 1))

        }

        return Item(x, y, effect)
    }
}

sealed class ItemEffect(val potionType: PotionType) {
    abstract fun apply(player: Player): String
    abstract val isNegative: Boolean

    data class Heal(val amount: Int) : ItemEffect(PotionType.HEALTH) {
        override val isNegative = false
        override fun apply(player: Player): String {
            player.hp += amount
            return "Восстановлено $amount HP (HP=${player.hp})"
        }
    }

    data class Poison(val amount: Int) : ItemEffect(PotionType.HEALTH) {
        override val isNegative = true
        override fun apply(player: Player): String {
            player.hp -= amount
            return "Отравление: -$amount HP (HP=${player.hp})"
        }
    }

    data class BuffAttack(val amount: Int) : ItemEffect(PotionType.ATTACK) {
        override val isNegative = false
        override fun apply(player: Player): String {
            player.attackPower += amount
            return "Атака +$amount (ATK=${player.attackPower})"
        }
    }

    data class DebuffAttack(val amount: Int) : ItemEffect(PotionType.ATTACK) {
        override val isNegative = true
        override fun apply(player: Player): String {
            player.attackPower -= amount
            return "Атака -$amount (ATK=${player.attackPower})"
        }
    }
}

enum class PotionType {
    HEALTH, ATTACK
}

