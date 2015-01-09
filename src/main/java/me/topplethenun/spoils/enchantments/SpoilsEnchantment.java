package me.topplethenun.spoils.enchantments;

import org.bukkit.enchantments.Enchantment;

public class SpoilsEnchantment {

    private final Enchantment enchantment;
    private final int minimumLevel;
    private final int maximumLevel;

    public SpoilsEnchantment(Enchantment enchantment, int first, int second) {
        this.enchantment = enchantment;
        this.minimumLevel = Math.min(first, second);
        this.maximumLevel = Math.max(first, second);
    }

    public Enchantment getEnchantment() {
        return enchantment;
    }

    public int getMinimumLevel() {
        return minimumLevel;
    }

    public int getMaximumLevel() {
        return maximumLevel;
    }

}
