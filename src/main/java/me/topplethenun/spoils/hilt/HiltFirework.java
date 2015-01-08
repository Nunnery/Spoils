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

import org.bukkit.FireworkEffect;
import org.bukkit.Material;
import org.bukkit.inventory.meta.FireworkMeta;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class HiltFirework extends HiltItemStack {

    public HiltFirework(Collection<FireworkEffect> fireworkEffects, int power) {
        super(Material.FIREWORK);
        setFireworkEffects(fireworkEffects);
        setPower(power);
    }

    public HiltFirework setPower(int power) {
        createItemMeta();
        if (getItemMeta() instanceof FireworkMeta) {
            ((FireworkMeta) getItemMeta()).setPower(power);
        }
        return this;
    }

    public List<FireworkEffect> getFireworkEffects() {
        createItemMeta();
        if (getItemMeta() instanceof FireworkMeta && ((FireworkMeta) getItemMeta()).hasEffects()) {
            return new ArrayList<>(((FireworkMeta) getItemMeta()).getEffects());
        }
        return new ArrayList<>();
    }

    public HiltFirework setFireworkEffects(Collection<FireworkEffect> effects) {
        createItemMeta();
        if (getItemMeta() instanceof FireworkMeta) {
            ((FireworkMeta) getItemMeta()).addEffects(effects);
        }
        return this;
    }

}
