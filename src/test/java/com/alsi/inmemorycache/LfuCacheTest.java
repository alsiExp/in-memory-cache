package com.alsi.inmemorycache;

import com.alsi.inmemorycache.core.Cache;
import com.alsi.inmemorycache.core.CacheFactory;
import com.alsi.inmemorycache.core.utils.CacheException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("LFU cache test: ")
public class LfuCacheTest extends BaseCacheTest {

    private Cache<Integer, Object> lfu;

    @BeforeEach
    void init() {
        lfu = CacheFactory.get(3, CacheFactory.EvictionStrategy.LEAST_FREQUENCY_USED);
    }

    @DisplayName("cache let survive only objects with maximum call frequency")
    @Test
    void lfuCacheBaseTest() {

        fillCacheWithObjectsAndIntegerKeysNTimes(lfu, 3);
        // get some elements
        IntStream.range(0, 10)
                .forEach(i -> lfu.get(3));
        IntStream.range(0, 5)
                .forEach(i -> lfu.get(2));

        lfu.put(4, new Object());

        assertTrue(lfu.contains(3), "Element 3 must be in cache, because it was called 10 times");
        assertTrue(lfu.contains(2), "Element 2 must be in cache, because it was called 5 times");
        assertTrue(lfu.contains(4), "Element 4 must be in cache, because it is last added");
        assertFalse(lfu.contains(1), "Element 1 must be deleted from cache, because max cache size was 3");
    }


    @DisplayName("cache don't add null as key")
    @Test
    void testTryToAddNullKeyInLfuCache() {
        assertThrows(CacheException.class, () -> testTryToAddNullKey(lfu));
    }

    @DisplayName("cache have at least one slot to store object if max size is less that 1")
    @Test
    void testTryToCreateCacheWithZeroMaxSize() {
        testThatCacheCanStoreAtLeastOneElement(CacheFactory.get(-5, CacheFactory.EvictionStrategy.LEAST_FREQUENCY_USED));
    }

    @DisplayName("cache return Optional.empty() for not existed key")
    @Test
    void testGetValueFromEmptyCache() {
        testThatGetObjectFromEmptyCacheFindNothing(lfu);
    }

    @DisplayName("cache save frequency when put existed key with the same value")
    @Test
    void testAddKeyThatAlreadyContainsInCacheWithSameValue() {
        int keyWithMaxFrequency = 2;
        int keyWithFrequency_1 = 3;
        fillCacheWithObjectsAndIntegerKeysNTimes(lfu, 3);

        IntStream.range(0, 3)
                .forEach(i -> lfu.get(keyWithMaxFrequency));
        IntStream.range(0, 1)
                .forEach(i -> lfu.get(keyWithFrequency_1));

        Object obj = lfu.get(keyWithMaxFrequency).orElse(null);
        assertNotNull(obj);

        lfu.put(keyWithMaxFrequency, obj);

        IntStream.range(5, 10)
                .forEach(i -> {
                    lfu.put(i, new Object());
                    lfu.get(i);
                    lfu.get(i);
                });
        assertTrue(lfu.get(keyWithMaxFrequency).isPresent());
        assertFalse(lfu.get(keyWithFrequency_1).isPresent());
    }

    @DisplayName("cache set frequency to zero when put existed key with the new value")
    @Test
    void testAddKeyThatAlreadyContainsInCacheWithNewValue() {
        int keyWithMaxFrequency = 2;
        int keyWithFrequency_1 = 1;
        fillCacheWithObjectsAndIntegerKeysNTimes(lfu, 3);

        IntStream.range(0, 3)
                .forEach(i -> lfu.get(keyWithMaxFrequency));
        IntStream.range(0, 1)
                .forEach(i -> lfu.get(keyWithFrequency_1));

        assertTrue(lfu.get(keyWithMaxFrequency).isPresent());

        lfu.put(keyWithMaxFrequency, new Object());

        IntStream.range(5, 10)
                .forEach(i -> {
                    lfu.put(i, new Object());
                    lfu.get(i);
                    lfu.get(i);
                });
        assertFalse(lfu.get(keyWithMaxFrequency).isPresent());
        assertFalse(lfu.get(keyWithFrequency_1).isPresent());
    }
}
