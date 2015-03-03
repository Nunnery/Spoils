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
package com.tealcube.minecraft.spigot.spoils.loaders;

import com.google.common.base.Optional;
import com.tealcube.minecraft.spigot.spoils.SpoilsPluginImpl;
import com.tealcube.minecraft.spigot.spoils.tiers.TierImpl;
import com.tealcube.minecraft.spigot.spoils.api.items.ItemGroup;
import com.tealcube.minecraft.spigot.spoils.api.loaders.TierLoader;
import com.tealcube.minecraft.spigot.spoils.api.tiers.StandardTierTrait;
import com.tealcube.minecraft.spigot.spoils.api.tiers.Tier;
import com.tealcube.minecraft.spigot.spoils.api.tiers.TierTrait;
import com.tealcube.minecraft.spigot.spoils.common.enchantments.LeveledEnchantment;
import com.tealcube.minecraft.spigot.spoils.common.utils.TextUtils;
import org.apache.commons.lang.Validate;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TierLoaderImpl implements TierLoader {

    private final YamlConfiguration configuration;
    private final Set<TierTrait> traits;

    public TierLoaderImpl(YamlConfiguration configuration, Set<TierTrait> traits) {
        this.configuration = configuration;
        this.traits = traits;
    }

    @Override
    public Set<Tier> load() {
        Validate.notNull(configuration, "configuration can't be null");
        Validate.notNull(traits, "traits can't be null");
        Set<Tier> tiers = new HashSet<>();
        for (String key : configuration.getKeys(false)) {
            if (!configuration.isConfigurationSection(key)) {
                continue;
            }
            ConfigurationSection cs = configuration.getConfigurationSection(key);
            TierImpl tier = new TierImpl(key);
            for (StandardTierTrait trait : StandardTierTrait.values()) {
                switch (trait) {
                    case DISPLAY_NAME:
                        tier.setTraitValue(trait, cs.getString(trait.key()));
                        break;
                    case PRIMARY_COLOR:
                        tier.setTraitValue(trait, TextUtils.convertTag(cs.getString(trait.key())));
                        break;
                    case SECONDARY_COLOR:
                        tier.setTraitValue(trait, TextUtils.convertTag(cs.getString(trait.key())));
                        break;
                    case FLAVOR_TEXT:
                        tier.setTraitValue(trait, cs.getStringList(trait.key()));
                        break;
                    case BASE_ENCHANTMENTS:
                        Set<LeveledEnchantment> baseEnch = new HashSet<>();
                        for (String s : cs.getStringList(trait.key())) {
                            baseEnch.add(LeveledEnchantment.fromString(s));
                        }
                        tier.setTraitValue(trait, baseEnch);
                        break;
                    case BONUS_ENCHANTMENTS:
                        Set<LeveledEnchantment> bonusEnch = new HashSet<>();
                        for (String s : cs.getStringList(trait.key())) {
                            bonusEnch.add(LeveledEnchantment.fromString(s));
                        }
                        tier.setTraitValue(trait, bonusEnch);
                        break;
                    case MINIMUM_BONUS_ENCHANTMENTS:
                        tier.setTraitValue(trait, cs.getInt(trait.key()));
                        break;
                    case MAXIMUM_BONUS_ENCHANTMENTS:
                        tier.setTraitValue(trait, cs.getInt(trait.key()));
                        break;
                    case ALLOWED_ITEM_GROUPS:
                        List<ItemGroup> allowedItemGroups = new ArrayList<>();
                        for (String s : cs.getStringList(trait.key())) {
                            Optional<ItemGroup> itemGroupOptional = SpoilsPluginImpl.getInstance()
                                    .getItemGroupManager().find(s);
                            if (!itemGroupOptional.isPresent()) {
                                continue;
                            }
                            ItemGroup itemGroup = itemGroupOptional.get();
                            allowedItemGroups.add(!itemGroup.isInverse() ? itemGroup : itemGroup.inverse());
                        }
                        tier.setTraitValue(trait, allowedItemGroups);
                        break;
                    case DISALLOWED_ITEM_GROUPS:
                        List<ItemGroup> disallowedItemGroups = new ArrayList<>();
                        for (String s : cs.getStringList(trait.key())) {
                            Optional<ItemGroup> itemGroupOptional = SpoilsPluginImpl.getInstance()
                                    .getItemGroupManager().find(s);
                            if (!itemGroupOptional.isPresent()) {
                                continue;
                            }
                            ItemGroup itemGroup = itemGroupOptional.get();
                            disallowedItemGroups.add(itemGroup.isInverse() ? itemGroup : itemGroup.inverse());
                        }
                        tier.setTraitValue(trait, disallowedItemGroups);
                        break;
                }
            }
            for (TierTrait trait : traits) {
                Object value = cs.get(trait.key(), trait.defaultValue());
                tier.setTraitValue(trait, value);
            }
            tiers.add(tier);
        }
        return tiers;
    }

}
