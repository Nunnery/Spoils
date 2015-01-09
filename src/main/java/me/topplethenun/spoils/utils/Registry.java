package me.topplethenun.spoils.utils;

import java.util.HashSet;
import java.util.Set;

public class Registry<T> {

    private Set<T> registeredSet;

    public Registry() {
        this.registeredSet = new HashSet<>();
    }

    public Registry<T> register(T item) {
        this.registeredSet.add(item);
        return this;
    }

    public Registry<T> unregister(T item) {
        this.registeredSet.remove(item);
        return this;
    }

    public boolean contains(T item) {
        return registeredSet.contains(item);
    }

    public Set<T> getRegisteredItems() {
        return new HashSet<>(registeredSet);
    }

}
