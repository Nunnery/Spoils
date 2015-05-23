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
package com.tealcube.minecraft.bukkit.spoils.identification;

import com.tealcube.minecraft.bukkit.config.MasterConfiguration;
import com.tealcube.minecraft.bukkit.config.VersionedSmartConfiguration;
import com.tealcube.minecraft.bukkit.config.VersionedSmartYamlConfiguration;
import com.tealcube.minecraft.spigot.spoils.api.SpoilsPlugin;
import com.tealcube.minecraft.spigot.spoils.api.tiers.Tier;
import com.tealcube.minecraft.spigot.spoils.common.math.SpoilsRandom;
import com.tealcube.minecraft.spigot.spoils.common.utils.MessageUtils;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.ranzdo.bukkit.methodcommand.Arg;
import se.ranzdo.bukkit.methodcommand.Command;
import se.ranzdo.bukkit.methodcommand.FlagArg;
import se.ranzdo.bukkit.methodcommand.Flags;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

public class SpoilsIdentification extends JavaPlugin {

    private static Logger logger;
    private MasterConfiguration settings;
    private SpoilsPlugin spoilsPlugin;
    private SpoilsRandom random;

    @Override
    public void onEnable() {
        logger = LoggerFactory.getLogger(SpoilsIdentification.class);
        random = new SpoilsRandom();
        this.spoilsPlugin = (SpoilsPlugin) getServer().getPluginManager().getPlugin("spoils-core");

        VersionedSmartYamlConfiguration configYAML = new VersionedSmartYamlConfiguration(new File(getDataFolder(), "config.yml"), getResource
                ("config.yml"), VersionedSmartConfiguration.VersionUpdateType.BACKUP_AND_UPDATE);
        if (configYAML.update()) {
            logger.info("Updating config.yml");
        }

        settings = MasterConfiguration.loadFromFiles(configYAML);

        this.spoilsPlugin.getTierTraitRegistry().register(IdentificationTierTrait.IDENTIFICATION_CHANCE);
        this.spoilsPlugin.getCommandHandler().registerCommands(this);

        getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
            @Override
            public void run() {
                double amount = 0D;
                for (Tier t : spoilsPlugin.getTierManager().getManaged()) {
                    amount += (double) t.getTraitValue(IdentificationTierTrait.IDENTIFICATION_CHANCE);
                }
                if (amount > 100D) {
                    logger.info("Identification chance does not add up to 100");
                    spoilsPlugin.setInvalidationReason("Identification chance does not add up to 100");
                    spoilsPlugin.setValid(false);
                }
            }
        }, 20L * 2);

        getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
            @Override
            public void run() {
                if (!spoilsPlugin.isValid()) {
                    logger.info("Parent plugin is invalid, disabling");
                    getServer().getPluginManager().disablePlugin(SpoilsIdentification.this);
                }
            }
        }, 20L * 5);

        getServer().getPluginManager().registerEvents(new IdentificationListener(this), this);

        logger.info("Enabling " + getDescription().getName() + " v" + getDescription().getVersion());
    }

    @Override
    public void onDisable() {
        HandlerList.unregisterAll(this);
    }

    /**
     * Gets and returns the MasterConfiguration for this instance.
     *
     * @return MasterConfiguration
     */
    public MasterConfiguration getSettings() {
        return settings;
    }

    public Tier getRandomTierFromIdentificationChance() {
        double amount = random.nextDouble() * 100;
        double curAmount = 0D;
        Set<Tier> tiers = new HashSet<>(spoilsPlugin.getTierManager().getManaged());
        for (Tier t : tiers) {
            curAmount += (double) t.getTraitValue(IdentificationTierTrait.IDENTIFICATION_CHANCE);
            if (curAmount >= amount) {
                return t;
            }
        }
        return null;
    }

    public SpoilsPlugin getSpoilsPlugin() {
        return spoilsPlugin;
    }

    public IdentityTome getNewIdentityTome() {
        return new IdentityTome(this);
    }

    public UnidentifiedItem getNewUnidentifiedItem(Material material) {
        return new UnidentifiedItem(this, material);
    }

    @Command(identifier = "spoils summon tome", permissions = "spoils.command.summon", onlyPlayers = false)
    @Flags(identifier = {"a", "t"}, description = {"amount of items", "tier of items"})
    public void summonSubcommand(CommandSender sender, @Arg(name = "target", def = "?sender") Player target,
                                 @FlagArg("a") @Arg(name = "amount", def = "1", verifiers = "min[1]") int amount) {
        for (int i = 0; i < amount; i++) {
            target.getInventory().addItem(getNewIdentityTome());
        }
        MessageUtils.sendMessage(target, "<green>You received <white>%amount%" + getSettings().getString("config.identity-tome.name") +
                        "<green>(s).", new String[][]{{"%amount%", "" + amount}});
        if (!target.equals(sender)) {
            MessageUtils.sendMessage(sender, "<green>You gave <white>%receiver% %amount% " + getSettings().getString("config.identity-tome.name") +
                    "<green>(s).", new String[][]{{"%amount%", "" + amount}, {"%receiver%", target.getDisplayName()}});
        }
    }

}
