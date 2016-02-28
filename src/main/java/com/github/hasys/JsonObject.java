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

    /**
     * Return JsonObject value of member.
     *
     * @param name is string name of required member value
     * @return value of member as JsonObject
     * @throws IllegalStateException if value of member is not a JsonObject
     */
    public JsonObject getJsonObjectByName(String name) {
        Object o = values.get(name);
        if (!(o instanceof JsonObject)) {
            throw new IllegalStateException("Member " + name + " is not a JsonObject");
        }

        return (JsonObject) o;
    }
}
