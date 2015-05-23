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
package com.tealcube.minecraft.spigot.spoils;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import com.tealcube.minecraft.bukkit.config.MasterConfiguration;
import com.tealcube.minecraft.bukkit.config.SmartYamlConfiguration;
import com.tealcube.minecraft.bukkit.config.VersionedSmartConfiguration;
import com.tealcube.minecraft.bukkit.config.VersionedSmartYamlConfiguration;
import com.tealcube.minecraft.spigot.spoils.api.SpoilsPlugin;
import com.tealcube.minecraft.spigot.spoils.api.builders.ItemBuilder;
import com.tealcube.minecraft.spigot.spoils.api.items.ItemGroup;
import com.tealcube.minecraft.spigot.spoils.api.loaders.ItemGroupLoader;
import com.tealcube.minecraft.spigot.spoils.api.loaders.TierLoader;
import com.tealcube.minecraft.spigot.spoils.api.managers.ItemGroupManager;
import com.tealcube.minecraft.spigot.spoils.api.managers.TierManager;
import com.tealcube.minecraft.spigot.spoils.api.names.ResourceTable;
import com.tealcube.minecraft.spigot.spoils.api.names.ResourceType;
import com.tealcube.minecraft.spigot.spoils.api.tiers.Tier;
import com.tealcube.minecraft.spigot.spoils.api.tiers.TierTrait;
import com.tealcube.minecraft.spigot.spoils.api.tiers.TierTraitRegistry;
import com.tealcube.minecraft.spigot.spoils.builders.ItemBuilderImpl;
import com.tealcube.minecraft.spigot.spoils.commands.SpoilsCommand;
import com.tealcube.minecraft.spigot.spoils.common.io.Debugger;
import com.tealcube.minecraft.spigot.spoils.common.io.SmartTextFile;
import com.tealcube.minecraft.spigot.spoils.loaders.ItemGroupLoaderImpl;
import com.tealcube.minecraft.spigot.spoils.loaders.TierLoaderImpl;
import com.tealcube.minecraft.spigot.spoils.managers.ItemGroupManagerImpl;
import com.tealcube.minecraft.spigot.spoils.managers.TierManagerImpl;
import com.tealcube.minecraft.spigot.spoils.names.ResourceTableImpl;
import com.tealcube.minecraft.spigot.spoils.tiers.TierTraitRegistryImpl;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.ranzdo.bukkit.methodcommand.CommandHandler;

import java.io.File;
import java.util.Set;

public class SpoilsPluginImpl extends SpoilsPlugin {

    private static Logger logger;

    private static SpoilsPlugin instance;
    private TierTraitRegistry tierTraitRegistry;
    private Debugger debugger;
    private MasterConfiguration settings;
    private TierManager tierManager;
    private ItemGroupManager itemGroupManager;
    private ResourceTable resourceTable;
    private CommandHandler commandHandler;
    private boolean valid;
    private String invalidationReason;

    public static SpoilsPlugin getInstance() {
        return instance;
    }

    @Override
    public void onDisable() {
        logger.info("Disabling v" + getDescription().getVersion());
        for (TierTrait trait : tierTraitRegistry.getRegisteredTraits()) {
            tierTraitRegistry.unregister(trait);
        }
    }

