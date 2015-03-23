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
package com.tealcube.minecraft.spigot.spoils.commands;

import com.tealcube.minecraft.bukkit.hilt.HiltItemStack;
import com.tealcube.minecraft.spigot.spoils.api.SpoilsPlugin;
import com.tealcube.minecraft.spigot.spoils.api.tiers.Tier;
import com.tealcube.minecraft.spigot.spoils.common.math.SpoilsRandom;
import com.tealcube.minecraft.spigot.spoils.common.utils.MessageUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import se.ranzdo.bukkit.methodcommand.Arg;
import se.ranzdo.bukkit.methodcommand.Command;
import se.ranzdo.bukkit.methodcommand.FlagArg;
import se.ranzdo.bukkit.methodcommand.Flags;

import java.util.Set;

public class SpoilsCommand {

    private final SpoilsPlugin plugin;
    private final SpoilsRandom random;

    public SpoilsCommand(SpoilsPlugin plugin) {
        this.plugin = plugin;
        this.random = new SpoilsRandom();
    }

    @Command(identifier = "spoils summon tier", permissions = "spoils.command.summon", onlyPlayers = false)
    @Flags(identifier = {"a", "t"}, description = {"amount of items", "tier of items"})
    public void summonSubcommand(CommandSender sender, @Arg(name = "target", def = "?sender") Player target,
                                 @FlagArg("a") @Arg(name = "amount", def = "1", verifiers = "min[1]") int amount,
                                 @FlagArg("t") @Arg(name = "tier", def = "*") String tierName) {
        Tier tier = plugin.getTierManager().find(tierName).orNull();
        if (!tierName.equals("*") && tier == null) {
            MessageUtils.sendMessage(sender, "<red>Unable to find a tier with that name.");
            return;
        }
        Set<Tier> managedSet = plugin.getTierManager().getManaged();
        Tier[] managedArray = managedSet.toArray(new Tier[managedSet.size()]);
        for (int i = 0; i < amount; i++) {
            Tier t = tier;
            if (t == null) {
                t = managedArray[random.nextInt(managedArray.length)];
            }
            HiltItemStack his = plugin.getNewItemBuilder().withTier(t).build();
            target.getInventory().addItem(his);
        }
        MessageUtils.sendMessage(target, "<green>You received <white>%amount%<green> randomized items.",
                new String[][]{{"%amount%", "" + amount}});
        if (!target.equals(sender)) {
            MessageUtils.sendMessage(sender, "<green>You gave <white>%receiver% %amount%<green> randomized items.",
                    new String[][]{{"%amount%", "" + amount}, {"%receiver%", target.getDisplayName()}});
        }
    }

}
