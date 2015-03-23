package com.tealcube.minecraft.bukkit.spoils.identification;

import com.tealcube.minecraft.bukkit.hilt.HiltItemStack;
import com.tealcube.minecraft.spigot.spoils.common.utils.TextUtils;
import org.bukkit.Material;

public class UnidentifiedItem extends HiltItemStack {

    public UnidentifiedItem(SpoilsIdentification identification, Material material) {
        super(material);
        setName(TextUtils.color(identification.getSettings().getString("config.unidentified-item.name")));
        setLore(TextUtils.color(identification.getSettings().getStringList("config.unidentified-item.lore")));
    }

}
