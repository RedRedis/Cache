import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
//        Cache<String, Integer> cache = new Cache<>(3, String::length); // k -> k.length()
//        cache.put("aaa", 3);
//        cache.put("bbb", 3);
//        cache.put("cccccc", 6);
//        cache.put("aaa", 3);
//        cache.put("dd", 2);
//
//
//
//        int aaa = cache.get("aaa");
//        int bbb = cache.get("bbb");
//
//        System.out.println(aaa + bbb);


        UniqueCache<String, Integer> cache = new UniqueCache<>(3, String::length);


        cache.put("aaa", 3);
        cache.put("bbb", 3);
        cache.put("cccccc", 6);
        cache.put("aaa", 3);
        cache.put("dd", 2);

        System.out.println(cache.get("aaa"));
        System.out.println(cache.get("bbb"));
        System.out.println(cache.get("cccccc"));
        System.out.println(cache.get("dd"));

//        FastCache<String, Integer> cache = new FastCache<>(3, String::length);
//        cache.put("aaa", 3);
//        cache.put("bbb", 3);
//        cache.put("cccccc", 6);
//        cache.put("aaa", 3);
//        cache.put("dd", 2);
//
//        System.out.println(cache.get("aaa"));
//        System.out.println(cache.get("bbb"));
//        System.out.println(cache.get("cccccc"));
//        System.out.println(cache.get("dd"));
    }
}