package ru.kaed.data.model;

import java.util.Random;

public class ItemFactory {
    private static final double HP_POTION_VISIBLE_PROB = 0.6;
    private static final double POSITIVE_HP_PROB = 0.7;
    private static final double POSITIVE_ATK_PROB = 0.7;

    private static final int[] HEAL_RANGE = {3, 10};
    private static final int[] POISON_RANGE = {2, 6};
    private static final int[] BUFF_ATTACK_RANGE = {1, 3};
    private static final int[] DEBUFF_ATTACK_RANGE = {1, 3};

    private static final Random RANDOM = new Random();

    public static Item createRandomItem(int x, int y) {
        boolean showHpPotion = RANDOM.nextDouble() < HP_POTION_VISIBLE_PROB;

        ItemEffect effect;
        if (showHpPotion) {
            if (RANDOM.nextDouble() < POSITIVE_HP_PROB)
                effect = new ItemEffect.Heal(RANDOM.nextInt(HEAL_RANGE[1] - HEAL_RANGE[0] + 1) + HEAL_RANGE[0]);
            else
                effect = new ItemEffect.Poison(RANDOM.nextInt(POISON_RANGE[1] - POISON_RANGE[0] + 1) + POISON_RANGE[0]);
        } else {
            if (RANDOM.nextDouble() < POSITIVE_ATK_PROB)
                effect = new ItemEffect.BuffAttack(RANDOM.nextInt(BUFF_ATTACK_RANGE[1] - BUFF_ATTACK_RANGE[0] + 1) + BUFF_ATTACK_RANGE[0]);
            else
                effect = new ItemEffect.DebuffAttack(RANDOM.nextInt(DEBUFF_ATTACK_RANGE[1] - DEBUFF_ATTACK_RANGE[0] + 1) + DEBUFF_ATTACK_RANGE[0]);
        }

        return new Item(x, y, effect);
    }
}
