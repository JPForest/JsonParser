package com.github.hasys;

import static java.lang.Character.isWhitespace;
import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

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
            throw new IllegalArgumentException(unexpectedTokenPresentExpected(JsonToken.START_OBJECT));
        }
        currentPosition++;

        JsonObject jsonObject = new JsonObject();
        while (currentPosition < data.length()) {
            switch (parseNextToken()) {
                case STRING:
                    String string = parseString();
                    if (parseNextToken() != JsonToken.COLON) {
                        throw new IllegalArgumentException(unexpectedTokenPresentExpected(JsonToken.COLON));
                    }
                    currentPosition++;
                    Object value = parseValue();
                    jsonObject.addValue(string, value);
                    currentPosition++;
                    break;

                case END_OBJECT:
                    if (jsonObject.getValuesCount() == 0) {
                        return JsonObject.EMPTY;
                    }
                    return jsonObject;

                default:
                    throw new IllegalArgumentException(data + System.lineSeparator() + " is not a valid json object");
            }
        }

        throw new IllegalArgumentException(data + System.lineSeparator() + " is not a valid json object");
    }

    private Object parseValue() {
        switch (parseNextToken()) {
            case START_OBJECT:
                return parseObject();
            case STRING:
                return parseString();
            default:
                throw new IllegalArgumentException(
                        unexpectedTokenPresentExpected(JsonToken.START_OBJECT, JsonToken.STRING)
                );
        }
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
        if (data.charAt(currentPosition) != '"') {
            throw new IllegalArgumentException("String token has to start with \" symbol.");
        }
        currentPosition++;

        int start = currentPosition;
        while (data.charAt(currentPosition) != '"') {
            currentPosition++;
        }
        String string = data.substring(start, currentPosition);
        currentPosition++;

        return string;
    }

    private String unknownTokenErrorMessage() {
        return String.format("Unknown token '%s' at position %s", data.charAt(currentPosition), currentPosition);
    }

    private String unexpectedTokenPresentExpected(JsonToken... expectedToken) {
        return String.format("Unexpected token '%s' at position %s. Expected one of '%s'.",
                currentToken(), currentPosition, Arrays.toString(expectedToken));
    }
}
