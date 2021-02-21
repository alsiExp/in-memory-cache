package com.alsi.inmemorycache;

import com.alsi.inmemorycache.core.Cache;
import org.junit.jupiter.api.DisplayName;


@DisplayName("Base cache test")
public class BaseCacheTest {


    protected void tryToAddNullKey(Cache<Integer, Object> cache) {
        cache.put(null, new Object());
    }

}
