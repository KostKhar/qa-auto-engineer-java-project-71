package hexlet.code;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ParserTest {

    @Test
    void parse_jsonContent_returnsMap() throws Exception {
        String content = """
                {
                  "name": "resources",
                  "version": "1.0.0"
                }""";
        Map<String, Object> result = Parser.parse(content, "json");

        assertAll(
                () -> assertEquals("resources", result.get("name")),
                () -> assertEquals("1.0.0", result.get("version")));
    }

    @Test
    void parse_yamlContent_returnsMap() throws Exception {
        String content = """
                name: John
                age: 30""";
        Map<String, Object> result = Parser.parse(content, "yaml");

        assertAll(
                () -> assertEquals("John", result.get("name")),
                () -> assertNotNull(result.get("age")));
    }

    @Test
    void parse_ymlFormatAlias_returnsMap() throws Exception {
        String content = "name: John";
        Map<String, Object> result = Parser.parse(content, "yml");

        assertEquals("John", result.get("name"));
    }

    @Test
    void parse_emptyContent_returnsEmptyMap() throws Exception {
        assertTrue(Parser.parse("   ", "json").isEmpty());
    }

    @Test
    void parse_nullContent_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> Parser.parse(null, "json"));
    }

    @Test
    void parse_nullFormat_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> Parser.parse("{}", null));
    }

    @Test
    void parse_unsupportedFormat_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> Parser.parse("{}", "xml"));
    }
}
