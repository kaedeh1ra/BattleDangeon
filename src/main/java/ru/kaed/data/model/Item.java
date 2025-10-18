package ru.kaed.data.model;

import ru.kaed.data.repository.Entity;

public class Item implements Entity {
    private int x, y;
    private final char symbol = 'I';
    private final ItemEffect effect;

    public Item(int x, int y, ItemEffect effect) {
        this.x = x;
        this.y = y;
        this.effect = effect;
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

    public String getVisibleName(Player player) {
        if (effect.getPotionType() == PotionType.HEALTH) {
            if (player.getHealthPotionIdentified() == null) return "Зелье здоровья";
            else if (player.getHealthPotionIdentified()) return "Зелье здоровья (яд)";
            else return "Зелье здоровья (лечение)";
        } else {
            if (player.getAttackPotionIdentified() == null) return "Зелье атаки";
            else if (player.getAttackPotionIdentified()) return "Зелье атаки (дебафф)";
            else return "Зелье атаки (бафф)";
        }
    }

    public void use(Player player) {
        String message = effect.apply(player);

        if (effect.isNegative()) {
            if (effect.getPotionType() == PotionType.HEALTH) player.setHealthPotionIdentified(true);
            else player.setAttackPotionIdentified(true);
        }

        System.out.println("Вы использовали " + getVisibleName(player) + ". " + message);
    }

    public ItemEffect getEffect() {
        return effect;
    }
}

abstract class ItemEffect {
    private final PotionType potionType;

    public ItemEffect(PotionType potionType) {
        this.potionType = potionType;
    }

    public PotionType getPotionType() {
        return potionType;
    }

    public abstract String apply(Player player);

    public abstract boolean isNegative();

    public static class Heal extends ItemEffect {
        private final int amount;

        public Heal(int amount) {
            super(PotionType.HEALTH);
            this.amount = amount;
        }

        @Override
        public String apply(Player player) {
            player.setHp(player.getHp() + amount);
            return "Восстановлено " + amount + " HP (HP=" + player.getHp() + ")";
        }

        @Override
        public boolean isNegative() {
            return false;
        }
    }

    public static class Poison extends ItemEffect {
        private final int amount;

        public Poison(int amount) {
            super(PotionType.HEALTH);
            this.amount = amount;
        }

        @Override
        public String apply(Player player) {
            player.setHp(player.getHp() - amount);
            return "Отравление: -" + amount + " HP (HP=" + player.getHp() + ")";
        }

        @Override
        public boolean isNegative() {
            return true;
        }
    }

    public static class BuffAttack extends ItemEffect {
        private final int amount;

        public BuffAttack(int amount) {
            super(PotionType.ATTACK);
            this.amount = amount;
        }

        @Override
        public String apply(Player player) {
            player.setAttackPower(player.getAttackPower() + amount);
            return "Атака +" + amount + " (ATK=" + player.getAttackPower() + ")";
        }

        @Override
        public boolean isNegative() {
            return false;
        }
    }

    public static class DebuffAttack extends ItemEffect {
        private final int amount;

        public DebuffAttack(int amount) {
            super(PotionType.ATTACK);
            this.amount = amount;
        }

        @Override
        public String apply(Player player) {
            player.setAttackPower(player.getAttackPower() - amount);
            return "Атака -" + amount + " (ATK=" + player.getAttackPower() + ")";
        }

        @Override
        public boolean isNegative() {
            return true;
        }
    }
}

enum PotionType {
    HEALTH, ATTACK
}