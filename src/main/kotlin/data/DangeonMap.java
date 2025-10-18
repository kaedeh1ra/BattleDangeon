package data;

import data.model.*;
import data.repository.Entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DangeonMap {
    private final int width;
    private final int height;
    private final Player player;
    private final List<Entity> entities = new ArrayList<>();
    private int enemiesCount = 0;
    private final Random random = new Random();

    public DangeonMap(int width, int height, Player player) {
        this.width = width;
        this.height = height;
        this.player = player;
    }

    public int getEnemiesCount() { return enemiesCount; }

    public boolean isInside(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }

    public Entity getEntityAt(int x, int y) {
        for (Entity e : entities) {
            if (e.getX() == x && e.getY() == y) return e;
        }
        return null;
    }

    public boolean isFree(int x, int y) {
        return isInside(x, y) && getEntityAt(x, y) == null && (player.getX() != x || player.getY() != y);
    }

    public void render() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (player.getX() == x && player.getY() == y) System.out.print("@ ");
                else {
                    Entity e = getEntityAt(x, y);
                    System.out.print((e != null ? e.getSymbol() : '.') + " ");
                }
            }
            System.out.println();
        }
        System.out.println("HP: " + player.getHp() + " | В инвентаре " + player.getInventory().size() + " предметов");
    }

    public void spawnEnemies(int count) {
        enemiesCount = count;
        for (int i=0;i<count;i++) {
            int[] coords = randomFreeCell();
            entities.add(new Enemy(coords[0], coords[1]));
        }
    }

    public void spawnItems(int count) {
        for (int i=0;i<count;i++) {
            int[] coords = randomFreeCell();
            entities.add(ItemFactory.createRandomItem(coords[0], coords[1]));
        }
    }

    public void movePlayer(int dx, int dy) {
        int newX = player.getX() + dx;
        int newY = player.getY() + dy;
        if (!isInside(newX, newY)) {
            System.out.println("Нельзя ходить в стену!");
            return;
        }

        Entity entity = getEntityAt(newX, newY);

        if (entity instanceof Enemy) {
            Enemy enemy = (Enemy) entity;
            player.attack(enemy);
            if (enemy.isAlive()) enemy.attack(player);
            else {
                System.out.println("Враг побеждён");
                enemiesCount--;
                entities.remove(enemy);
            }
        } else if (entity instanceof Item) {
            Item item = (Item) entity;
            System.out.println("Ты нашёл " + item.getVisibleName(player) + "! Взять? (y/n)");
            String input = System.console() != null ? System.console().readLine().trim().toLowerCase() : "y"; // безопасно для IDE
            if (input.equals("y")) {
                player.getInventory().add(item);
                entities.remove(item);
            }
            player.setX(newX);
            player.setY(newY);
        } else {
            player.setX(newX);
            player.setY(newY);
        }
    }

    private int[] randomFreeCell() {
        int x, y;
        do {
            x = random.nextInt(width);
            y = random.nextInt(height);
        } while (!isFree(x, y));
        return new int[]{x, y};
    }
}
