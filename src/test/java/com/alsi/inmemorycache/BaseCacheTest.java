package com.alsi.inmemorycache;

import com.alsi.inmemorycache.core.Cache;
import org.junit.jupiter.api.DisplayName;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


@DisplayName("Base cache test")
public class BaseCacheTest {


    protected void testTryToAddNullKey(Cache<Integer, Object> cache) {
        cache.put(null, new Object());
    }

    protected void testThatCacheCanStoreAtLeastOneElement(Cache<Integer, Object> cache) {
        fillCacheWithObjectsAndIntegerKeysNTimes(cache, 2);
        int lastAwaitingKey = 2;
        assertTrue(cache.get(lastAwaitingKey).isPresent());
        assertNotNull(cache.get(lastAwaitingKey).orElse(null));
    }

    protected void fillCacheWithObjectsAndIntegerKeysNTimes(Cache<Integer, Object> cache, int nTimesCount) {
        fillCacheWithObjectsAndIntegerKeysNTimes(cache, nTimesCount, 1);

    }

    protected void fillCacheWithObjectsAndIntegerKeysNTimes(Cache<Integer, Object> cache, int nTimesCount, int startFromKey) {
        IntStream.range(startFromKey, startFromKey + nTimesCount)
                .forEach(index -> cache.put(index, new Object()));

    }

}
