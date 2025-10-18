package ru.kaed.data

import ru.kaed.data.model.Enemy
import ru.kaed.data.model.Item
import ru.kaed.data.model.Player
import ru.kaed.data.repository.Entity
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



}