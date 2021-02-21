package com.alsi.inmemorycache.core.cacheImpl;

import com.alsi.inmemorycache.core.Cache;

import java.util.LinkedList;
import java.util.Optional;

public class LruCache<K, V> extends AbstractCache<K, V> implements Cache<K, V> {

    public LruCache(int maxSize) {
        super(maxSize);
        this.keys = new LinkedList<>();
    }

    private final LinkedList<K> keys;

    @Override
    public Optional<V> get(K key) {
        checkKey(key);
        return Optional.ofNullable(storage.get(key));
    }

    @Override
    public void put(K key, V value) {
        checkKey(key);
        if(storage.containsKey(key)) {
            putExistedKey(key, value);
        } else {
            putNewKey(key, value);
        }
    }

    private void putExistedKey(K key, V value) {
        // no eviction or incrementing curSize need
        storage.put(key, value);

        // put key in the end if the list
        keys.remove(key);
        keys.add(key);
    }

    private void putNewKey(K key, V value) {
        evictOneIfNeed();
        storage.put(key, value);
        keys.add(key);
        curSize += 1;
    }

    protected void evictOne() {
        if(!storage.isEmpty()) {
            K lastKey = keys.removeFirst();
            storage.remove(lastKey);
            curSize = curSize - 1;
        }
    }
}
