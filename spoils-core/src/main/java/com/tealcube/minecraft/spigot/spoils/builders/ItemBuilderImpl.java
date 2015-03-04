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
package com.tealcube.minecraft.spigot.spoils.builders;

import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.tealcube.minecraft.bukkit.hilt.HiltItemStack;
import com.tealcube.minecraft.spigot.spoils.api.SpoilsPlugin;
import com.tealcube.minecraft.spigot.spoils.api.builders.ItemBuilder;
import com.tealcube.minecraft.spigot.spoils.api.items.ItemGroup;
import com.tealcube.minecraft.spigot.spoils.api.names.ResourceType;
import com.tealcube.minecraft.spigot.spoils.api.tiers.StandardTierTrait;
import com.tealcube.minecraft.spigot.spoils.api.tiers.Tier;
import com.tealcube.minecraft.spigot.spoils.common.math.SpoilsRandom;
import com.tealcube.minecraft.spigot.spoils.common.utils.TextUtils;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ItemBuilderImpl implements ItemBuilder {

    private final SpoilsPlugin plugin;
    private boolean built;
    private Material material;
    private Tier tier;

    public ItemBuilderImpl(SpoilsPlugin plugin) {
        this.plugin = plugin;
    }

    @SuppressWarnings("unchecked")
    @Override
    public HiltItemStack build() {
        Preconditions.checkNotNull(tier);
        Preconditions.checkState(!isBuilt());
        if (material == null) {
            material = calculateMaterial();
        }
        Preconditions.checkState(material != Material.AIR);

        Random random = new SpoilsRandom();
        boolean onlyUseGenericAsFallback = plugin.getSettings().getBoolean("config.only-use-generic-as-fallback");
        HiltItemStack itemStack = new HiltItemStack(material);

        List<String> namePartOneList = new ArrayList<>();
        for (ItemGroup itemGroup : plugin.getItemGroupManager().findItemGroups(material)) {
            namePartOneList.addAll(plugin.getResourceTable().getAvailableResources(ResourceType.PART_ONE, itemGroup
                    .getName().toLowerCase()));
        }
        if (!onlyUseGenericAsFallback || namePartOneList.isEmpty()) {
            namePartOneList.addAll(plugin.getResourceTable().getAvailableResources(ResourceType.PART_ONE, "generic"));
        }
        String namePartOne = namePartOneList.get(random.nextInt(namePartOneList.size()));

        List<String> namePartTwoList = new ArrayList<>();
        for (ItemGroup itemGroup : plugin.getItemGroupManager().findItemGroups(material)) {
            namePartTwoList.addAll(plugin.getResourceTable().getAvailableResources(ResourceType.PART_TWO, itemGroup
                    .getName().toLowerCase()));
        }
        if (!onlyUseGenericAsFallback || namePartTwoList.isEmpty()) {
            namePartTwoList.addAll(plugin.getResourceTable().getAvailableResources(ResourceType.PART_TWO, "generic"));
        }
        String namePartTwo = namePartTwoList.get(random.nextInt(namePartTwoList.size()));

        String name = TextUtils.color(String.format("%s%s %s%s", tier.getTraitValue(StandardTierTrait.PRIMARY_COLOR),
                namePartOne, namePartTwo, tier.getTraitValue(StandardTierTrait.SECONDARY_COLOR)));
        itemStack.setName(name);

        List<String> lore = new ArrayList<>();
        List<String> flavorText = (List<String>) tier.getTraitValue(StandardTierTrait.FLAVOR_TEXT);
        String s = flavorText.get(random.nextInt(flavorText.size()));
        List<String> toAdd = Splitter.on("/n").omitEmptyStrings().splitToList(s);
        lore.addAll(TextUtils.color(toAdd));
        itemStack.setLore(lore);

        built = true;
        return itemStack;
    }

    @Override
    public boolean isBuilt() {
        return built;
    }

    @Override
    public ItemBuilder withMaterial(Material material) {
        Preconditions.checkNotNull(material);
        Preconditions.checkState(!isBuilt());
        Preconditions.checkArgument(material != Material.AIR);
        this.material = material;
        return this;
    }

    @Override
    public ItemBuilder withTier(Tier tier) {
        Preconditions.checkNotNull(tier);
        Preconditions.checkState(!isBuilt());
        this.tier = tier;
        return this;
    }

    @SuppressWarnings("unchecked")
    private Material calculateMaterial() {
        Random random = new SpoilsRandom();
        List<Material> materialList = new ArrayList<>();
        List<ItemGroup> allowedItemGroups = (List<ItemGroup>) tier.getTraitValue(StandardTierTrait.ALLOWED_ITEM_GROUPS);
        List<ItemGroup> disallowedItemGroups = (List<ItemGroup>) tier.getTraitValue(StandardTierTrait
                .DISALLOWED_ITEM_GROUPS);
        for (ItemGroup itemGroup : allowedItemGroups) {
            if (itemGroup.isInverse()) {
                continue;
            }
            materialList.addAll(itemGroup.getMaterials());
        }
        for (ItemGroup itemGroup : disallowedItemGroups) {
            if (!itemGroup.isInverse()) {
                continue;
            }
            materialList.removeAll(itemGroup.getMaterials());
        }
        return materialList.get(random.nextInt(materialList.size()));
    }

}
