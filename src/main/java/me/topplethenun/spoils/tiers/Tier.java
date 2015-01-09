package me.topplethenun.spoils.tiers;

import org.apache.commons.lang.Validate;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Tier {

    private final String name;
    private final Map<TierTrait, Object> traitValueMap;

    public Tier(String name) {
        this.name = name;
        this.traitValueMap = new ConcurrentHashMap<>();
    }

    public Object getTraitValue(TierTrait trait) {
        Validate.notNull(trait, "trait cannot be null");
        if (!traitValueMap.containsKey(trait)) {
            return trait.defaultValue();
        }
        return traitValueMap.get(trait);
    }

    public void setTraitValue(TierTrait trait, Object value) {
        Validate.notNull(trait, "trait cannot be null");
        Validate.notNull(value, "value cannot be null");
        Class traitValueClazz = trait.valueClass();
        Class valueClazz = value.getClass();
        if (!valueClazz.equals(traitValueClazz)) {
            throw new IllegalArgumentException("value is not of the right class type");
        }
        traitValueMap.put(trait, value);
    }

    public boolean hasTraitValue(TierTrait trait) {
        Validate.notNull(trait, "trait cannot be null");
        return traitValueMap.containsKey(trait);
    }

    public Map<TierTrait, Object> getTraitValues() {
        return new HashMap<>(traitValueMap);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tier)) return false;

        Tier tier = (Tier) o;

        return !(name != null ? !name.equals(tier.name) : tier.name != null);
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

}
