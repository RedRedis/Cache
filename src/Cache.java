import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.function.Function;

public class Cache<K, V> {

    private final int maxSize;
    private final Function<K, V> valueByKeyGenerator;

    private final Map<K, V> data = new HashMap<>();
    private final Map<K, LocalDateTime> keyTimestamps = new HashMap<>();
    private final SortedMap<LocalDateTime, K> timestampToKey = new TreeMap<>();

    public Cache(int maxSize, Function<K, V> valueByKeyGenerator) {
        this.maxSize = maxSize;
        this.valueByKeyGenerator = valueByKeyGenerator;
    }

    public void put(K key) {
        put(key, valueByKeyGenerator.apply(key));
    }

    public void put(K key, V value) {
        LocalDateTime now = LocalDateTime.now();
        if (data.size() == maxSize) {
            LocalDateTime minTime = timestampToKey.firstKey();
            K minKey = timestampToKey.get(minTime);

            data.remove(minKey);
            keyTimestamps.remove(minKey);
            timestampToKey.remove(minTime);
        }
        data.put(key, value);
        keyTimestamps.put(key, now);
        timestampToKey.put(now, key);
    }

    public V get(K key) {
        if (keyTimestamps.containsKey(key)) {
            LocalDateTime now = LocalDateTime.now();
            timestampToKey.remove(keyTimestamps.get(key));
            keyTimestamps.put(key, now);
            timestampToKey.put(now, key);
        } else {
            put(key, valueByKeyGenerator.apply(key));
        }
        return data.get(key);
    }
}
