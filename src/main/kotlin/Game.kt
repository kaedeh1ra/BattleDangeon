package ru.kaed

import ru.kaed.data.DungeonMap
import ru.kaed.data.model.Player

class Game {
    companion object {
        fun start() {
            val player = Player(0, 0)
            val map = DungeonMap(width = 10, height = 10, player = player)
            map.spawnEnemies(4)
            map.spawnItems(3)

            println("Добро пожаловать в подземелье!")
            println("Используйте W/A/S/D для ходьбы, I для открытия инвентаря.\n")

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
                            println("Инвентарь пустой.")
                        } else {
                            println("Инвентарь:")
                            player.inventory.forEachIndexed { i, item ->
                                println("[$i] ${item.name}")
                            }
                            print("Использовать предмет под номером (или Enter для скипа): ")
                            val input = readlnOrNull()?.toIntOrNull()
                            input?.let { index ->
                                val item = player.inventory.getOrNull(index)
                                item?.use(player)
                                player.inventory.remove(item)
                            }
                        }
                    }

                    else -> println("Неизвестная команда.")
                }

                if (!player.isAlive()) println("💀 Ты умер! Игра окончена.")
            }
        }
    }
}