package ru.kaed.data.repository;

public interface Combatant {
    int getHp();
    void setHp(int hp);

    int getAttackPower();
    void setAttackPower(int attackPower);

    void attack(Combatant target);
    void takeDamage(int damage);

    default boolean isAlive() {
        return getHp() > 0;
    }
}
