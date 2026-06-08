package hexlet.code;

import hexlet.code.formatters.Stylish;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static hexlet.code.FileParser.parse;
import static org.junit.jupiter.api.Assertions.*;

class StylishTest extends BaseTest {


    String getResultFromTwoPaths(String file1, String file2) {
        Map<String, Object> data1 = parse(file1);
        Map<String, Object> data2 = parse(file2);
        return Stylish.generateDiff(data1, data2);
    }

    @Test
    void generateDiff_jsonFiles_resultNotNull() {
        assertNotNull(resultJson);
    }

    @Test
    void generateDiff_jsonFiles_returnsExpectedDiff() {
        String expected = """
                  {
                  - description:
                  - field: null
                    main: index.js
                    name: resources
                  - private: false
                  + private: true
                  + proxy: 123.234.53.22
                  - timeout: 20
                  + timeout: 50
                    version: 1.0.0
                }""";
        assertEquals(expected, resultJson);
    }

    @Test
    void generateDiff_jsonFiles_containsUnchangedFields() {
        assertAll(
                () -> assertTrue(resultJson.contains("    main: index.js")),
                () -> assertTrue(resultJson.contains("    name: resources")),
                () -> assertTrue(resultJson.contains("    version: 1.0.0")));
    }

    @Test
    void generateDiff_oneEmptyJsonFile() {
        String result = getResultFromTwoPaths(file1Json, file3Empty);
        assertAll(
                () -> assertTrue(result.contains("  - description: ")),
                () -> assertTrue(result.contains("  - field: null")),
                () -> assertTrue(result.contains("  - main: index.js")),
                () -> assertTrue(result.contains("  - name: resources")),
                () -> assertTrue(result.contains("  - private: false")),
                () -> assertTrue(result.contains("  - timeout: 20")),
                () -> assertTrue(result.contains("  - version: 1.0.0")));
    }

    @Test
    void generateDiff_twoEmptyJsonFiles() {
        assertEquals("{\n}", getResultFromTwoPaths(file3Empty, file3Empty));
    }

    @Test
    void generateDiff_equalJsonFiles() {
        String expected = """
                    {
                    description:
                    field: null
                    main: index.js
                    name: resources
                    private: false
                    timeout: 20
                    version: 1.0.0
                }""";
        assertEquals(expected, getResultFromTwoPaths(file1Json, file1Json));
    }

    @Test
    void generateDiff_ymlFiles_resultNotNull() {
        assertNotNull(resultYml);
    }

    @Test
    void generateDiff_ymlFiles_returnsExpectedDiff() {
        String expected = """
                {
                  - address: {street=Main St, city=New York, zip=10001}
                  + address: {street=Steals St, city=New York, zip=10001}
                  - age: 30
                  + age: 20
                    email: john@example.com
                  - hobbies: [reading, swimming, coding]
                  + hobbies: [reading, coding]
                  - name: John Doe
                  + name: John
                }""";
        assertEquals(expected, resultYml);
    }

    @Test
    void generateDiff_ymlFiles_doesNotContainNestedFieldPaths() {
        assertAll(
                () -> assertFalse(resultYml.contains("hobbies.swimming")),
                () -> assertFalse(resultYml.contains("address.city")));
    }

    @Test
    void generateDiff_oneEmptyYmlFile() {
        String result = getResultFromTwoPaths(file1Yml, file3Empty);
        assertAll(
                () -> assertTrue(result.contains("  - address: {street=Main St, city=New York, zip=10001}")),
                () -> assertTrue(result.contains("  - age: 30")),
                () -> assertTrue(result.contains("  - email: john@example.com")),
                () -> assertTrue(result.contains("  - hobbies: [reading, swimming, coding]")),
                () -> assertTrue(result.contains("  - name: John Doe")));
    }

    @Test
    void generateDiff_twoEmptyYmlFiles() {
        assertEquals("{\n}", getResultFromTwoPaths(file3Empty, file3Empty));
    }

    @Test
    void generateDiff_equalYmlFiles() {
        String expected = """
                {
                    address: {street=Main St, city=New York, zip=10001}
                    age: 30
                    email: john@example.com
                    hobbies: [reading, swimming, coding]
                    name: John Doe
                }""";
        assertEquals(expected, getResultFromTwoPaths(file1Yml, file1Yml));
    }

    @Test
    void generateDiff_shouldShowAddedKeys() {
        Map<String, Object> data1 = new HashMap<>();
        Map<String, Object> data2 = new HashMap<>();
        data2.put("key2", "value2");

        String result = Stylish.generateDiff(data1, data2);

        assertTrue(result.contains("  + key2: value2"));
    }

    @Test
    void generateDiff_shouldShowRemovedKeys() {
        Map<String, Object> data1 = new HashMap<>();
        Map<String, Object> data2 = new HashMap<>();
        data1.put("key1", "value1");

        String result = Stylish.generateDiff(data1, data2);

        assertTrue(result.contains("  - key1: value1"));
    }

    @Test
    void generateDiff_shouldShowChangedKeys() {
        Map<String, Object> data1 = new HashMap<>();
        Map<String, Object> data2 = new HashMap<>();
        data1.put("key", "oldValue");
        data2.put("key", "newValue");

        String result = Stylish.generateDiff(data1, data2);

        assertAll(
                () -> assertTrue(result.contains("  - key: oldValue")),
                () -> assertTrue(result.contains("  + key: newValue")));
    }

    @Test
    void generateDiff_shouldShowUnchangedKeys() {
        Map<String, Object> data1 = new HashMap<>();
        Map<String, Object> data2 = new HashMap<>();
        data1.put("key", "sameValue");
        data2.put("key", "sameValue");

        String result = Stylish.generateDiff(data1, data2);
        assertAll(
                () -> assertTrue(result.contains("    key: sameValue")),
                () -> assertFalse(result.contains("  - key")),
                () -> assertFalse(result.contains("  + key")));
    }

    @Test
    void generateDiff_shouldHandleNestedStructures() {
        Map<String, Object> data1 = new HashMap<>();
        Map<String, Object> data2 = new HashMap<>();

        Map<String, Object> nested1 = new HashMap<>();
        nested1.put("inner", "value1");
        data1.put("nested", nested1);

        Map<String, Object> nested2 = new HashMap<>();
        nested2.put("inner", "value2");
        data2.put("nested", nested2);

        String result = Stylish.generateDiff(data1, data2);
        assertAll(
                () -> assertTrue(result.contains("  - nested: {inner=value1}")),
                () -> assertTrue(result.contains("  + nested: {inner=value2}")));
    }

    @Test
    void generateDiff_shouldHandleNullValues() {
        Map<String, Object> data1 = new HashMap<>();
        Map<String, Object> data2 = new HashMap<>();
        data1.put("key", null);
        data2.put("key", "value");

        String result = Stylish.generateDiff(data1, data2);

        assertAll(
                () -> assertTrue(result.contains("  - key: null")),
                () -> assertTrue(result.contains("  + key: value")));
    }

    @Test
    void checkInvalidJsonThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,
                () -> getResultFromTwoPaths(file1Json, file4InvalidJson));
    }

}