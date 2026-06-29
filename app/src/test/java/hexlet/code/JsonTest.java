package hexlet.code;

import hexlet.code.formatters.Json;
import org.junit.jupiter.api.Test;

import static hexlet.code.FileParser.parse;
import static org.junit.jupiter.api.Assertions.*;

class JsonTest extends BaseTest {

    String generateDiff(String file1, String file2) {
        try {
            return Json.render(DiffBuilder.toJsonMap(DiffBuilder.build(parse(file1), parse(file2))));
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void jsonFiles_resultNotNull() {
        assertNotNull(resultJson);
    }

    @Test
    void jsonFiles_returnsExpectedDiff() {
        String expected = """
                [{"key":"description","type":"deleted","value":""},{"key":"field","type":"deleted","value":null},
                {"key":"main","type":"unchanged","value":"index.js"},{"key":"name","type":"unchanged","value":"resources"},
                {"key":"private","type":"changed","value":true,"previousValue":false},{"key":"proxy","type":"added","value":"123.234.53.22"},
                {"key":"timeout","type":"changed","value":50,"previousValue":20},{"key":"version","type":"unchanged","value":"1.0.0"}]""";
        assertEquals(expected, resultJson);
    }

    @Test
    void jsonFiles_doesNotContainStylishMarkers() {
        assertAll(
                () -> assertFalse(resultJson.contains("+ ")),
                () -> assertFalse(resultJson.contains("- ")));
    }

    @Test
    void jsonFiles_unchangedFieldsKeepOriginalValues() {
        assertAll(
                () -> assertTrue(resultJson.contains("\"key\":\"main\",\"type\":\"unchanged\",\"value\":\"index.js\"")),
                () -> assertTrue(resultJson.contains("\"key\":\"name\",\"type\":\"unchanged\",\"value\":\"resources\"")),
                () -> assertTrue(resultJson.contains("\"key\":\"version\",\"type\":\"unchanged\",\"value\":\"1.0.0\"")));
    }

    @Test
    void checkOneEmptyJsonFile() {
        String result = generateDiff(file1Json, file3Empty);
        String expected = """
                [{"key":"description","type":"deleted","value":""},
                {"key":"field","type":"deleted","value":null},
                {"key":"main","type":"deleted","value":"index.js"},
                {"key":"name","type":"deleted","value":"resources"},
                {"key":"private","type":"deleted","value":false},
                {"key":"timeout","type":"deleted","value":20},
                {"key":"version","type":"deleted","value":"1.0.0"}]""";
        assertEquals(expected, result);
    }

    @Test
    void checkTwoEmptyJsonFiles() {
        assertEquals("[]", generateDiff(file3Empty, file3Empty));
    }

    @Test
    void checkEqualJsonFiles() {
        String expected = """
                [{"key":"description","type":"unchanged","value":""},
                {"key":"field","type":"unchanged","value":null},
                {"key":"main","type":"unchanged","value":"index.js"},
                {"key":"name","type":"unchanged","value":"resources"},
                {"key":"private","type":"unchanged","value":false},
                {"key":"timeout","type":"unchanged","value":20},
                {"key":"version","type":"unchanged","value":"1.0.0"}]""";
        assertEquals(expected, generateDiff(file1Json, file1Json));
    }

    @Test
    void checkYmlFiles_resultNotNull() {
        assertNotNull(resultYml);
    }

    @Test
    void checkYmlFiles_containsChangedAndUnchangedFields() {
        assertAll(
                () -> assertTrue(resultYml.contains("\"key\":\"email\",\"type\":\"unchanged\",\"value\":\"john@example.com\"")),
                () -> assertTrue(resultYml.contains("\"key\":\"age\",\"type\":\"changed\",\"value\":20,\"previousValue\":30")),
                () -> assertTrue(resultYml.contains("\"key\":\"name\",\"type\":\"changed\",\"value\":\"John\",\"previousValue\":\"John Doe\"")),
                () -> assertTrue(resultYml.contains(
                        "\"key\":\"address\",\"type\":\"changed\",\"value\":{\"street\":\"Steals St\",\"city\":\"New York\",\"zip\":10001},"
                               + "\"previousValue\":{\"street\":\"Main St\",\"city\":\"New York\",\"zip\":10001}")),
                () -> assertTrue(resultYml.contains(
                        "\"key\":\"hobbies\",\"type\":\"changed\",\"value\":[\"reading\",\"coding\"],"
                              + "\"previousValue\":[\"reading\",\"swimming\",\"coding\"]")));
    }

    @Test
    void checkYmlFiles_doesNotContainStylishMarkers() {
        assertAll(
                () -> assertFalse(resultYml.contains("+ ")),
                () -> assertFalse(resultYml.contains("- ")));
    }

    @Test
    void checkOneEmptyYmlFile() {
        String result = generateDiff(file1Yml, file3Empty);
        assertAll(
                () -> assertTrue(result.contains("\"key\":\"name\",\"type\":\"deleted\",\"value\":\"John Doe\"")),
                () -> assertTrue(result.contains("\"key\":\"age\",\"type\":\"deleted\",\"value\":30")),
                () -> assertTrue(result.contains("\"key\":\"email\",\"type\":\"deleted\",\"value\":\"john@example.com\"")),
                () -> assertTrue(result.contains("\"street\":\"Main St\"")),
                () -> assertTrue(result.contains("[\"reading\",\"swimming\",\"coding\"]")));
    }

    @Test
    void checkTwoEmptyYmlFiles() {
        assertEquals("[]", generateDiff(file3Empty, file3Empty));
    }

    @Test
    void checkEqualYmlFiles() {
        String result = generateDiff(file1Yml, file1Yml);
        assertAll(
                () -> assertTrue(result.contains("\"key\":\"name\",\"type\":\"unchanged\",\"value\":\"John Doe\"")),
                () -> assertTrue(result.contains("\"key\":\"age\",\"type\":\"unchanged\",\"value\":30")),
                () -> assertTrue(result.contains("\"key\":\"email\",\"type\":\"unchanged\",\"value\":\"john@example.com\"")),
                () -> assertFalse(result.contains("previousValue")),
                () -> assertFalse(result.contains("John DoeJohn")));
    }

    @Test
    void checkInvalidJsonThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,
                () -> generateDiff(file1Json, file4InvalidJson));
    }
}
