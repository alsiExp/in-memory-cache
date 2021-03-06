package com.alsi.inmemorycache;

import com.alsi.inmemorycache.core.Cache;
import com.alsi.inmemorycache.core.CacheFactory;
import com.alsi.inmemorycache.core.utils.CacheException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("LRU cache test")
public class LruCacheTest extends BaseCacheTest {

    private Cache<Integer, Object> lru;

    @BeforeEach
    void init() {
        lru = CacheFactory.get(3, CacheFactory.EvictionStrategy.LEAST_RECENTLY_USED);
    }

    @DisplayName("cache delete oldest object when reached max size")
    @Test
    void lruCacheBaseTest() {

        fillCacheWithObjectsAndIntegerKeysNTimes(lru, 4);

        assertTrue(lru.contains(3), "Key 3 must be in cache");
        assertTrue(lru.contains(2), "Key 2 must be in cache");
        assertTrue(lru.contains(4), "Key 4 must be in cache");
        assertFalse(lru.contains(1), "Key 1 must be deleted from cache, because max cache size was 3");
    }


    @DisplayName("cache don't add null as key")
    @Test
    void testTryToAddNullKeyInLruCache() {
        assertThrows(CacheException.class, () -> testTryToAddNullKey(lru));
    }

    @DisplayName("cache have at least one slot to store object if max size is less that 1")
    @Test
    void testTryToCreateCacheWithZeroMaxSize() {
        testThatCacheCanStoreAtLeastOneElement(CacheFactory.get(0, CacheFactory.EvictionStrategy.LEAST_RECENTLY_USED));
    }

    @DisplayName("cache return Optional.empty() for not existed key")
    @Test
    void testGetValueFromEmptyCache() {
        testThatGetObjectFromEmptyCacheFindNothing(lru);
    }

    @DisplayName("cache update position of the element, when somebody put existing key")
    @Test
    void testAddNewPairThatAlreadyContainsInCache() {
        fillCacheWithObjectsAndIntegerKeysNTimes(lru, 4);
        assertTrue(lru.contains(3), "Key 3 must be in cache");
        Object objWithKey3 = lru.get(3).orElse(null);
        assertNotNull(objWithKey3, "Value for key 3 must be not null");
        lru.put(3, objWithKey3);

        // adding 2 new elements with indexes 10 and 11
        fillCacheWithObjectsAndIntegerKeysNTimes(lru, 2, 10);

        assertTrue(lru.contains(3), "Key 3 still must be in cache");

    }
}
