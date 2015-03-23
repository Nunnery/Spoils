package com.tealcube.minecraft.bukkit.spoils.identification;

import com.tealcube.minecraft.spigot.spoils.api.tiers.TierTrait;

public enum IdentificationTierTrait implements TierTrait {

    IDENTIFICATION_CHANCE("identification-chance", Double.class, 0.0D);

    private final String key;
    private final Class valueClass;
    private final Object defaultValue;

    private IdentificationTierTrait(String key, Class valueClass, Object defaultValue) {
        this.key = key;
        this.valueClass = valueClass;
        this.defaultValue = defaultValue;
    }

    @Override
    public String key() {
        return key;
    }

    @Override
    public Class valueClass() {
        return valueClass;
    }

    @Override
    public Object defaultValue() {
        return defaultValue;
    }

}
