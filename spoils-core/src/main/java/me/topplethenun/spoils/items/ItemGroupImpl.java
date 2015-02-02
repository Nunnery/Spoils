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

import me.topplethenun.spoils.api.items.ItemGroup;
import org.bukkit.Material;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ItemGroupImpl implements ItemGroup {

    private final Set<Material> materials;
    private final boolean inverse;
    private final Type type;

    public ItemGroupImpl(Material... materials) {
        this(new HashSet<>(Arrays.asList(materials)));
    }

    public ItemGroupImpl(Set<Material> materials) {
        this(materials, false);
    }

    public ItemGroupImpl(Set<Material> materials, boolean inverse) {
        this(materials, inverse, Type.UNKNOWN);
    }

    public ItemGroupImpl(Set<Material> materials, boolean inverse, Type type) {
        this.materials = materials;
        this.inverse = inverse;
        this.type = type;
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
        return new ItemGroupImpl(getMaterials(), !isInverse());
    }

    @Override
    public Type getType() {
        return type;
    }

}
