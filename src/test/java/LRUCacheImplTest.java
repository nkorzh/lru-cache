import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class LRUCacheImplTest {

    @Test
    void returnsSingleElement() {
        LRUCache<Integer, Integer> cache = new LRUCacheImpl<>(1);
        cache.put(1, 2);

        Integer returnedValue = cache.get(1);

        assertEquals(2, returnedValue);
    }

    @Test
    void overwritesValue() {
        LRUCache<Integer, Integer> cache = new LRUCacheImpl<>(1);
        cache.put(1, 2);
        cache.put(1, 3);

        Integer returnedValue = cache.get(1);

        assertEquals(3, returnedValue);
    }

    @Test
    void returnsNullForNonexistentKey() {
        LRUCache<Integer, Integer> cache = new LRUCacheImpl<>(1);
        cache.put(1, 2);

        Integer returnedValue = cache.get(2);

        assertNull(returnedValue);
    }

    @Test
    void removesLastAdded() {
        LRUCache<Integer, Integer> cache = new LRUCacheImpl<>(1);
        cache.put(1, 2);
        cache.put(2, 3);

        Integer valueKey1 = cache.get(1);

        assertNull(valueKey1);
    }

    @Test
    void removesLastAdded_2() {
        LRUCache<Integer, Integer> cache = new LRUCacheImpl<>(2);
        cache.put(1, 2);
        cache.put(2, 3);
        cache.get(1);
        cache.put(3, 4);

        Integer valueKey2 = cache.get(2);
        Integer valueKey1 = cache.get(1);
        Integer valueKey3 = cache.get(3);

        assertNull(valueKey2);
        assertEquals(2, valueKey1);
        assertEquals(4, valueKey3);
    }

    @Test
    void throwsForNullKeyPut() {
        LRUCache<Integer, Integer> cache = new LRUCacheImpl<>(1);

        assertThrows(NullPointerException.class, () -> cache.put(null, 2));
    }

    @Test
    void throwsForNullKeyGet() {
        LRUCache<Integer, Integer> cache = new LRUCacheImpl<>(1);

        assertThrows(NullPointerException.class, () -> cache.get(null));
    }

    @Test
    void throwsForNullValue() {
        LRUCache<Integer, Integer> cache = new LRUCacheImpl<>(1);

        assertThrows(NullPointerException.class, () -> cache.put(1, null));
    }
}
