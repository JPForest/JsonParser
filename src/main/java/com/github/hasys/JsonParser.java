package com.github.hasys;

import static java.lang.Character.isWhitespace;
import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JsonParser {

    private int currentPosition = 0;
    private final String data;

    public JsonParser(String data) {
        this.data = data.trim();
    }

    public JsonParser(URI pathToFile) throws IOException {
        this.data = new String(Files.readAllBytes(Paths.get(pathToFile)), UTF_8).trim();
    }

    /**
     * Parse method parse json sting or file, it is not called automatically for lazy evaluation imitation.
     *
     * @return parsed {@link JsonObject}
     */
    public JsonObject parse() {
        currentPosition = 0;
        JsonObject jsonObject = parseObject();

        if (currentPosition != data.length() - 1) {
            throw new IllegalArgumentException(data + System.lineSeparator() + " is not a valid json object");
        }

        return  jsonObject;
    }

    private JsonObject parseObject() {
        if (parseNextToken() != JsonToken.START_OBJECT) {
            throw new IllegalArgumentException(
                    String.format("Unexpected token '%s' at position %s", currentToken(), currentPosition)
            );
        }
        currentPosition++;

        JsonObject jsonObject = new JsonObject();
        while (currentPosition < data.length()) {
            JsonToken currentToken = parseNextToken();

            if (currentToken == JsonToken.STRING) {
                String string = parseString();
                if (parseNextToken() != JsonToken.COLON) {
                    throw new IllegalArgumentException(
                            String.format("Unexpected token '%s' at position %s", currentToken(), currentPosition)
                    );
                }
                currentPosition++;
                Object value = parseValue();
                jsonObject.addValue(string, value);
                currentPosition++;
            } else if (currentToken == JsonToken.END_OBJECT) {
                if (jsonObject.getValuesCount() == 0) {
                    return JsonObject.EMPTY;
                } else {
                    return jsonObject;
                }
            } else {
                throw new IllegalArgumentException(data + System.lineSeparator() + " is not a valid json object");
            }
        }

        throw new IllegalArgumentException(data + System.lineSeparator() + " is not a valid json object");
    }

    private Object parseValue() {
        return parseObject();
    }

    private JsonToken parseNextToken() {
        JsonToken token = null;
        for (int i = currentPosition; i < data.length(); i++) {
            if (isWhitespace(data.charAt(i))) {
                continue;
            }

            currentPosition = i;
            token = currentToken();
            break;
        }

        if (token == null) {
            throw new IllegalArgumentException(
                    String.format("Token not found in string: \"%s\"", data)
            );
        }

        return token;
    }

    private JsonToken currentToken() {
        switch (data.charAt(currentPosition)) {
            case '{':
                return JsonToken.START_OBJECT;
            case '}':
                return JsonToken.END_OBJECT;
            case '"':
                return JsonToken.STRING;
            case ':':
                return JsonToken.COLON;
            default:
                throw new IllegalArgumentException(unknownTokenErrorMessage());
        }
    }

    protected String parseString() {
        if (data.charAt(currentPosition) == '"') {
            currentPosition++;
            int start = currentPosition;
            while (data.charAt(currentPosition) != '"') {
                currentPosition++;
            }
            String string = data.substring(start, currentPosition);
            currentPosition++;
            return string;
        } else {
            throw new IllegalArgumentException("String token has to start with \" simbol.");
        }
    }

    private String unknownTokenErrorMessage() {
        return String.format("Unknown token '%s' at position %s", data.charAt(currentPosition), currentPosition);
    }
}
