import java.util.Map.Entry;
import net.jcip.annotations.NotThreadSafe;

import static java.util.Map.entry;

@NotThreadSafe
public class LRUCacheImpl<K, V> extends LRUCacheAbstract<K, V> {

    public LRUCacheImpl(int maxSize) {
        super(maxSize);
    }

    @Override
    protected V doGet(K key) {
        Node<Entry<K, V>> node = cache.get(key);
        if (node == null) {
            return null;
        }
        accessList.moveToFirst(node);
        return node.value.getValue();
    }

    @Override
    protected void doPut(K key, V value) {
        Node<Entry<K, V>> existingNode = cache.get(key);
        if (existingNode != null) {
            existingNode.value = entry(key, value);
            accessList.moveToFirst(existingNode);
        } else {
            Node<Entry<K, V>> newNode = new Node<>(entry(key, value));

            cache.put(key, newNode);
            accessList.addFirst(newNode);

            if (accessList.getSize() > maxSize) {
                dropLeastRecentlyUsed();
            }
        }
    }

    private void dropLeastRecentlyUsed() {
        Node<Entry<K, V>> lastUsedNode = accessList.removeLast();
        cache.remove(lastUsedNode.value.getKey());
    }
}
