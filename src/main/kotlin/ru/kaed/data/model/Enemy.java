package ru.kaed.data.model;

import ru.kaed.data.repository.Combatant;
import ru.kaed.data.repository.Entity;

import java.util.Random;

public class Enemy implements Entity, Combatant {
    private int x, y, hp, attackPower;
    private final char symbol = 'E';
    private static final Random RANDOM = new Random();

    public Enemy(int x, int y) {
        this.x = x;
        this.y = y;
        this.hp = RANDOM.nextInt(16 - 9 + 1) + 9; // 9..16
        this.attackPower = RANDOM.nextInt(6 - 3 + 1) + 3; // 3..6
    }

    @Override
    public void attack(Combatant target) {
        target.takeDamage(attackPower);
        System.out.println("Враг атаковал на " + attackPower + " урона");
    }

    @Override
    public void takeDamage(int damage) {
        hp -= damage;
        System.out.println("У врага осталось " + hp + " hp");
    }

    @Override public boolean isAlive() { return hp > 0; }

    @Override public int getHp() { return hp; }
    @Override public void setHp(int hp) { this.hp = hp; }

    @Override public int getAttackPower() { return attackPower; }
    @Override public void setAttackPower(int attackPower) { this.attackPower = attackPower; }

    @Override public int getX() { return x; }
    @Override public void setX(int x) { this.x = x; }

    @Override public int getY() { return y; }
    @Override public void setY(int y) { this.y = y; }

    @Override public char getSymbol() { return symbol; }
}
