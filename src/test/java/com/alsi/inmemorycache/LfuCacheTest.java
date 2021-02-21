package com.alsi.inmemorycache;

import com.alsi.inmemorycache.core.Cache;
import com.alsi.inmemorycache.core.CacheFactory;
import com.alsi.inmemorycache.core.utils.CacheException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Lfu cache test: ")
public class LfuCacheTest extends BaseCacheTest {

    private Cache<Integer, Object> lfu;

    @BeforeEach
    void init() {
        lfu = CacheFactory.get(3, CacheFactory.EvictionStrategy.LEAST_FREQUENCY_USED);
    }

    @DisplayName("cache let survive only objects with maximum call frequency")
    @Test
    void lfuCacheBaseTest() {

        // fill cache
        lfu.put(1, new Object());
        lfu.put(2, new Object());
        lfu.put(3, new Object());

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
    void tryToAddNullKeyInLfuCache() {
        assertThrows(CacheException.class, () -> tryToAddNullKey(lfu));
    }
}
