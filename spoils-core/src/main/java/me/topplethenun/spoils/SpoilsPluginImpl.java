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

import me.topplethenun.config.MasterConfiguration;
import me.topplethenun.config.SmartYamlConfiguration;
import me.topplethenun.config.VersionedSmartConfiguration;
import me.topplethenun.config.VersionedSmartYamlConfiguration;
import me.topplethenun.spoils.api.SpoilsPlugin;
import me.topplethenun.spoils.api.items.ItemGroup;
import me.topplethenun.spoils.api.loaders.ItemGroupLoader;
import me.topplethenun.spoils.api.loaders.TierLoader;
import me.topplethenun.spoils.api.managers.ItemGroupManager;
import me.topplethenun.spoils.api.managers.TierManager;
import me.topplethenun.spoils.api.names.ResourceTable;
import me.topplethenun.spoils.api.names.ResourceType;
import me.topplethenun.spoils.api.tiers.Tier;
import me.topplethenun.spoils.api.tiers.TierTrait;
import me.topplethenun.spoils.api.tiers.TierTraitRegistry;
import me.topplethenun.spoils.common.io.Debugger;
import me.topplethenun.spoils.common.io.SmartTextFile;
import me.topplethenun.spoils.loaders.ItemGroupLoaderImpl;
import me.topplethenun.spoils.loaders.TierLoaderImpl;
import me.topplethenun.spoils.managers.ItemGroupManagerImpl;
import me.topplethenun.spoils.managers.TierManagerImpl;
import me.topplethenun.spoils.names.ResourceTableImpl;
import me.topplethenun.spoils.tiers.TierTraitRegistryImpl;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.util.Set;
import java.util.logging.Level;

public class SpoilsPluginImpl extends SpoilsPlugin {

    private static SpoilsPlugin instance;
    private TierTraitRegistry tierTraitRegistry;
    private Debugger debugger;
    private MasterConfiguration settings;
    private TierManager tierManager;
    private ItemGroupManager itemGroupManager;
    private ResourceTable resourceTable;

    public static SpoilsPlugin getInstance() {
        return instance;
    }

    @Override
    public void onDisable() {
        debug("Disabling v" + getDescription().getVersion());
        for (TierTrait trait : tierTraitRegistry.getRegisteredTraits()) {
            tierTraitRegistry.unregister(trait);
        }
    }

