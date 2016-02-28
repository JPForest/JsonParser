package com.github.hasys;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.junit.Test;

public class JsonParseTest {

    @Test(expected = IllegalArgumentException.class)
    public void testEmptyString() {
        new JsonParser("").parse();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testOnlyOpeningCurlyBrace() {
        new JsonParser("{").parse();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testOnlyClosingCurlyBrace() {
        new JsonParser("}").parse();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testExcessOpeningCurlyBrace() {
        new JsonParser("  { { } ").parse();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testExcessClosingCurlyBrace() {
        new JsonParser("  { } } ").parse();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIncorrectNotationObjectInsideObject() {
        new JsonParser("{ {  }  }   ").parse();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testObjectWithoutEncloseCurlyBracket() {
        new JsonParser("{ 0").parse();
    }

    @Test
    public void testEmptyObject() {
        assertEquals(JsonObject.EMPTY, new JsonParser("{}").parse());
    }

    @Test
    public void testWhiteSpacedObject() {
        assertEquals(JsonObject.EMPTY, new JsonParser(" \t\r\n{ \r\n\t} \t\r\n").parse());
    }

    @Test
    public void testEmptyObjectFromFile() throws IOException, URISyntaxException {
        ClassLoader classLoader = getClass().getClassLoader();
        URI fileUri = classLoader.getResource("emptyObject.json").toURI();
        assertEquals(JsonObject.EMPTY, new JsonParser(fileUri).parse());
    }

    @Test
    public void testObjectWithEmptyObjectValue() {
        JsonParser jsonParser = new JsonParser("{ \"first\": {} }");
        JsonObject jsonObject = jsonParser.parse();
        assertEquals(jsonObject.getValuesCount(), 1);
        assertEquals(JsonObject.EMPTY, jsonObject.getValueByName("first"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMemberWithoutValue() {
        new JsonParser("{ \"first\": } }").parse();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMemberWithoutColon() {
        new JsonParser("{ \"first\" {} }").parse();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMemberWithWrongString() {
        new JsonParser("{ first: {} }").parse();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWrongInvocationOfParseStringMethod() {
        new JsonParser("first").parseString();
    }

}
