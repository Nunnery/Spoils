package me.topplethenun.spoils.api.tiers;

import java.util.Set;

public interface TierTraitRegistry {
    /**
     * Register a TierTrait if it is not already registered.
     * @param trait TierTrait to register
     */
    void register(TierTrait trait);

    /**
     * Unregister a TierTrait if it is already registered.
     * @param trait TierTrait to unregister
     */
    void unregister(TierTrait trait);

    /**
     * Checks and returns if the given TierTrait is registered.
     * @param trait TierTrait to check
     * @return if TierTrait is registered
     */
    boolean isRegister(TierTrait trait);

    /**
     * Gets and returns a Set of all registered TierTraits.
     * @return Set of all registered TierTraits
     */
    Set<TierTrait> getRegisteredTraits();
}
