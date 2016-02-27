package com.github.hasys;

public class JsonParser {

    public static JsonObject parse(String data) {
        if (data == null || data.length() == 0) {
            throw new IllegalArgumentException("Json string can not to be null or empty");
        }

        if (!data.equals("{}")) {
            throw new IllegalArgumentException("Json string is not a valid json");
        }

        return JsonObject.EMPTY;
    }
}
