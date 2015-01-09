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
package me.topplethenun.spoils.enchantments;

import org.bukkit.enchantments.Enchantment;

public class SpoilsEnchantment {

    private final Enchantment enchantment;
    private final int minimumLevel;
    private final int maximumLevel;

    public SpoilsEnchantment(Enchantment enchantment, int first, int second) {
        this.enchantment = enchantment;
        this.minimumLevel = Math.min(first, second);
        this.maximumLevel = Math.max(first, second);
    }

    public Enchantment getEnchantment() {
        return enchantment;
    }

    public int getMinimumLevel() {
        return minimumLevel;
    }

    public int getMaximumLevel() {
        return maximumLevel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SpoilsEnchantment)) return false;

        SpoilsEnchantment that = (SpoilsEnchantment) o;

        return maximumLevel == that.maximumLevel &&
                minimumLevel == that.minimumLevel &&
                !(enchantment != null ? !enchantment.equals(that.enchantment) : that.enchantment != null);
    }

    @Override
    public int hashCode() {
        int result = enchantment != null ? enchantment.hashCode() : 0;
        result = 31 * result + minimumLevel;
        result = 31 * result + maximumLevel;
        return result;
    }
}
