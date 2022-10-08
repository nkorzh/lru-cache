import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import lombok.NonNull;
import org.jetbrains.annotations.Nullable;

public abstract class LRUCacheAbstract<K, V> implements LRUCache<K, V> {
    protected final Map<K, Node<Entry<K, V>>> cache;
    protected final AccessTracker<Entry<K, V>> accessList;
    protected final int maxSize;

    public LRUCacheAbstract(int maxSize) {
        if (maxSize <= 0) {
            throw new IllegalArgumentException("Max cache size should be at least 1");
        }
        this.maxSize = maxSize;
        cache = new HashMap<>(maxSize);
        accessList = new AccessTracker<>();
    }

    /**
     * Returns the value to which the specified key is mapped,
     * or null if this cache contains no mapping for the key.
     *
     * @param key the key whose associated value is to be returned
     * @return the value to which the specified key is mapped,
     * or null if this cache contains no mapping for the key
     * @throws NullPointerException if the key is null
     */
    @Override
    @Nullable
    public V get(@NonNull K key) {
        int oldSize = cache.size();
        assert accessList.getSize() == oldSize;
        assert oldSize <= maxSize : "Cache exceeds max size";

        V result = doGet(key);

        assert cache.size() == oldSize;
        assert accessList.getSize() == oldSize;
        return result;
    }

    protected abstract V doGet(K key);

    /**
     * Associates the specified value with the specified key in this cache.
     * If the cache previously contained a mapping for the key, the old value is replaced.
     *
     * @param key   key with which the specified value is to be associated
     * @param value value to be associated with the specified key
     * @throws NullPointerException if the key or value is null
     */
    @Override
    public void put(@NonNull K key, @NonNull V value) {
        assert accessList.getSize() == cache.size();
        assert cache.size() <= maxSize;

        doPut(key, value);

        assert accessList.getSize() == cache.size();
        assert cache.size() <= maxSize : "Cache exceeds max size";
    }

    protected abstract void doPut(K key, V value);
}
