package hexlet.code;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class FileParserTest {

    private static final String FILE1_JSON = "src/test/resources/file1.json";
    private static final String FILE1_YML = "src/test/resources/file1Yml.yml";
    private static final String FILE_EMPTY = "src/test/resources/file3.json";
    private static final String FILE_INVALID = "src/test/resources/file4.json";

    @Test
    void parse_jsonFile_returnsNonNullMap() {
        assertNotNull(FileParser.parse(FILE1_JSON));
    }

    @Test
    void parse_ymlFile_returnsNonNullMap() {
        assertNotNull(FileParser.parse(FILE1_YML));
    }

    @Test
    void parse_jsonFile_containsExpectedKeys() {
        Map<String, Object> result = FileParser.parse(FILE1_JSON);
        assertAll(
                () -> assertTrue(result.containsKey("name")),
                () -> assertTrue(result.containsKey("version")),
                () -> assertTrue(result.containsKey("timeout")),
                () -> assertTrue(result.containsKey("main")),
                () -> assertTrue(result.containsKey("private")),
                () -> assertTrue(result.containsKey("field")));
    }

    @Test
    void parse_jsonFile_containsExpectedValues() {
        Map<String, Object> result = FileParser.parse(FILE1_JSON);
        assertAll(
                () -> assertEquals("resources", result.get("name")),
                () -> assertEquals("1.0.0", result.get("version")),
                () -> assertEquals(20, result.get("timeout")),
                () -> assertEquals("index.js", result.get("main")),
                () -> assertEquals(false, result.get("private")),
                () -> assertNull(result.get("field")));
    }

    @Test
    void parse_ymlFile_containsExpectedKeys() {
        Map<String, Object> result = FileParser.parse(FILE1_YML);
        assertAll(
                () -> assertTrue(result.containsKey("name")),
                () -> assertTrue(result.containsKey("age")),
                () -> assertTrue(result.containsKey("email")),
                () -> assertTrue(result.containsKey("address")),
                () -> assertTrue(result.containsKey("hobbies")));
    }

    @Test
    void parse_ymlFile_containsExpectedValues() {
        Map<String, Object> result = FileParser.parse(FILE1_YML);
        assertAll(
                () -> assertEquals("John Doe", result.get("name")),
                () -> assertEquals(30, result.get("age")),
                () -> assertEquals("john@example.com", result.get("email")));
    }

    @Test
    void parse_ymlFile_nestedObjectParsedCorrectly() {
        Map<String, Object> result = FileParser.parse(FILE1_YML);
        Object address = result.get("address");
        assertInstanceOf(Map.class, address);
    }

    @Test
    void parse_ymlFile_listParsedCorrectly() {
        Map<String, Object> result = FileParser.parse(FILE1_YML);
        Object hobbies = result.get("hobbies");
        assertInstanceOf(List.class, hobbies);
    }

    @Test
    void parse_emptyFile_returnsEmptyMap() {
        assertTrue(FileParser.parse(FILE_EMPTY).isEmpty());
    }

    @Test
    void parse_jsonFile_keysAreSortedAlphabetically() {
        Map<String, Object> result = FileParser.parse(FILE1_JSON);
        List<String> keys = List.copyOf(result.keySet());
        List<String> sortedKeys = keys.stream().sorted().toList();
        assertEquals(sortedKeys, keys);
    }

    @Test
    void parse_ymlFile_keysAreSortedAlphabetically() {
        Map<String, Object> result = FileParser.parse(FILE1_YML);
        List<String> keys = List.copyOf(result.keySet());
        List<String> sortedKeys = keys.stream().sorted().toList();
        assertEquals(sortedKeys, keys);
    }

    @Test
    void parse_nullArgument_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> FileParser.parse(null));
    }

    @Test
    void parse_nonExistentFile_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,
                () -> FileParser.parse("src/test/resources/nonexistent.json"));
    }

    @Test
    void parse_invalidJson_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> FileParser.parse(FILE_INVALID));
    }
}
