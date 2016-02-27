package com.github.hasys;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class JsonParseTest {

    @Test(expected = IllegalArgumentException.class)
    public void emptyStringTest() {
        assertEquals(JsonParser.parse(""), JsonObject.EMPTY);
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullStringTest() {
        assertEquals(JsonParser.parse(null), JsonObject.EMPTY);
    }

    @Test
    public void emptyObjectTest() {
        assertEquals(JsonParser.parse("{}"), JsonObject.EMPTY);
    }

    @Test(expected = IllegalArgumentException.class)
    public void incorrectObjectTest() {
        JsonParser.parse("{");
    }
}
