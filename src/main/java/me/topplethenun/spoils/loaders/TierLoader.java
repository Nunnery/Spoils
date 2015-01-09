package me.topplethenun.spoils.loaders;

import me.topplethenun.spoils.tiers.Tier;
import me.topplethenun.spoils.tiers.TierTrait;
import org.apache.commons.lang.Validate;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.HashSet;
import java.util.Set;

public class TierLoader implements Loader<Set<Tier>> {

    private final YamlConfiguration configuration;
    private final Set<TierTrait> traits;

    public TierLoader(YamlConfiguration configuration, Set<TierTrait> traits) {
        this.configuration = configuration;
        this.traits = traits;
    }

    @Override
    public Set<Tier> load() {
        Validate.notNull(configuration, "configuration can't be null");
        Validate.notNull(traits, "traits can't be null");
        Set<Tier> tiers = new HashSet<>();
        for (String key : configuration.getKeys(false)) {
            if (!configuration.isConfigurationSection(key)) {
                continue;
            }
            ConfigurationSection cs = configuration.getConfigurationSection(key);
            Tier tier = new Tier(key);
            for (TierTrait trait : traits) {
                tier.setTraitValue(trait, cs.get(trait.key(), trait.defaultValue()));
            }
            tiers.add(tier);
        }
        return tiers;
    }

}
