import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;

public class UniqueCache<K, V> {

    private final int maxSize;
    private final Function<K, V> valueByKeyGenerator;

    private final Map<K, V> data = new HashMap<>();
    private final Map<K, LocalDateTime> keyTimestamps = new HashMap<>();
    private final SortedMap<LocalDateTime, Set<K>> timestampToKey = new TreeMap<>();

    public UniqueCache(int maxSize, Function<K, V> valueByKeyGenerator) {
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
            K minKey = timestampToKey.get(minTime).stream().findAny().get();

            data.remove(minKey);
            keyTimestamps.remove(minKey);

            timestampToKey.get(minTime).remove(minKey);
            if (timestampToKey.get(minTime).isEmpty()) {
                timestampToKey.remove(minTime);
            }
        }
        data.put(key, value);
        keyTimestamps.put(key, now);
        if (!timestampToKey.containsKey(now)) {
            timestampToKey.put(now, new HashSet<>());
        }
        timestampToKey.get(now).add(key);
    }

    public V get(K key) {
        if (keyTimestamps.containsKey(key)) {
            LocalDateTime now = LocalDateTime.now();

            timestampToKey.get(keyTimestamps.get(key)).remove(key);
            if (timestampToKey.get(keyTimestamps.get(key)).isEmpty()) {
                timestampToKey.remove(keyTimestamps.get(key));
            }
            if (!timestampToKey.containsKey(now)) {
                timestampToKey.put(now, new HashSet<>());
            }

            timestampToKey.get(now).add(key);
            keyTimestamps.put(key, now);
        } else {
            put(key, valueByKeyGenerator.apply(key));
        }
        return data.get(key);
    }
}
