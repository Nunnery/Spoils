package com.tealcube.minecraft.bukkit.spoils.identification;

import com.tealcube.minecraft.bukkit.hilt.HiltBook;
import com.tealcube.minecraft.spigot.spoils.common.utils.TextUtils;

public class IdentityTome extends HiltBook {
    public IdentityTome(SpoilsIdentification identification) {
        super(TomeType.WRITTEN_BOOK);
        setName(TextUtils.color(identification.getSettings().getString("config.identity-tome.name")));
        setLore(TextUtils.color(identification.getSettings().getStringList("config.identity-tome.lore")));
    }
}
