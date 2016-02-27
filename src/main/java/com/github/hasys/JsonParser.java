package com.github.hasys;

import static java.lang.Character.isWhitespace;

public class JsonParser {

    public static JsonObject parse(String data) {
        if (data == null || data.length() == 0) {
            throw new IllegalArgumentException("Json string can not to be null or empty");
        }

        JsonObject jsonObject;
        if (parseNextToken(data, 0) == JsonToken.START_OBJECT) {
            jsonObject = parseObject(data);
        } else {
            throw new IllegalArgumentException("Json string has to start with an json Object");
        }

        return jsonObject;
    }

    public static JsonObject parseObject(String data) {
        boolean insideObject = false;
        JsonObject jsonObject = null;
        for (int i = 0; i < data.length(); i++) {
            if (isWhitespace(data.charAt(i))) {
                continue;
            }
            JsonToken token = parseNextToken(data, i);
            if (jsonObject != null) {
                throw new IllegalArgumentException(
                        String.format("Unexpected token %s at position %s", token, i)
                );
            }

            if (insideObject) {
                if (token == JsonToken.END_OBJECT) {
                    jsonObject = JsonObject.EMPTY;
                    insideObject = false;
                } else {
                    throw new IllegalArgumentException(
                            String.format("Invalid token %s at position %s", token, i)
                    );
                }
            } else {
                if (token == JsonToken.START_OBJECT) {
                    insideObject = true;
                }
            }
        }

        if (jsonObject == null) {
            throw new IllegalArgumentException(data + System.lineSeparator() +" is not a valid json object");
        }

        return JsonObject.EMPTY;
    }

    private static JsonToken parseNextToken(String data, int position) {
        for (int i = position; i < data.length(); i++) {
            if (isWhitespace(data.charAt(i))) {
                continue;
            }

            switch (data.charAt(i)) {
                case '{':
                    return JsonToken.START_OBJECT;
                case '}':
                    return JsonToken.END_OBJECT;
                default:
                    throw new IllegalArgumentException(
                            String.format("Unknown token %s at position %s", data.charAt(position), position)
                    );
            }
        }

        throw new IllegalArgumentException(
                String.format("Token not found in string: \"%s\"", data)
        );
    }
}
