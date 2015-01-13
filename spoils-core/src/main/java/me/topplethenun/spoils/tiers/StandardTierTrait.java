/*
 * This file is part of spoils-core, licensed under the ISC License.
 *
 * Copyright (c) 2014 Richard Harrah
 *
 * Permission to use, copy, modify, and/or distribute this software for any purpose with or without fee is hereby granted,
 * provided that the above copyright notice and this permission notice appear in all copies.
 *
 * THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES WITH REGARD TO THIS SOFTWARE INCLUDING ALL
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY SPECIAL, DIRECT,
 * INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN
 * ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF OR IN CONNECTION WITH THE USE OR PERFORMANCE OF
 * THIS SOFTWARE.
 */
package me.topplethenun.spoils.tiers;

import me.topplethenun.spoils.api.tiers.TierTrait;
import me.topplethenun.spoils.common.enchantments.LeveledEnchantment;
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
    BASE_ENCHANTMENTS("base-enchantments", Set.class, new HashSet<LeveledEnchantment>()),
    BONUS_ENCHANTMENTS("bonus-enchantments", Set.class, new HashSet<LeveledEnchantment>()),
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
