package ru.kaed.data.model;

import ru.kaed.data.repository.Combatant;
import ru.kaed.data.repository.Entity;

import java.util.ArrayList;
import java.util.List;

public class Player implements Entity, Combatant {
    private int x, y, hp, attackPower;
    private final char symbol = '@';
    private final List<Item> inventory = new ArrayList<>();

    private Boolean healthPotionIdentified = null;
    private Boolean attackPotionIdentified = null;

    public Player(int x, int y) {
        this.x = x;
        this.y = y;
        this.hp = 30;
        this.attackPower = 5;
    }

    @Override
    public void attack(Combatant target) {
        System.out.println("Ты атаковал моба на " + attackPower + " урона");
        target.takeDamage(attackPower);
    }

    @Override
    public void takeDamage(int damage) {
        hp -= damage;
        System.out.println("Ты был атакован на " + damage + " урона(( \nУ тебя осталось " + hp + " hp");
    }

    @Override
    public boolean isAlive() {
        return hp > 0;
    }

    @Override
    public int getHp() {
        return hp;
    }

    @Override
    public void setHp(int hp) {
        this.hp = hp;
    }

    @Override
    public int getAttackPower() {
        return attackPower;
    }

    @Override
    public void setAttackPower(int attackPower) {
        this.attackPower = attackPower;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public void setX(int x) {
        this.x = x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public void setY(int y) {
        this.y = y;
    }

    @Override
    public char getSymbol() {
        return symbol;
    }

    public List<Item> getInventory() {
        return inventory;
    }

    public Boolean getHealthPotionIdentified() {
        return healthPotionIdentified;
    }

    public void setHealthPotionIdentified(Boolean healthPotionIdentified) {
        this.healthPotionIdentified = healthPotionIdentified;
    }

    public Boolean getAttackPotionIdentified() {
        return attackPotionIdentified;
    }

    public void setAttackPotionIdentified(Boolean attackPotionIdentified) {
        this.attackPotionIdentified = attackPotionIdentified;
    }
}
