import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;

public class FastCache<K,V> {

    private final Function<K, V> valueByKeyGenerator;

    private final Map<K, V> data;

    public FastCache(int maxSize, Function<K, V> valueByKeyGenerator) {
        this.valueByKeyGenerator = valueByKeyGenerator;
        this.data = new LinkedHashMap<>(maxSize, 0.75f, true) {
            protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
                return size() > maxSize;
            }
        };
    }

    public void put(K key) {
        put(key, valueByKeyGenerator.apply(key));
    }

    public void put(K key, V value) {
        data.put(key, value);
    }

    public V get(K key) {
//        if (!data.containsKey(key)) {
//            put(key, valueByKeyGenerator.apply(key));
//        }
        return data.get(key);
    }

}
