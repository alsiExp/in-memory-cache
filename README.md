# In-memory-cache


Create an in-memory cache (for caching Objects) with configurable max size and eviction strategy.
Two strategies should be implemented: LRU and LFU.
For this task it is assumed that only one thread will access the cache, so there is no need to make it thread-safe.
Please provide an example of usage of the cache as a unit test(s). 

## Implementation details
1. Cache didn't support null value as a key, if you try to do this *CacheException* will bet thrown
2. When method *contains(K key)* called for LFU implementation frequency for this element will be increased
3. For LRU cache if the same key puts this element take his place in the end of the queue.
4. For LFU cache if the same key puts and value object is the same happens nothing, but if value object is new - frequency will be set to zero.
5. Method get returns *Optional* and best way to use this - don't forget about *.orElseGet(() -> call_dao)*    

## How to run
To run test please use:
> mvn clean test
 
