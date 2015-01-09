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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SpoilsEnchantment)) return false;

        SpoilsEnchantment that = (SpoilsEnchantment) o;

        return maximumLevel == that.maximumLevel &&
                minimumLevel == that.minimumLevel &&
                !(enchantment != null ? !enchantment.equals(that.enchantment) : that.enchantment != null);
    }

    @Override
    public int hashCode() {
        int result = enchantment != null ? enchantment.hashCode() : 0;
        result = 31 * result + minimumLevel;
        result = 31 * result + maximumLevel;
        return result;
    }
}
