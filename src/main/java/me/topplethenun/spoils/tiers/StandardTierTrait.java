package me.topplethenun.spoils.tiers;

import me.topplethenun.spoils.enchantments.SpoilsEnchantment;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public enum StandardTierTrait implements TierTrait {

    DISPLAY_NAME("display-name", String.class, ""),
    DISPLAY_COLOR("display-color", ChatColor.class, ChatColor.WHITE),
    IDENTIFICATION_COLOR("identification-color", ChatColor.class, ChatColor.WHITE),
    BASE_LORE("base-lore", List.class, new ArrayList<String>()),
    BONUS_LORE("bonus-lore", List.class, new ArrayList<String>()),
    MINIMUM_BONUS_LORE("bonus-lore-minimum", Integer.class, 0),
    MAXIMUM_BONUS_LORE("bonus-lore-maximum", Integer.class, 0),
    BASE_ENCHANTMENTS("base-enchantments", Set.class, new HashSet<SpoilsEnchantment>()),
    BONUS_ENCHANTMENTS("bonus-enchantments", Set.class, new HashSet<SpoilsEnchantment>()),
    MINIMUM_BONUS_ENCHANTMENTS("bonus-enchantments-minimum", Integer.class, 0),
    MAXIMUM_BONUS_ENCHANTMENTS("bonus-enchantments-maximum", Integer.class, 0);

    private final String key;
    private final Class valueClass;
    private final Object defaultValue;

    private StandardTierTrait(String key, Class valueClass, Object defaultValue) {
        this.key = key;
        this.valueClass = valueClass;
        this.defaultValue = defaultValue;
    }

    @Override
    public String key() {
        return key;
    }

    @Override
    public Class valueClass() {
        return valueClass;
    }

    @Override
    public Object defaultValue() {
        return defaultValue;
    }

}
