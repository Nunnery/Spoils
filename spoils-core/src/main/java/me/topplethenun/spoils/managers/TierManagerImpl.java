package me.topplethenun.spoils.managers;

import com.google.common.base.Optional;
import me.topplethenun.spoils.api.managers.TierManager;
import me.topplethenun.spoils.api.tiers.Tier;
import me.topplethenun.spoils.tiers.StandardTierTrait;
import org.apache.commons.lang.Validate;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class TierManagerImpl implements TierManager {

    private final Map<String, Tier> tierMap;

    public TierManagerImpl() {
        tierMap = new ConcurrentHashMap<>();
    }

    @Override
    public Optional<Tier> find(String name) {
        Validate.notNull(name, "name cannot be null");
        return tierMap.containsKey(name.toLowerCase()) ? Optional.of(tierMap.get(name.toLowerCase())) :
                Optional.<Tier>absent();
    }

    @Override
    public void add(Tier param) {
        Validate.notNull(param, "param cannot be null");
        String name = param.getName();
        if (!tierMap.containsKey(name.toLowerCase())) {
            tierMap.put(name.toLowerCase(), param);
        }
    }

    @Override
    public void remove(Tier param) {
        Validate.notNull(param, "param cannot be null");
        String name = param.getName();
        if (tierMap.containsKey(name.toLowerCase())) {
            tierMap.remove(name.toLowerCase());
        }
    }

    @Override
    public void remove(String name) {
        Validate.notNull(name, "name cannot be null");
        if (!tierMap.containsKey(name.toLowerCase())) {
            tierMap.remove(name.toLowerCase());
        }
    }

    @Override
    public Set<Tier> getManaged() {
        return new HashSet<>(tierMap.values());
    }
}
