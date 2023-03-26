package grvt.cloud.epam.cache;


import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class TriangleCacheResolver {
    private final Map<Integer, String> cacheMap = new HashMap<>();
    public String getValue(Integer value) {
        return cacheMap.get(value);
    }
    public String putValue(Integer value, String result) {
        cacheMap.put(value, result);
        return result;
    }

    public boolean containsValue(Integer key) {
        return cacheMap.containsKey(key);
    }
}
