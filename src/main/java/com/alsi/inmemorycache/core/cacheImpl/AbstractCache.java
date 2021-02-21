package com.alsi.inmemorycache.core.cacheImpl;

import com.alsi.inmemorycache.core.Cache;
import com.alsi.inmemorycache.core.utils.CacheException;

import java.util.HashMap;

public abstract class AbstractCache<K, V> implements Cache<K, V> {

    protected AbstractCache(int maxSize) {
        this.maxSize = maxSize > 0 ? maxSize : 1;
        this.storage = new HashMap<>(this.maxSize);
        this.curSize = 0;
    }

    protected int curSize;
    protected final int maxSize;
    protected final HashMap<K, V> storage;

    @Override
    public boolean contains(K key) {
        return this.get(key).isPresent();
    }

    protected void evictOneIfNeed() {
        if(maxSize == curSize) {
            evictOne();
        }
    }

    protected void checkKey(K key) {
        if(key == null) {
            throw new CacheException("Cache key can not be null");
        }
    }
    protected abstract void evictOne();
}
