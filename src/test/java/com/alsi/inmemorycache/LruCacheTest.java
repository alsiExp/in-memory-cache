package com.alsi.inmemorycache;

import com.alsi.inmemorycache.core.Cache;
import com.alsi.inmemorycache.core.CacheFactory;
import com.alsi.inmemorycache.core.utils.CacheException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Lfu cache test")
public class LruCacheTest extends BaseCacheTest {

    private Cache<Integer, Object> lru;

    @BeforeEach
    void init() {
        lru = CacheFactory.get(3, CacheFactory.EvictionStrategy.LEAST_RECENTLY_USED);
    }

    @DisplayName("cache delete oldest object when reached max size")
    @Test
    void lruCacheBaseTest() {

        lru.put(1, new Object());
        lru.put(2, new Object());
        lru.put(3, new Object());
        lru.put(4, new Object());

        assertTrue(lru.contains(3), "Element 3 must be in cache");
        assertTrue(lru.contains(2), "Element 2 must be in cache");
        assertTrue(lru.contains(4), "Element 4 must be in cache");
        assertFalse(lru.contains(1), "Element 1 must be deleted from cache, because max cache size was 3");
    }


    @DisplayName("cache don't add null as key")
    @Test
    void tryToAddNullKeyInLruCache() {
        assertThrows(CacheException.class, () -> tryToAddNullKey(lru));
    }

}
