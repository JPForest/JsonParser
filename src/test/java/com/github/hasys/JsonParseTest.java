package com.github.hasys;

import static org.junit.Assert.*;

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

    @Test(expected = IllegalArgumentException.class)
    public void incorrectObjectTest() {
        JsonParser.parse("{");
    }

    @Test(expected = IllegalArgumentException.class)
    public void incorrectObject2Test() {
        JsonParser.parse("  { { } ");
    }

    @Test(expected = IllegalArgumentException.class)
    public void incorrectObject3Test() {
        JsonParser.parse("  { } } ");
    }

    @Test(expected = IllegalArgumentException.class)
    public void incorrectObject4Test() {
        JsonParser.parse("{ {  }  }   ");
    }

    @Test
    public void emptyObjectTest() {
        assertEquals(JsonParser.parse("{}"), JsonObject.EMPTY);
    }

    @Test
    public void whiteSpacedObjectTest() {
        assertEquals(JsonParser.parse(" {}"), JsonObject.EMPTY);
        assertEquals(JsonParser.parse("\t{}"), JsonObject.EMPTY);
        assertEquals(JsonParser.parse("\n{}"), JsonObject.EMPTY);
        assertEquals(JsonParser.parse("\r\n{}"), JsonObject.EMPTY);
        assertEquals(JsonParser.parse(" \t\r\n{ \r\n\t} \t\r\n"), JsonObject.EMPTY);
    }
}
