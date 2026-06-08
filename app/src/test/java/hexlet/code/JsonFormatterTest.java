package hexlet.code;

import hexlet.code.formatters.JsonFormatter;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static hexlet.code.FileParser.parse;
import static org.junit.jupiter.api.Assertions.*;

class JsonFormatterTest extends BaseTest {

    String getResultFromTwoPaths(String file1, String file2) {
        Map<String, Object> data1 = parse(file1);
        Map<String, Object> data2 = parse(file2);
        return JsonFormatter.generateDiff(data1, data2);
    }

    @Test
    void jsonFiles_resultNotNull() {
        assertNotNull(resultJson);
    }

    @Test
    void jsonFiles_returnsExpectedDiff() {
        String expected = """
                {
                  "description" : "",
                  "field" : null,
                  "main" : "index.js",
                  "name" : "resources",
                  "private" : "falsetrue",
                  "proxy" : "123.234.53.22",
                  "timeout" : "2050",
                  "version" : "1.0.0"
                }""";
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
                () -> assertTrue(resultJson.contains("\"main\" : \"index.js\"")),
                () -> assertTrue(resultJson.contains("\"name\" : \"resources\"")),
                () -> assertTrue(resultJson.contains("\"version\" : \"1.0.0\"")));
    }

    @Test
    void checkOneEmptyJsonFile() {
        String result = getResultFromTwoPaths(file1Json, file3Empty);
        String expected = """
                {
                  "description" : "",
                  "field" : null,
                  "main" : "index.js",
                  "name" : "resources",
                  "private" : false,
                  "timeout" : 20,
                  "version" : "1.0.0"
                }""";
        assertEquals(expected, result);
    }

    @Test
    void checkTwoEmptyJsonFiles() {
        assertEquals("{ }", getResultFromTwoPaths(file3Empty, file3Empty));
    }

    @Test
    void checkEqualJsonFiles() {
        String expected = """
                {
                  "description" : "",
                  "field" : null,
                  "main" : "index.js",
                  "name" : "resources",
                  "private" : false,
                  "timeout" : 20,
                  "version" : "1.0.0"
                }""";
        assertEquals(expected, getResultFromTwoPaths(file1Json, file1Json));
    }

    @Test
    void checkYmlFiles_resultNotNull() {
        assertNotNull(resultYml);
    }

    @Test
    void checkYmlFiles_containsChangedAndUnchangedFields() {
        assertAll(
                () -> assertTrue(resultYml.contains("\"email\" : \"john@example.com\"")),
                () -> assertTrue(resultYml.contains("\"age\" : \"3020\"")),
                () -> assertTrue(resultYml.contains("\"name\" : \"John DoeJohn\"")),
                () -> assertTrue(resultYml.contains(
                        "\"address\" : \"{street=Main St, city=New York, zip=10001}"
                                + "{street=Steals St, city=New York, zip=10001}\"")),
                () -> assertTrue(resultYml.contains(
                        "\"hobbies\" : \"[reading, swimming, coding][reading, coding]\"")));
    }

    @Test
    void checkYmlFiles_doesNotContainStylishMarkers() {
        assertAll(
                () -> assertFalse(resultYml.contains("+ ")),
                () -> assertFalse(resultYml.contains("- ")));
    }

    @Test
    void checkOneEmptyYmlFile() {
        String result = getResultFromTwoPaths(file1Yml, file3Empty);
        assertAll(
                () -> assertTrue(result.contains("\"name\" : \"John Doe\"")),
                () -> assertTrue(result.contains("\"age\" : 30")),
                () -> assertTrue(result.contains("\"email\" : \"john@example.com\"")),
                () -> assertTrue(result.contains("\"street\" : \"Main St\"")),
                () -> assertTrue(result.contains("[ \"reading\", \"swimming\", \"coding\" ]")));
    }

    @Test
    void checkTwoEmptyYmlFiles() {
        assertEquals("{ }", getResultFromTwoPaths(file3Empty, file3Empty));
    }

    @Test
    void checkEqualYmlFiles() {
        String result = getResultFromTwoPaths(file1Yml, file1Yml);
        assertAll(
                () -> assertTrue(result.contains("\"name\" : \"John Doe\"")),
                () -> assertTrue(result.contains("\"age\" : 30")),
                () -> assertTrue(result.contains("\"email\" : \"john@example.com\"")),
                () -> assertFalse(result.contains("John DoeJohn")),
                () -> assertFalse(result.contains("3020")));
    }

    @Test
    void checkInvalidJsonThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,
                () -> getResultFromTwoPaths(file1Json, file4InvalidJson));
    }
}
