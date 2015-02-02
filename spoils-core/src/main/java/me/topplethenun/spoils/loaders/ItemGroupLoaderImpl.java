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

import me.topplethenun.spoils.api.items.ItemGroup;
import me.topplethenun.spoils.api.loaders.ItemGroupLoader;
import me.topplethenun.spoils.items.ItemGroupImpl;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.HashSet;
import java.util.Set;

public class ItemGroupLoaderImpl implements ItemGroupLoader {

    private final YamlConfiguration configuration;

    public ItemGroupLoaderImpl(YamlConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    public Set<ItemGroup> load() {
        Set<ItemGroup> itemGroups = new HashSet<>();
        for (ItemGroup.Type type : ItemGroup.Type.values()) {
            if (type == ItemGroup.Type.UNKNOWN) {
                continue;
            }
            ConfigurationSection section = configuration.getConfigurationSection(type.getPath());
            if (section == null) {
                continue;
            }
            for (String key : section.getKeys(false)) {
                Set<Material> materials = new HashSet<>();
                for (String element : section.getStringList(key)) {
                    materials.add(Material.getMaterial(element));
                }
                ItemGroup itemGroup = new ItemGroupImpl(materials, false, type, key);
                itemGroups.add(itemGroup);
            }
        }
        return itemGroups;
    }
}
