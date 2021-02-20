package com.alsi.inmemorycache.core;

import java.util.Optional;

public interface Cache<K, V> {

    void put(K key, V value);

    boolean contains(K key);

    Optional<V> get(K key);
}
