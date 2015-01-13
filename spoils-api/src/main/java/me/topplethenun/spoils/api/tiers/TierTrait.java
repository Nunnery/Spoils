package me.topplethenun.spoils.api.tiers;

public interface TierTrait {

    /**
     * Returns the value that denotes the path of this TierTrait.
     * @return name and path of this TierTrait
     */
    String key();

    /**
     * Returns the Class of allowed values for this TierTrait.
     * @return Class of allowed values
     */
    Class<?> valueClass();

    /**
     * Returns the default value for this TierTrait.
     * @return default value
     */
    Object defaultValue();

}
