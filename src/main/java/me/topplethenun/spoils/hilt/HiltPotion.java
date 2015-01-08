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

import org.bukkit.Material;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class HiltPotion extends HiltItemStack {

    public HiltPotion(PotionEffectType mainEffectType, Collection<PotionEffect> effects) {
        super(Material.POTION);
        setMainEffectType(mainEffectType);
        setEffects(effects);
    }

    public HiltPotion setMainEffectType(PotionEffectType type) {
        createItemMeta();
        if (getItemMeta() instanceof PotionMeta) {
            ((PotionMeta) getItemMeta()).setMainEffect(type);
        }
        return this;
    }

    public List<PotionEffect> getEffects() {
        createItemMeta();
        if (getItemMeta() instanceof PotionMeta && ((PotionMeta) getItemMeta()).hasCustomEffects()) {
            return new ArrayList<>(((PotionMeta) getItemMeta()).getCustomEffects());
        }
        return new ArrayList<>();
    }

    public HiltPotion setEffects(Collection<PotionEffect> effects) {
        createItemMeta();
        if (getItemMeta() instanceof PotionMeta) {
            if (((PotionMeta) getItemMeta()).hasCustomEffects()) {
                ((PotionMeta) getItemMeta()).clearCustomEffects();
            }
            for (PotionEffect potionEffect : effects) {
                ((PotionMeta) getItemMeta()).addCustomEffect(potionEffect, false);
            }
        }
        return this;
    }

}
