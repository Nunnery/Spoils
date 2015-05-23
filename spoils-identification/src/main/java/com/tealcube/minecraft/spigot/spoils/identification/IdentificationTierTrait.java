/*
 * This file is part of spoils-identification, licensed under the ISC License.
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
package com.tealcube.minecraft.spigot.spoils.identification;

import com.tealcube.minecraft.spigot.spoils.api.tiers.TierTrait;

public enum IdentificationTierTrait implements TierTrait {

    IDENTIFICATION_CHANCE("identification-chance", Double.class, 0.0D);

    private final String key;
    private final Class valueClass;
    private final Object defaultValue;

    IdentificationTierTrait(String key, Class valueClass, Object defaultValue) {
        this.key = key;
        this.valueClass = valueClass;
        this.defaultValue = defaultValue;
    }

    @Override
    public String key() {
        return key;
    }

    @Override
    public Class valueClass() {
        return valueClass;
    }

    @Override
    public Object defaultValue() {
        return defaultValue;
    }

}
