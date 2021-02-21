package com.alsi.inmemorycache;

import com.alsi.inmemorycache.core.Cache;
import org.junit.jupiter.api.DisplayName;

import java.util.Optional;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;


@DisplayName("Base cache test")
public class BaseCacheTest {

    protected void fillCacheWithObjectsAndIntegerKeysNTimes(Cache<Integer, Object> cache, int nTimesCount) {
        fillCacheWithObjectsAndIntegerKeysNTimes(cache, nTimesCount, 1);

    }

    protected void fillCacheWithObjectsAndIntegerKeysNTimes(Cache<Integer, Object> cache, int nTimesCount, int startFromKey) {
        IntStream.range(startFromKey, startFromKey + nTimesCount)
                .forEach(index -> cache.put(index, new Object()));

    }

    protected void testTryToAddNullKey(Cache<Integer, Object> cache) {
        cache.put(null, new Object());
    }

    protected void testThatCacheCanStoreAtLeastOneElement(Cache<Integer, Object> cache) {
        fillCacheWithObjectsAndIntegerKeysNTimes(cache, 2);
        int lastAwaitingKey = 2;
        assertTrue(cache.get(lastAwaitingKey).isPresent());
        assertNotNull(cache.get(lastAwaitingKey).orElse(null));
    }


    protected void testThatGetObjectFromEmptyCacheFindNothing(Cache<Integer, Object> cache) {
        testThatGetObjectFromEmptyCacheFindNothing(cache, 1);
    }

    protected void testThatGetObjectFromEmptyCacheFindNothing(Cache<Integer, Object> cache, int testValue) {
        assertFalse(cache.contains(testValue));
        Optional<Object> opt = cache.get(1);
        assertFalse(opt.isPresent());
    }

}
