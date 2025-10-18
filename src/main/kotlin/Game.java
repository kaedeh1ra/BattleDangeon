import data.DangeonMap;
import data.model.Player;

import java.util.Random;

public class Game {
    public static void start() {
        Player player = new Player(0,0);
        DangeonMap map = new DangeonMap(10,10,player);

        Random random = new Random();
        map.spawnItems(random.nextInt(3,5));
        map.spawnEnemies(random.nextInt(4,8));

        System.out.println("Добро пожаловать в подземелье!");
        System.out.println("Используй W/A/S/D для перемещение, I чтобы открыть инвентарь.\n");

        java.util.Scanner scanner = new java.util.Scanner(System.in);

        while (player.isAlive()) {
            map.render();
            System.out.print("> ");
            String command = scanner.nextLine().trim().toLowerCase();

            switch (command) {
                case "w" -> map.movePlayer(0, -1);
                case "s" -> map.movePlayer(0, 1);
                case "a" -> map.movePlayer(-1, 0);
                case "d" -> map.movePlayer(1, 0);
                case "i" -> {
                    if (player.getInventory().isEmpty()) {
                        System.out.println("Инвентарь пуст.");
                    } else {
                        System.out.println("Инвентарь:");
                        for (int i = 0; i < player.getInventory().size(); i++) {
                            System.out.println("[" + i + "] " + player.getInventory().get(i).getVisibleName(player));
                        }

                        System.out.print("Использовать предмет под номером (или Enter чтобы выйти): ");
                        String input = scanner.nextLine().trim();
                        if (!input.isEmpty()) {
                            try {
                                int index = Integer.parseInt(input);
                                if (index >= 0 && index < player.getInventory().size()) {
                                    player.getInventory().get(index).use(player);
                                    player.getInventory().remove(index);
                                }
                            } catch (NumberFormatException ignored) {}
                        }
                    }
                }
                default -> System.out.println("Неизвестная команда.");
            }

            if (!player.isAlive()) System.out.println("Ты сдох! Игра окончена.");
            if (map.getEnemiesCount() == 0) {
                System.out.println("Молодец! Ты победил(а)");
                break;
            }
        }
    }
}
