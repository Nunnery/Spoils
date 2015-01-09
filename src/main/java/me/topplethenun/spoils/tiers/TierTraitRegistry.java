package me.topplethenun.spoils.tiers;

import org.apache.commons.lang.Validate;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class TierTraitRegistry {

    private final Map<String, TierTrait> registeredTraits;

    public TierTraitRegistry() {
        registeredTraits = new HashMap<>();
    }

    public void register(TierTrait trait) {
        Validate.notNull(trait, "trait cannot be null");
        if (registeredTraits.containsKey(trait.key().toLowerCase())) {
            return;
        }
        registeredTraits.put(trait.key().toLowerCase(), trait);
    }

    public void unregister(TierTrait trait) {
        Validate.notNull(trait, "trait cannot be null");
        if (!registeredTraits.containsKey(trait.key().toLowerCase())) {
            return;
        }
        registeredTraits.remove(trait.key().toLowerCase());
    }

    public boolean isRegistered(TierTrait trait) {
        Validate.notNull(trait, "trait cannot be null");
        return registeredTraits.containsKey(trait.key());
    }

    public Set<TierTrait> getRegisteredTraits() {
        return new HashSet<>(registeredTraits.values());
    }

}
