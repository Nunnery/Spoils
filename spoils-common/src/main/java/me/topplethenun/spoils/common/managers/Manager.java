package me.topplethenun.spoils.common.managers;

import com.google.common.base.Optional;

import java.util.Set;

public interface Manager<E> {

    Optional<E> find(String name);

    void add(E param);

    void remove(E param);

    void remove(String name);

    Set<E> getManaged();

}
