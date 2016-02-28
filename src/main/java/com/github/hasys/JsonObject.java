package com.github.hasys;

import java.util.HashMap;
import java.util.Map;

public class JsonObject {

    public static final JsonObject EMPTY = new JsonObject();
    public final Map<String, Object> values = new HashMap<>();

    public int getValuesCount() {
        return values.size();
    }

    public Object getValueByName(String name) {
        return values.get(name);
    }

    public void addValue(String key, Object value) {
        values.put(key, value);
    }
}
