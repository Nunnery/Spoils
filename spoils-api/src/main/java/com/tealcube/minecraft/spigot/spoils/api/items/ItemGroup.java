/*
 * This file is part of spoils-api, licensed under the ISC License.
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
package com.tealcube.minecraft.spigot.spoils.api.items;

import org.bukkit.Material;

import java.util.Set;

public interface ItemGroup {

    String getName();

    Type getType();

    Set<Material> getMaterials();

    boolean isInverse();

    ItemGroup inverse();

    enum Type {
        TOOL("tools"),
        ARMOR("armor"),
        MATERIAL("materials"),
        UNKNOWN("");

        private final String path;
        private Type(String path) {
            this.path = path;
        }

        public String getPath() {
            return path;
        }

        public static Type fromString(String s) {
            for (Type val : values()) {
                if (val.getPath().equals(s)) {
                    return val;
                }
            }
            return UNKNOWN;
        }
    }

}
