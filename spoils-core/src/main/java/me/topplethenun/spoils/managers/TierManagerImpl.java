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
package me.topplethenun.spoils.managers;

import com.google.common.base.Optional;
import me.topplethenun.spoils.api.managers.TierManager;
import me.topplethenun.spoils.api.tiers.Tier;
import me.topplethenun.spoils.tiers.StandardTierTrait;
import org.apache.commons.lang.Validate;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class TierManagerImpl implements TierManager {

    private final Map<String, Tier> tierMap;

    public TierManagerImpl() {
        tierMap = new ConcurrentHashMap<>();
    }

    @Override
    public Optional<Tier> find(String name) {
        Validate.notNull(name, "name cannot be null");
        return tierMap.containsKey(name.toLowerCase()) ? Optional.of(tierMap.get(name.toLowerCase())) :
                Optional.<Tier>absent();
    }

    @Override
    public void add(Tier param) {
        Validate.notNull(param, "param cannot be null");
        String name = param.getName();
        if (!tierMap.containsKey(name.toLowerCase())) {
            tierMap.put(name.toLowerCase(), param);
        }
    }

    @Override
    public void remove(Tier param) {
        Validate.notNull(param, "param cannot be null");
        String name = param.getName();
        if (tierMap.containsKey(name.toLowerCase())) {
            tierMap.remove(name.toLowerCase());
        }
    }

    @Override
    public void remove(String name) {
        Validate.notNull(name, "name cannot be null");
        if (!tierMap.containsKey(name.toLowerCase())) {
            tierMap.remove(name.toLowerCase());
        }
    }

    @Override
    public Set<Tier> getManaged() {
        return new HashSet<>(tierMap.values());
    }
}
