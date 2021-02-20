package com.alsi.inmemorycache.core.cacheImpl;

import com.alsi.inmemorycache.core.Cache;
import com.alsi.inmemorycache.core.utils.CacheException;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class LfuCache<K, V> extends AbstractCache<K, V> implements Cache<K, V> {

    public LfuCache(int maxSize) {
        super(maxSize);
        this.keysFrequency = new HashMap<>(this.maxSize);
        this.minFreq = 0;
        this.minFreqKey = null;
    }

    private final HashMap<K, Integer> keysFrequency;
    private int minFreq;
    private K minFreqKey;

    @Override
    public Optional<V> get(K key) {
        checkKey(key);
        if (storage.isEmpty()) {
            return Optional.empty();
        }
        keysFrequency.computeIfPresent(key, (k, old) -> old += 1);
        if (key.equals(minFreqKey)) {
            recalculateMinFrequency();
        }

        return Optional.ofNullable(storage.get(key));
    }

    @Override
    public void put(K key, V value) {
        checkKey(key);
        evictOneIfNeed();
        storage.put(key, value);
        keysFrequency.put(key, 1);
        minFreqKey = key;
        minFreq = 1;
        curSize += 1;
    }

    protected void evictOne() {
        storage.remove(minFreqKey);
        keysFrequency.remove(minFreqKey);
        // no recalculateMinFrequency() need because this method will be called only on put,
        // and minFreq will be set there for a new element.
        curSize = curSize - 1;
    }

    private void recalculateMinFrequency() {
        if(!storage.isEmpty()) {
            Map.Entry<K, Integer> minFreqEntry = keysFrequency.entrySet()
                    .stream()
                    .min(Comparator.comparingInt(Map.Entry::getValue))
                    // newer happens because evict calls only if maxSize == curSize and maxSize always >= 1;
                    .orElseThrow(() -> new CacheException("Cache is empty, but was called recalculateMinFrequency method"));

            minFreq = minFreqEntry.getValue();
            minFreqKey = minFreqEntry.getKey();
        }

    }
}
