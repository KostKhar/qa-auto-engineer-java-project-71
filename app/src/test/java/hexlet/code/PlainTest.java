package hexlet.code;

import hexlet.code.formatters.Plain;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static hexlet.code.FileParser.parse;
import static org.junit.jupiter.api.Assertions.*;

class PlainTest extends BaseTest {

    String generateDiff(String file1, String file2) {
        Map<String, Object> data1 = parse(file1);
        Map<String, Object> data2 = parse(file2);
        return Plain.generateDiff(data1, data2);
    }

    @Test
    void generateDiff_jsonFiles_resultNotNull() {
        assertNotNull(resultJson);
    }

    @Test
    void generateDiff_jsonFiles_returnsExpectedDiff() {
        String expected = """
                Property 'description' was removed
                Property 'field' was removed
                Property 'private' was updated. From false to true
                Property 'proxy' was added with value: '123.234.53.22'
                Property 'timeout' was updated. From 20 to 50""";
        System.out.println(resultJson);

        assertEquals(expected, resultJson);
    }

    @Test
    void generateDiff_jsonFiles_doesNotContainUnchangedFields() {
        assertAll(
                () -> assertFalse(resultJson.contains("main")),
                () -> assertFalse(resultJson.contains("name")),
                () -> assertFalse(resultJson.contains("version")));
    }

    @Test
    void generateDiff_jsonFiles_doesNotContainStylishMarkers() {
        assertAll(
                () -> assertFalse(resultJson.contains("+ ")),
                () -> assertFalse(resultJson.contains("- ")));
    }

    @Test
    void generateDiff_oneEmptyJsonFile() {
        String result = generateDiff(file1Json, file3Empty);
        assertAll(
                () -> assertTrue(result.contains("Property 'description' was removed")),
                () -> assertTrue(result.contains("Property 'field' was removed")),
                () -> assertTrue(result.contains("Property 'main' was removed")),
                () -> assertTrue(result.contains("Property 'name' was removed")),
                () -> assertTrue(result.contains("Property 'private' was removed")),
                () -> assertTrue(result.contains("Property 'timeout' was removed")),
                () -> assertTrue(result.contains("Property 'version' was removed")));
    }

    @Test
    void generateDiff_twoEmptyJsonFiles() {
        assertEquals("", generateDiff(file3Empty, file3Empty));
    }

    @Test
    void generateDiff_equalJsonFiles() {
        assertEquals("", generateDiff(file1Json, file1Json));
    }

    @Test
    void generateDiff_ymlFiles_resultNotNull() {
        assertNotNull(resultYml);
    }

    @Test
    void generateDiff_ymlFiles_returnsExpectedDiff() {
        String expected = """
                Property 'address' was updated. From [complex value] to [complex value]
                Property 'age' was updated. From 30 to 20
                Property 'hobbies' was updated. From [complex value] to [complex value]
                Property 'name' was updated. From 'John Doe' to 'John'""";
        System.out.println(resultYml);
        assertEquals(expected, resultYml);
    }

    @Test
    void generateDiff_ymlFiles_doesNotContainUnchangedFields() {
        assertFalse(resultYml.contains("email"));
    }

    @Test
    void generateDiff_oneEmptyYmlFile() {
        String result = generateDiff(file1Yml, file3Empty);
        assertAll(
                () -> assertTrue(result.contains("Property 'address' was removed")),
                () -> assertTrue(result.contains("Property 'age' was removed")),
                () -> assertTrue(result.contains("Property 'email' was removed")),
                () -> assertTrue(result.contains("Property 'hobbies' was removed")),
                () -> assertTrue(result.contains("Property 'name' was removed")));
    }

    @Test
    void generateDiff_twoEmptyYmlFiles() {
        assertEquals("", generateDiff(file3Empty, file3Empty));
    }

    @Test
    void generateDiff_equalYmlFiles() {
        assertEquals("", generateDiff(file1Yml, file1Yml));
    }

    @Test
    void checkInvalidJsonThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,
                () -> generateDiff(file1Json, file4InvalidJson));
    }

    @Test
    void generateDiff_addedNullValue_formatsAsNull() {
        Map<String, Object> data1 = Map.of();
        Map<String, Object> data2 = new LinkedHashMap<>();
        data2.put("field", null);

        assertEquals(
                "Property 'field' was added with value: null",
                Plain.generateDiff(data1, data2));
    }
}
