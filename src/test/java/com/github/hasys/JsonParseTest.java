package com.github.hasys;

import static org.junit.Assert.*;

import org.junit.Test;

public class JsonParseTest {

    @Test(expected = IllegalArgumentException.class)
    public void emptyStringTest() {
        new JsonParser("").parse();
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullStringTest() {
        new JsonParser(null).parse();
    }

    @Test(expected = IllegalArgumentException.class)
    public void incorrectObjectTest() {
        new JsonParser("{").parse();
    }

    @Test(expected = IllegalArgumentException.class)
    public void incorrectObject2Test() {
        new JsonParser("}").parse();
    }

    @Test(expected = IllegalArgumentException.class)
    public void incorrectObject3Test() {
        new JsonParser("  { { } ").parse();
    }

    @Test(expected = IllegalArgumentException.class)
    public void incorrectObject4Test() {
        new JsonParser("  { } } ").parse();
    }

    @Test(expected = IllegalArgumentException.class)
    public void incorrectObject5Test() {
        new JsonParser("{ {  }  }   ").parse();
    }

    @Test(expected = IllegalArgumentException.class)
    public void incorrectObject6Test() {
        new JsonParser("{ 0").parse();
    }

    @Test
    public void emptyObjectTest() {
        assertEquals(new JsonParser("{}").parse(), JsonObject.EMPTY);
    }

    @Test
    public void whiteSpacedObjectTest() {
        assertEquals(new JsonParser(" {}").parse(), JsonObject.EMPTY);
        assertEquals(new JsonParser("\t{}").parse(), JsonObject.EMPTY);
        assertEquals(new JsonParser("\n{}").parse(), JsonObject.EMPTY);
        assertEquals(new JsonParser("\r\n{}").parse(), JsonObject.EMPTY);
        assertEquals(new JsonParser(" \t\r\n{ \r\n\t} \t\r\n").parse(), JsonObject.EMPTY);
    }
}
