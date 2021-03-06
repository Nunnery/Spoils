/*
 * This file is part of spoils-core, licensed under the ISC License.
 *
 * Copyright (c) 2014-2015 Richard Harrah
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
package com.tealcube.minecraft.spigot.spoils.managers;

import com.google.common.base.Optional;
import com.tealcube.minecraft.spigot.spoils.api.items.ItemGroup;
import com.tealcube.minecraft.spigot.spoils.api.managers.ItemGroupManager;
import org.apache.commons.lang.Validate;
import org.bukkit.Material;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ItemGroupManagerImpl implements ItemGroupManager {

    private final Map<String, ItemGroup> itemGroupMap;

    public ItemGroupManagerImpl() {
        itemGroupMap = new ConcurrentHashMap<>();
    }

    @Override
    public Optional<ItemGroup> find(String name) {
        Validate.notNull(name, "name cannot be null");
        return itemGroupMap.containsKey(name) ? Optional.of(itemGroupMap.get(name)) : Optional.<ItemGroup>absent();
    }

    @Override
    public void add(ItemGroup param) {
        Validate.notNull(param, "param cannot be null");
        if (!itemGroupMap.containsKey(param.getName().toLowerCase())) {
            itemGroupMap.put(param.getName().toLowerCase(), param);
        }
    }

    @Override
    public void remove(ItemGroup param) {
        Validate.notNull(param, "param cannot be null");
        if (!itemGroupMap.containsKey(param.getName().toLowerCase())) {
            itemGroupMap.remove(param.getName().toLowerCase());
        }
    }

    @Override
    public void remove(String name) {
        Validate.notNull(name, "name cannot be null");
        if (!itemGroupMap.containsKey(name.toLowerCase())) {
            itemGroupMap.remove(name.toLowerCase());
        }
    }

    @Override
    public Set<ItemGroup> getManaged() {
        return new HashSet<>(itemGroupMap.values());
    }

    @Override
    public Set<ItemGroup> findItemGroups(Material material) {
        Set<ItemGroup> itemGroups = new HashSet<>();
        for (ItemGroup val : itemGroupMap.values()) {
            if (val.getMaterials().contains(material)) {
                itemGroups.add(val);
            }
        }
        return itemGroups;
    }

    @Override
    public Set<ItemGroup> findItemGroups(Material material, ItemGroup.Type type) {
        Set<ItemGroup> itemGroups = new HashSet<>();
        for (ItemGroup val : itemGroupMap.values()) {
            if (val.getType() == type && val.getMaterials().contains(material)) {
                itemGroups.add(val);
            }
        }
        return itemGroups;
    }

}
