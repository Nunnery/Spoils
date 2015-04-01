/*
 * This file is part of spoils-common, licensed under the ISC License.
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
package com.tealcube.minecraft.spigot.spoils.common.enchantments;

import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.enchantments.Enchantment;

import java.util.List;

public class LeveledEnchantment {

    private final Enchantment enchantment;
    private final int minimumLevel;
    private final int maximumLevel;

    public LeveledEnchantment(Enchantment enchantment, int first, int second) {
        this.enchantment = enchantment;
        this.minimumLevel = Math.min(first, second);
        this.maximumLevel = Math.max(first, second);
    }

    public static LeveledEnchantment fromString(String s) {
        Preconditions.checkNotNull(s);
        List<String> splitString = Lists.newArrayList(Splitter.on(":").omitEmptyStrings().trimResults().split(s));
        Preconditions.checkState(splitString.size() > 1);
        if (splitString.size() == 2) {
            return new LeveledEnchantment(Enchantment.getByName(splitString.get(0)), NumberUtils.toInt(splitString.get
                    (1)), NumberUtils.toInt(splitString.get(1)));
        }
        return new LeveledEnchantment(Enchantment.getByName(splitString.get(0)),
                NumberUtils.toInt(splitString.get(1)), NumberUtils.toInt(splitString.get(2)));
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
    public int hashCode() {
        int result = enchantment != null ? enchantment.hashCode() : 0;
        result = 31 * result + minimumLevel;
        result = 31 * result + maximumLevel;
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LeveledEnchantment)) {
            return false;
        }

        LeveledEnchantment that = (LeveledEnchantment) o;

        return maximumLevel == that.maximumLevel &&
                minimumLevel == that.minimumLevel &&
                !(enchantment != null ? !enchantment.equals(that.enchantment) : that.enchantment != null);
    }

    @Override
    public String toString() {
        return (enchantment != null ? enchantment.getName() : "") + ":" + minimumLevel + ":" + maximumLevel;
    }

}
