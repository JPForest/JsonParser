package com.github.hasys;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class JsonParseTest {

    @Test
    public void emptyStringTest() {
        assertEquals(JsonParser.parse("{}"), JsonObject.EMPTY);
        assertEquals(JsonParser.parse(""), JsonObject.EMPTY);
        assertEquals(JsonParser.parse(null), JsonObject.EMPTY);
    }
}