    @Override
    public void onEnable() {
        valid = true;
        instance = this;
        debugger = new Debugger(new File(getDataFolder(), "debug.log"));
        tierTraitRegistry = new TierTraitRegistryImpl();

        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        JoranConfigurator jc = new JoranConfigurator();
        jc.setContext(loggerContext);
        loggerContext.reset();
        loggerContext.putProperty("baseplugindir", getDataFolder().getParent());
        loggerContext.putProperty("plugindir", getDataFolder().getName());
        try {
            jc.doConfigure(getResource("logback.xml"));
        } catch (JoranException e) {
            e.printStackTrace();
        }
        logger = LoggerFactory.getLogger(SpoilsPlugin.class);

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
        logger.info("Loaded item groups: " + itemGroupSet.size());

        getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
            @Override
            public void run() {
                Set<Tier> tierSet = getNewTierLoader().load();
                for (Tier t : tierSet) {
                    getTierManager().add(t);
                }
                logger.info("Loaded tiers: " + tierSet.size());
            }
        }, 20L);

        commandHandler = new CommandHandler(this);
        commandHandler.registerCommands(new SpoilsCommand(this));

        logger.info("Enabling v" + getDescription().getVersion());
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
            logger.info("Writing /resources/NamePartOne/generic.txt");
        }
        getResourceTable().setAvailableResources(ResourceType.PART_ONE, "generic", file.read());
        file = new SmartTextFile(new File(getDataFolder(), "/resources/NamePartTwo/generic.txt"));
        if (!file.exists()) {
            file.write(getResource("resources/NamePartTwo/generic.txt"));
            logger.info("Writing /resources/NamePartTwo/generic.txt");
        }
        getResourceTable().setAvailableResources(ResourceType.PART_TWO, "generic", file.read());
        file = new SmartTextFile(new File(getDataFolder(), "/resources/FlavorText/generic.txt"));
        if (!file.exists()) {
            file.write(getResource("resources/FlavorText/generic.txt"));
            logger.info("Writing /resources/FlavorText/generic.txt");
        }
        getResourceTable().setAvailableResources(ResourceType.FLAVOR_TEXT, "generic", file.read());

        File namePartOneFolder = new File(getDataFolder(), "/resources/NamePartOne/");
        for (String s : namePartOneFolder.list()) {
            if (!s.equalsIgnoreCase("generic.txt") && s.endsWith(".txt")) {
                file = new SmartTextFile(new File(namePartOneFolder, StringUtils.replace(s, ".txt", "")));
                getResourceTable().setAvailableResources(ResourceType.PART_ONE,
                        StringUtils.replace(s, ".txt", "").toLowerCase(),
                        file.read());
            }
        }

        File namePartTwoFolder = new File(getDataFolder(), "/resources/NamePartTwo/");
        for (String s : namePartOneFolder.list()) {
            if (!s.equalsIgnoreCase("generic.txt") && s.endsWith(".txt")) {
                file = new SmartTextFile(new File(namePartTwoFolder, StringUtils.replace(s, ".txt", "")));
                getResourceTable().setAvailableResources(ResourceType.PART_TWO,
                        StringUtils.replace(s, ".txt", "").toLowerCase(),
                        file.read());
            }
        }

        File flavorTextFolder = new File(getDataFolder(), "/resources/FlavorText/");
        for (String s : flavorTextFolder.list()) {
            if (!s.equalsIgnoreCase("generic.txt") && s.endsWith(".txt")) {
                file = new SmartTextFile(new File(flavorTextFolder, StringUtils.replace(s, ".txt", "")));
                getResourceTable().setAvailableResources(ResourceType.FLAVOR_TEXT,
                        StringUtils.replace(s, ".txt", "").toLowerCase(),
                        file.read());
            }
        }

        logger.info("Loaded primary name files: " + getResourceTable().getFileNames(ResourceType.PART_ONE).size());
        logger.info("Loaded primary names: " + getResourceTable().getAmountOfLoadedResources(ResourceType.PART_ONE));
        logger.info("Loaded secondary name files: " + getResourceTable().getFileNames(ResourceType.PART_TWO).size());
        logger.info("Loaded secondary names: " + getResourceTable().getFileNames(ResourceType.PART_TWO).size());
        logger.info("Loaded flavor texts: " + getResourceTable().getAmountOfLoadedResources(ResourceType.FLAVOR_TEXT));
        logger.info("Loaded flavor text files: " + getResourceTable().getFileNames(ResourceType.FLAVOR_TEXT).size());
    }

    @Override
    public CommandHandler getCommandHandler() {
        return commandHandler;
    }

    @Override
    public ItemBuilder getNewItemBuilder() {
        return new ItemBuilderImpl(this);
    }

    @Override
    public boolean isValid() {
        return valid;
    }

    @Override
    public void setValid(boolean valid) {
        this.valid = valid;
    }

    @Override
    public String getInvalidationReason() {
        return invalidationReason != null ? invalidationReason : "";
    }

    @Override
    public void setInvalidationReason(String invalidationReason) {
        this.invalidationReason = invalidationReason;
    }

}
