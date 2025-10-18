package ru.kaed.data

import ru.kaed.data.model.Enemy
import ru.kaed.data.model.Item
import ru.kaed.data.model.ItemEffect
import ru.kaed.data.model.Player
import ru.kaed.data.repository.Entity
import kotlin.math.min
import kotlin.random.Random

data class DungeonMap(
    val width: Int,
    val height: Int,
    val player: Player,
    val entities: MutableList<Entity> = mutableListOf()
) {
    fun isInside(x: Int, y: Int) = x in 0..<width && y in 0..<height

    fun getEntityAt(x: Int, y: Int): Entity? = entities.find { it.x == x && it.y == y }

    fun isFree(x: Int, y: Int): Boolean =
        isInside(x, y) && getEntityAt(x, y) == null && (player.x != x || player.y != y)

    fun render() {
        for (y in 0..<height) {
            for (x in 0..<width) {
                when {
                    player.x == x && player.y == y -> print("@ ")
                    else -> print((getEntityAt(x, y)?.symbol ?: '.') + " ")
                }
            }
            println()
        }
        println("HP: ${player.hp} | В инвентаре ${player.inventory.size} предметов")
    }


    fun spawnEnemies(count: Int) {
        repeat(count) {
            val (x, y) = randomFreeCell()
            entities.add(Enemy(x, y))
        }
    }

    fun spawnItems(count: Int) {
        repeat(count) {
            val (x, y) = randomFreeCell()
            entities.add(ItemFactory.createRandomItem(x, y, player))
        }
    }

    fun movePlayer(dx: Int, dy: Int){
        val newX = player.x + dx
        val newY = player.y + dy
        if (!isInside(newX, newY)) {
            println("Нельзя ходить в стену!")
            return
        }

        val entity = getEntityAt(newX, newY)

        when(entity){
            is Enemy -> {
                player.attack(entity)
                if (entity.isAlive()) entity.attack(player)
                else {
                    println("Враг побеждён")
                    entities.remove(entity)
                }
            }
            is Item -> {
                println("Ты нашёл ${entity.name}! Взять? (y/n)")
                if (readln().trim().lowercase() == "y") {
                    player.inventory.add(entity)
                    entities.remove(entity)
                }
                player.x = newX
                player.y = newY
            }
            else -> {
                player.x = newX
                player.y = newY
            }
        }
    }

    private fun randomFreeCell(): Pair<Int, Int> {
        var x: Int
        var y: Int
        do {
            x = Random.nextInt(width)
            y = Random.nextInt(height)
        } while (!isFree(x, y))
        return x to y
    }

}

object ItemFactory {
    fun createRandomItem(x: Int, y: Int, player: Player): Item {
        val roll = Random.nextInt(100)

        val effect: ItemEffect = when {
            roll < 50 -> ItemEffect.Heal(Random.nextInt(3, 11))
            roll < 75 -> ItemEffect.BuffAttack(Random.nextInt(3, 11))
            roll < 90 -> ItemEffect.Poison(Random.nextInt(2, 6))
            else -> ItemEffect.DebuffAttack(Random.nextInt(1, 4))
        }

        return Item(
            x = x,
            y = y,
            name = effect.toString(),
            effect = applyEffect(player = player, effect = effect)
        )
    }

    private fun applyEffect(player: Player, effect: ItemEffect): (Player) -> Unit = {
        when (effect) {
            is ItemEffect.Heal -> {
                val currentHP = min(effect.amount + player.hp, 3)
                val increasedHP = currentHP - player.hp
                player.hp = currentHP
                println("Здоровье восстановлено на ${increasedHP}, HP: ${player.hp}")
            }
            is ItemEffect.Poison -> {
                player.hp -= effect.amount
                println("Здоровье уменьшено на ${effect.amount}, HP: ${player.hp}")
            }
            is ItemEffect.BuffAttack -> {
                player.attackPower += effect.amount
                println("Атака увеличена на ${effect.amount}, теперь ты наносишь ${player.attackPower} урона")
            }
            is ItemEffect.DebuffAttack -> {
                player.attackPower -= effect.amount
                println("Атака Уменьшена на ${effect.amount}, теперь ты наносишь ${player.attackPower} урона")
            }
        }
    }


}