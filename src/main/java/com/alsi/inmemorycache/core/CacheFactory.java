package com.alsi.inmemorycache.core;

import com.alsi.inmemorycache.core.cacheImpl.LfuCache;
import com.alsi.inmemorycache.core.cacheImpl.LruCache;

public class CacheFactory {

    private static final int DEFAULT_SIZE = 10;

    public static <K, V> Cache<K, V> get(EvictionStrategy evictionStrategy) {
        return get(DEFAULT_SIZE, evictionStrategy);
    }

    public static <K, V> Cache<K, V> get(int maxSize, EvictionStrategy evictionStrategy) {
        if(EvictionStrategy.LEAST_RECENTLY_USED == evictionStrategy) {
            return new LruCache<>(maxSize);
        }
        if(EvictionStrategy.LEAST_FREQUENCY_USED == evictionStrategy) {
            return new LfuCache<>(maxSize);
        }

        throw new UnsupportedOperationException("Evict strategy " + evictionStrategy.name() + " is not supported");
    }

    public enum EvictionStrategy {
        LEAST_RECENTLY_USED,

        LEAST_FREQUENCY_USED
    }
}
