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
package me.topplethenun.spoils;

import me.topplethenun.spoils.api.SpoilsPlugin;
import me.topplethenun.spoils.api.loaders.TierLoader;
import me.topplethenun.spoils.api.managers.TierManager;
import me.topplethenun.spoils.api.tiers.Tier;
import me.topplethenun.spoils.api.tiers.TierTrait;
import me.topplethenun.spoils.api.tiers.TierTraitRegistry;
import me.topplethenun.spoils.common.configuration.MasterConfiguration;
import me.topplethenun.spoils.common.configuration.SmartYamlConfiguration;
import me.topplethenun.spoils.common.configuration.VersionedSmartConfiguration;
import me.topplethenun.spoils.common.configuration.VersionedSmartYamlConfiguration;
import me.topplethenun.spoils.common.io.Debugger;
import me.topplethenun.spoils.loaders.TierLoaderImpl;
import me.topplethenun.spoils.managers.TierManagerImpl;
import me.topplethenun.spoils.tiers.StandardTierTrait;
import me.topplethenun.spoils.tiers.TierTraitRegistryImpl;

import java.io.File;
import java.util.Set;
import java.util.logging.Level;

public class SpoilsPluginImpl extends SpoilsPlugin {

    private TierTraitRegistry tierTraitRegistry;
    private Debugger debugger;
    private MasterConfiguration settings;
    private TierManager tierManager;

    @Override
    public void onDisable() {
        debug("Disabling v" + getDescription().getVersion());
        for (TierTrait trait : tierTraitRegistry.getRegisteredTraits()) {
            tierTraitRegistry.unregister(trait);
        }
    }

    @Override
    public void onEnable() {
        debugger = new Debugger(new File(getDataFolder(), "debug.log"));
        tierTraitRegistry = new TierTraitRegistryImpl();
        for (TierTrait trait : StandardTierTrait.values()) {
            tierTraitRegistry.register(trait);
        }

        settings = new MasterConfiguration();
        VersionedSmartYamlConfiguration configuration = new VersionedSmartYamlConfiguration
                (new File(getDataFolder(), "config.yml"), getResource("config.yml"),
                 VersionedSmartConfiguration.VersionUpdateType.BACKUP_AND_UPDATE);
        configuration.update();
        settings.load(configuration);
        configuration = new VersionedSmartYamlConfiguration(new File(getDataFolder(), "tiers.yml"),
                                                            getResource("tiers.yml"),
                                                            VersionedSmartConfiguration.VersionUpdateType.BACKUP_AND_UPDATE);
        configuration.update();

        tierManager = new TierManagerImpl();
        Set<Tier> tierSet = getNewTierLoader().load();
        for (Tier t : tierSet) {
            tierManager.add(t);
        }

        debug("Enabling v" + getDescription().getVersion());
    }

    @Override
    public void debug(String... messages) {
        debug(Level.INFO, messages);
    }

    public void debug(Level level, String... messages) {
        if (settings.getBoolean("config.debug")) {
            debugger.debug(level, messages);
        }
    }

    @Override
    public TierTraitRegistry getTierTraitRegistry() {
        return tierTraitRegistry;
    }

    @Override
    public MasterConfiguration getSettings() {
        return settings;
    }

    @Override
    public TierManager getTierManager() {
        return tierManager;
    }

    @Override
    public TierLoader getNewTierLoader() {
        SmartYamlConfiguration configuration = new SmartYamlConfiguration(new File(getDataFolder(), "tiers" +
                ".yml"));
        return new TierLoaderImpl(configuration, getTierTraitRegistry().getRegisteredTraits());
    }
}
