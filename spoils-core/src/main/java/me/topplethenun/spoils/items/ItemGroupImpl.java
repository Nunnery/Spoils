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
package me.topplethenun.spoils.items;

import com.google.common.base.Preconditions;
import me.topplethenun.spoils.api.items.ItemGroup;
import org.bukkit.Material;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ItemGroupImpl implements ItemGroup {

    private final Set<Material> materials;
    private final boolean inverse;
    private final Type type;
    private final String name;

    public ItemGroupImpl(String name, Material... materials) {
        this(new HashSet<>(Arrays.asList(materials)), name);
    }

    public ItemGroupImpl(Set<Material> materials, String name) {
        this(materials, false, name);
    }

    public ItemGroupImpl(Set<Material> materials, boolean inverse, String name) {
        this(materials, inverse, Type.UNKNOWN, name);
    }

    public ItemGroupImpl(Set<Material> materials, boolean inverse, Type type, String name) {
        Preconditions.checkNotNull(materials);
        Preconditions.checkNotNull(type);
        Preconditions.checkNotNull(name);
        this.materials = materials;
        this.inverse = inverse;
        this.type = type;
        this.name = name;
    }

    @Override
    public Set<Material> getMaterials() {
        return materials;
    }

    @Override
    public boolean isInverse() {
        return inverse;
    }

    @Override
    public ItemGroup inverse() {
        return new ItemGroupImpl(getMaterials(), !isInverse(), name);
    }

    @Override
    public Type getType() {
        return type;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ItemGroupImpl)) {
            return false;
        }

        ItemGroupImpl itemGroup = (ItemGroupImpl) o;

        return !(name != null ? !name.equals(itemGroup.name) : itemGroup.name != null);
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}
