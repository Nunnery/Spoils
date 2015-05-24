/*
 * This file is part of spoils-api, licensed under the ISC License.
 *
 * Copyright (c) 2014-2015 Richard Harrah
 *
 * Permission to use, copy, modify, and/or distribute this software for any purpose with or without fee is hereby
 * granted,
 * provided that the above copyright notice and this permission notice appear in all copies.
 *
 * THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES WITH REGARD TO THIS SOFTWARE INCLUDING ALL
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY SPECIAL, DIRECT,
 * INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER
 * IN AN
 * ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF OR IN CONNECTION WITH THE USE OR
 * PERFORMANCE OF
 * THIS SOFTWARE.
 */
package com.tealcube.minecraft.spigot.spoils.api.tiers;

import com.google.common.base.Optional;
import com.tealcube.minecraft.spigot.spoils.api.SpoilsPlugin;
import com.tealcube.minecraft.spigot.spoils.api.items.ItemGroup;
import com.tealcube.minecraft.spigot.spoils.common.enchantments.LeveledEnchantment;
import com.tealcube.minecraft.spigot.spoils.common.utils.TextUtils;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public enum StandardTierTrait implements TierTrait {

    DISPLAY_NAME("display-name", String.class, "") {
        @Override
        public Object load(ConfigurationSection baseSection) {
            return baseSection.getString(key(), (String) defaultValue());
        }
    },
    PRIMARY_COLOR("primary-color", ChatColor.class, ChatColor.WHITE) {
        @Override
        public Object load(ConfigurationSection baseSection) {
            return TextUtils.convertTag(baseSection.getString(key(), (String) defaultValue()));
        }
    },
    SECONDARY_COLOR("secondary-color", ChatColor.class, ChatColor.WHITE) {
        @Override
        public Object load(ConfigurationSection baseSection) {
            return TextUtils.convertTag(baseSection.getString(key(), (String) defaultValue()));
        }
    },
    FLAVOR_TEXT("flavor-text", List.class, new ArrayList<String>()) {
        @Override
        public Object load(ConfigurationSection baseSection) {
            return baseSection.getStringList(key());
        }
    },
    BASE_ENCHANTMENTS("base-enchantments", Set.class, new HashSet<LeveledEnchantment>()) {
        @Override
        public Object load(ConfigurationSection baseSection) {
            Set<LeveledEnchantment> baseEnch = new HashSet<>();
            for (String s : baseSection.getStringList(key())) {
                baseEnch.add(LeveledEnchantment.fromString(s));
            }
            return baseEnch;
        }
    },
    BONUS_ENCHANTMENTS("bonus-enchantments", Set.class, new HashSet<LeveledEnchantment>()) {
        @Override
        public Object load(ConfigurationSection baseSection) {
            Set<LeveledEnchantment> bonusEnch = new HashSet<>();
            for (String s : baseSection.getStringList(key())) {
                bonusEnch.add(LeveledEnchantment.fromString(s));
            }
            return bonusEnch;
        }
    },
    MINIMUM_BONUS_ENCHANTMENTS("bonus-enchantments-minimum", Integer.class, 0) {
        @Override
        public Object load(ConfigurationSection baseSection) {
            return baseSection.getInt(key(), (Integer) defaultValue());
        }
    },
    MAXIMUM_BONUS_ENCHANTMENTS("bonus-enchantments-maximum", Integer.class, 0) {
        @Override
        public Object load(ConfigurationSection baseSection) {
            return baseSection.getInt(key(), (Integer) defaultValue());
        }
    },
    ALLOWED_ITEM_GROUPS("allowed-groups", List.class, new ArrayList<ItemGroup>()) {
        @Override
        public Object load(ConfigurationSection baseSection) {
            List<ItemGroup> allowedItemGroups = new ArrayList<>();
            for (String s : baseSection.getStringList(key())) {
                Optional<ItemGroup> itemGroupOptional = SpoilsPlugin.getInstance().getItemGroupManager().find(s);
                if (!itemGroupOptional.isPresent()) {
                    continue;
                }
                ItemGroup itemGroup = itemGroupOptional.get();
                allowedItemGroups.add(!itemGroup.isInverse() ? itemGroup : itemGroup.inverse());
            }
            return allowedItemGroups;
        }
    },
    DISALLOWED_ITEM_GROUPS("disallowed-groups", List.class, new ArrayList<ItemGroup>()) {
        @Override
        public Object load(ConfigurationSection baseSection) {
            List<ItemGroup> disallowedItemGroups = new ArrayList<>();
            for (String s : baseSection.getStringList(key())) {
                Optional<ItemGroup> itemGroupOptional = SpoilsPlugin.getInstance().getItemGroupManager().find(s);
                if (!itemGroupOptional.isPresent()) {
                    continue;
                }
                ItemGroup itemGroup = itemGroupOptional.get();
                disallowedItemGroups.add(itemGroup.isInverse() ? itemGroup : itemGroup.inverse());
            }
            return disallowedItemGroups;
        }
    };

    private final String key;
    private final Class valueClass;
    private final Object defaultValue;

    StandardTierTrait(String key, Class valueClass, Object defaultValue) {
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
