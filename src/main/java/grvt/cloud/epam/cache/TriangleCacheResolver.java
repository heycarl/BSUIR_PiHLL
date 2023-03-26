package grvt.cloud.epam.cache;


import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class TriangleCacheResolver {
    private final Map<Integer, String> map = new HashMap<>();
    public String getValue(Integer value) {
        return map.get(value);
    }
    public String putValue(Integer value, String result) {
        map.put(value, result);
        return result;
    }

    public boolean containsValue(Integer key) {
        return map.containsKey(key);
    }
}