    @Override
    public void onEnable() {
        instance = this;
        debugger = new Debugger(new File(getDataFolder(), "debug.log"));
        tierTraitRegistry = new TierTraitRegistryImpl();

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
        configuration = new VersionedSmartYamlConfiguration(new File(getDataFolder(), "groups.yml"),
                getResource("groups.yml"), VersionedSmartConfiguration.VersionUpdateType.BACKUP_AND_UPDATE);
        configuration.update();

        resourceTable = new ResourceTableImpl();

        loadNames();

        tierManager = new TierManagerImpl();
        itemGroupManager = new ItemGroupManagerImpl();

        Set<ItemGroup> itemGroupSet = getNewItemGroupLoader().load();
        for (ItemGroup ig : itemGroupSet) {
            getItemGroupManager().add(ig);
        }
        debug("Loaded item groups: " + itemGroupSet.size());

        getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
            @Override
            public void run() {
                Set<Tier> tierSet = getNewTierLoader().load();
                for (Tier t : tierSet) {
                    getTierManager().add(t);
                }
                debug("Loaded tiers: " + tierSet.size());
            }
        }, 20L);

        debug("Enabling v" + getDescription().getVersion());
    }

    public void debug(Level level, String... messages) {
        if (settings.getBoolean("config.debug")) {
            debugger.debug(level, messages);
        }
    }

    @Override
    public void debug(String... messages) {
        debug(Level.INFO, messages);
    }

    @Override
    public MasterConfiguration getSettings() {
        return settings;
    }

    @Override
    public TierTraitRegistry getTierTraitRegistry() {
        return tierTraitRegistry;
    }

    @Override
    public TierManager getTierManager() {
        return tierManager;
    }

    @Override
    public TierLoader getNewTierLoader() {
        SmartYamlConfiguration configuration = new SmartYamlConfiguration(
                new File(getDataFolder(), "tiers.yml"));
        return new TierLoaderImpl(configuration, getTierTraitRegistry().getRegisteredTraits());
    }

    @Override
    public ItemGroupManager getItemGroupManager() {
        return itemGroupManager;
    }

    @Override
    public ItemGroupLoader getNewItemGroupLoader() {
        SmartYamlConfiguration configuration = new SmartYamlConfiguration(
                new File(getDataFolder(), "groups.yml"));
        return new ItemGroupLoaderImpl(configuration);
    }

    @Override
    public ResourceTable getResourceTable() {
        return resourceTable;
    }

    private void loadNames() {
        getLogger().info("Loading names");
        SmartTextFile file = new SmartTextFile(new File(getDataFolder(), "/resources/NamePartOne/generic.txt"));
        if (!file.exists()) {
            file.write(getResource("resources/NamePartOne/generic.txt"));
            debug("Writing /resources/NamePartOne/generic.txt");
        }
        getResourceTable().setAvailableResources(ResourceType.PART_ONE, "generic", file.read());
        file = new SmartTextFile(new File(getDataFolder(), "/resources/NamePartTwo/generic.txt"));
        if (!file.exists()) {
            file.write(getResource("resources/NamePartOne/generic.txt"));
            debug("Writing /resources/NamePartTwo/generic.txt");
        }
        getResourceTable().setAvailableResources(ResourceType.PART_TWO, "generic", file.read());
        file = new SmartTextFile(new File(getDataFolder(), "/resources/FlavorText/generic.txt"));
        if (!file.exists()) {
            file.write(getResource("resources/FlavorText/generic.txt"));
            debug("Writing /resources/FlavorText/generic.txt");
        }
        getResourceTable().setAvailableResources(ResourceType.FLAVOR_TEXT, "generic", file.read());

        File namePartOneFolder = new File(getDataFolder(), "/resources/NamePartOne/");
        for (String s : namePartOneFolder.list()) {
            if (s.equalsIgnoreCase("generic.txt") && s.endsWith(".txt")) {
                file = new SmartTextFile(new File(namePartOneFolder, StringUtils.replace(s, ".txt", "")));
                getResourceTable().setAvailableResources(ResourceType.PART_ONE,
                        StringUtils.replace(s, ".txt", "").toLowerCase(),
                        file.read());
            }
        }

        File namePartTwoFolder = new File(getDataFolder(), "/resources/NamePartTwo/");
        for (String s : namePartOneFolder.list()) {
            if (s.equalsIgnoreCase("generic.txt") && s.endsWith(".txt")) {
                file = new SmartTextFile(new File(namePartTwoFolder, StringUtils.replace(s, ".txt", "")));
                getResourceTable().setAvailableResources(ResourceType.PART_TWO,
                        StringUtils.replace(s, ".txt", "").toLowerCase(),
                        file.read());
            }
        }

        File flavorTextFolder = new File(getDataFolder(), "/resources/FlavorText/");
        for (String s : flavorTextFolder.list()) {
            if (s.equalsIgnoreCase("generic.txt") && s.endsWith(".txt")) {
                file = new SmartTextFile(new File(flavorTextFolder, StringUtils.replace(s, ".txt", "")));
                getResourceTable().setAvailableResources(ResourceType.FLAVOR_TEXT,
                        StringUtils.replace(s, ".txt", "").toLowerCase(),
                        file.read());
            }
        }

        debug("Loaded primary name files: " + getResourceTable().getFileNames(ResourceType.PART_ONE).size(),
                "Loaded primary names: " + getResourceTable().getAmountOfLoadedResources(ResourceType.PART_ONE),
                "Loaded secondary name files: " + getResourceTable().getFileNames(ResourceType.PART_TWO).size(),
                "Loaded secondary names: " + getResourceTable().getFileNames(ResourceType.PART_TWO).size());
    }

}
