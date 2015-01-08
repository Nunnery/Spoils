/*
 * This file is part of Spoils, licensed under the ISC License.
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
package me.topplethenun.spoils.hilt;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class HiltLeatherArmor extends HiltItemStack {

    public HiltLeatherArmor(LeatherArmorType type, Color color) {
        super(type.getMat());
        setColor(color);
    }

    public Color getColor() {
        createItemMeta();
        if (getItemMeta() instanceof LeatherArmorMeta) {
            return ((LeatherArmorMeta) getItemMeta()).getColor();
        }
        return null;
    }

    public HiltLeatherArmor setColor(Color color) {
        createItemMeta();
        if (getItemMeta() instanceof LeatherArmorMeta) {
            ((LeatherArmorMeta) getItemMeta()).setColor(color);
        }
        return this;
    }

    enum LeatherArmorType {
        HELMET(Material.LEATHER_HELMET),
        CHESTPLATE(Material.LEATHER_CHESTPLATE),
        LEGGINGS(Material.LEATHER_LEGGINGS),
        BOOTS(Material.LEATHER_BOOTS);

        private final Material mat;

        private LeatherArmorType(Material mat) {
            this.mat = mat;
        }

        public Material getMat() {
            return mat;
        }
    }

}
