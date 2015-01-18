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
package me.topplethenun.spoils.loaders;

import me.topplethenun.spoils.api.loaders.TierLoader;
import me.topplethenun.spoils.api.tiers.Tier;
import me.topplethenun.spoils.api.tiers.TierTrait;
import me.topplethenun.spoils.tiers.TierImpl;
import org.apache.commons.lang.Validate;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.HashSet;
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
            for (TierTrait trait : traits) {
                Object value;
                if (trait.valueClass().equals(ChatColor.class)) {
                    value = ChatColor.valueOf(cs.getString(trait.key()));
                } else {
                    value = cs.get(trait.key(), trait.defaultValue());
                }
                tier.setTraitValue(trait, value);
            }
            tiers.add(tier);
        }
        return tiers;
    }

}
