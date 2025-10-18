package ru.kaed

import ru.kaed.data.DungeonMap
import ru.kaed.data.model.Player
import kotlin.random.Random

class Game {
    companion object {
        fun start() {
            val player = Player(0, 0)
            val map = DungeonMap(10, 10, player)

            map.spawnItems(Random.nextInt(3, 5))
            map.spawnEnemies(Random.nextInt(4, 8))

            println("Добро пожаловать в подземелье!")
            println("Используй W/A/S/D для перемещение, I чтобы открыть инветарь.\n")

            while (player.isAlive()) {
                map.render()
                print("> ")
                when (readln().trim().lowercase()) {
                    "w" -> map.movePlayer(0, -1)
                    "s" -> map.movePlayer(0, 1)
                    "a" -> map.movePlayer(-1, 0)
                    "d" -> map.movePlayer(1, 0)
                    "i" -> {
                        if (player.inventory.isEmpty()) {
                            println("Инвентарь пуст.")
                        } else {
                            println("Инвентарь:")
                            player.inventory.forEachIndexed { i, item ->
                                println("[$i] ${item.getVisibleName(player)}")
                            }

                            print("Использовать предмет под номером (или Enter чтобы выйти): ")
                            val input = readlnOrNull()?.trim()!!.toIntOrNull()
                            input?.let { index ->
                                val item = player.inventory.getOrNull(index)
                                item?.use(player)
                                player.inventory.remove(item)
                            }
                        }
                    }
                    else -> println("Неизвестная команда.")
                }
                if (!player.isAlive()) println("Ты сдох! Игра окончена.")
                if (map.getEnemiesCount() == 0) {
                    print("Молодец! Ты победил(а)")
                    break
                }
            }
        }
    }
}