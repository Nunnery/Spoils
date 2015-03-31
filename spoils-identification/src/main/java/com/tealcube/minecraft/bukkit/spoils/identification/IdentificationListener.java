/*
 * This file is part of spoils-identification, licensed under the ISC License.
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
package com.tealcube.minecraft.bukkit.spoils.identification;

import com.tealcube.minecraft.bukkit.hilt.HiltItemStack;
import com.tealcube.minecraft.spigot.spoils.common.utils.MessageUtils;
import com.tealcube.minecraft.spigot.spoils.common.utils.TextUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;

public class IdentificationListener implements Listener {

    private SpoilsIdentification identification;

    public IdentificationListener(SpoilsIdentification identification) {
        this.identification = identification;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getCurrentItem() == null || event.getCursor() == null
                || event.getCurrentItem().getType() == Material.AIR || event.getCursor().getType() == Material.AIR ||
                !(event.getWhoClicked() instanceof Player) || event.getClick() != ClickType.RIGHT) {
            return;
        }
        Player player = (Player) event.getWhoClicked();
        HiltItemStack currentItem = new HiltItemStack(event.getCurrentItem());
        HiltItemStack cursor = new HiltItemStack(event.getCursor());

        if (cursor.getName() == null) {
            return;
        }

        if (!cursor.getName().equals(TextUtils.color(identification.getSettings().getString("config.identity-tome.name")))) {
            return;
        }
        if (!currentItem.getName().equals(TextUtils.color(identification.getSettings().getString("config.unidentified-item.name")))) {
            return;
        }

        Material m = currentItem.getType();
        currentItem = identification.getSpoilsPlugin().getNewItemBuilder().withMaterial(m).withTier(identification.getRandomTierFromIdentificationChance())
                .build();

        MessageUtils.sendMessage(event.getWhoClicked(), "<green>You successfully identified your item!");

        event.setCurrentItem(currentItem);
        cursor.setAmount(cursor.getAmount() - 1);
        event.setCursor(cursor.getAmount() == 0 ? null : cursor);
        event.setCancelled(true);
        event.setResult(Event.Result.DENY);
        player.updateInventory();
    }

}
