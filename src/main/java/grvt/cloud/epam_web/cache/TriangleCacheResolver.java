package grvt.cloud.epam_web.cache;


import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;

@Component
public class TriangleCacheResolver {
    private Map<Integer, String> map = new HashMap<Integer, String>();
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
