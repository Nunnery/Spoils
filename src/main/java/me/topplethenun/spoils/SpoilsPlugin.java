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
package me.topplethenun.spoils;

import me.topplethenun.spoils.io.Debugger;
import me.topplethenun.spoils.tiers.StandardTierTrait;
import me.topplethenun.spoils.tiers.TierTrait;
import me.topplethenun.spoils.tiers.TierTraitRegistry;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class SpoilsPlugin extends JavaPlugin {

    private TierTraitRegistry tierTraitRegistry;
    private Debugger debugger;

    @Override
    public void onEnable() {
        debugger = new Debugger(new File(getDataFolder(), "debug.log"));
        tierTraitRegistry = new TierTraitRegistry();
        for (TierTrait trait : StandardTierTrait.values()) {
            tierTraitRegistry.register(trait);
        }

        debugger.debug("Enabling v" + getDescription().getVersion());
    }

    @Override
    public void onDisable() {
        debugger.debug("Disabling v" + getDescription().getVersion());
        for (TierTrait trait : tierTraitRegistry.getRegisteredTraits()) {
            tierTraitRegistry.unregister(trait);
        }
    }

    public TierTraitRegistry getTierTraitRegistry() {
        return tierTraitRegistry;
    }

    public Debugger getDebugger() {
        return debugger;
    }
}
