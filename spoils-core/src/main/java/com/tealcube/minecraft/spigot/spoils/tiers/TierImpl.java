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
package com.tealcube.minecraft.spigot.spoils.tiers;

import com.google.common.reflect.TypeToken;
import com.tealcube.minecraft.spigot.spoils.api.tiers.Tier;
import com.tealcube.minecraft.spigot.spoils.api.tiers.TierTrait;
import org.apache.commons.lang.Validate;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TierImpl implements Tier {

    private final String name;
    private final Map<TierTrait, Object> traitValueMap;

    public TierImpl(String name) {
        this.name = name;
        this.traitValueMap = new ConcurrentHashMap<>();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Object getTraitValue(TierTrait trait) {
        Validate.notNull(trait, "trait cannot be null");
        if (!traitValueMap.containsKey(trait)) {
            return trait.defaultValue();
        }
        return traitValueMap.get(trait);
    }

    @Override
    public void setTraitValue(TierTrait trait, Object value) {
        Validate.notNull(trait, "trait cannot be null");
        Validate.notNull(value, "value cannot be null");
        Class traitValueClazz = trait.valueClass();
        TypeToken traitTypeToken = TypeToken.of(traitValueClazz);
        Class valueClazz = value.getClass();
        TypeToken valueTypeToken = TypeToken.of(valueClazz);
        if (!traitTypeToken.isAssignableFrom(valueTypeToken) && !valueTypeToken.isAssignableFrom(valueTypeToken)) {
            throw new IllegalArgumentException("value is not of the right class type");
        }
        traitValueMap.put(trait, value);
    }

    @Override
    public boolean hasTraitValue(TierTrait trait) {
        Validate.notNull(trait, "trait cannot be null");
        return traitValueMap.containsKey(trait);
    }

    @Override
    public Map<TierTrait, Object> getTraitValues() {
        return new HashMap<>(traitValueMap);
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TierImpl)) {
            return false;
        }

        TierImpl tier = (TierImpl) o;

        return !(name != null ? !name.equals(tier.name) : tier.name != null);
    }

}
