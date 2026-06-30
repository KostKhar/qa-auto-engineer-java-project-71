package hexlet.code;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FormatterTest extends BaseTest {

    private static final String DEFAULT_FORMAT = "stylish";
    private static final String PLAIN_FORMAT = "plain";
    private static final String JSON_FORMAT = "json";

    private String format(String file1, String file2, String formatName) {
        return Formatter.format(
                formatName,
                DiffBuilder.build(FileParser.parse(file1), FileParser.parse(file2)));
    }

    private String format(String file1, String file2) {
        return format(file1, file2, DEFAULT_FORMAT);
    }

    @Override
    String generateDiff(String file1, String file2) {
        return format(file1, file2, DEFAULT_FORMAT);
    }

    @Test
    void format_stylish_resultNotNull() {
        assertNotNull(format(file1Json, file2Json, DEFAULT_FORMAT));
    }

    @Test
    void format_jsonFiles_stylish_returnsExpectedDiff() {
        String result = format(file1Json, file2Json);

        assertAll(
                () -> assertTrue(result.contains("- description:"), "- description: not found"),
                () -> assertTrue(result.contains("- field: null"), "- field: null not found"),
                () -> assertTrue(result.contains("main: index.js"), "main: index.js not found"),
                () -> assertTrue(result.contains("name: resources"), "name: resources not found"),
                () -> assertTrue(result.contains("- private: false"), "- private: false not found"),
                () -> assertTrue(result.contains("+ private: true"), "+ private: true not found"),
                () -> assertTrue(result.contains("- timeout: 20"), "- timeout: 20 not found"),
                () -> assertTrue(result.contains("+ timeout: 50"), "- timeout: 50 not found"),
                () -> assertTrue(result.contains("version: 1.0.0"), "version: 1.0.0 not found")
        );
    }

    @Test
    void format_jsonFiles_stylish_containsUnchangedFields() {
        String result = format(file1Json, file2Json);
        assertAll(
                () -> assertTrue(result.contains("    main: index.js")),
                () -> assertTrue(result.contains("    name: resources")),
                () -> assertTrue(result.contains("    version: 1.0.0")));
    }

    @Test
    void format_oneEmptyJsonFile_stylish_allRemovedMarkers() {
        String result = format(file1Json, file3Empty, DEFAULT_FORMAT);
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
    void format_twoEmptyJsonFiles_stylish_returnsEmptyBraces() {
        assertEquals("{\n}", format(file3Empty, file3Empty));
    }

    @Test
    void format_equalJsonFiles_stylish_noChangedMarkers() {
        String result = format(file1Json, file1Json, DEFAULT_FORMAT);
        assertAll(
                () -> assertFalse(result.contains("  - ")),
                () -> assertFalse(result.contains("  + ")));
    }

    @Test
    void format_jsonFiles_plain_resultNotNull() {
        assertNotNull(format(file1Json, file2Json, PLAIN_FORMAT));
    }

    @Test
    void format_jsonFiles_plain_returnsExpectedDiff() {
        String expected = """
                Property 'description' was removed
                Property 'field' was removed
                Property 'private' was updated. From false to true
                Property 'proxy' was added with value: '123.234.53.22'
                Property 'timeout' was updated. From 20 to 50""";
        assertEquals(expected, format(file1Json, file2Json, PLAIN_FORMAT));
    }

    @Test
    void format_jsonFiles_plain_doesNotContainUnchangedFields() {
        String result = format(file1Json, file2Json, PLAIN_FORMAT);
        assertAll(
                () -> assertFalse(result.contains("main")),
                () -> assertFalse(result.contains("name")),
                () -> assertFalse(result.contains("version")));
    }

    @Test
    void format_oneEmptyJsonFile_plain_allRemovedMessages() {
        String result = format(file1Json, file3Empty, PLAIN_FORMAT);
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
    void format_twoEmptyJsonFiles_plain_returnsEmptyString() {
        assertEquals("", format(file3Empty, file3Empty, PLAIN_FORMAT));
    }

    @Test
    void format_equalJsonFiles_plain_returnsEmptyString() {
        assertEquals("", format(file1Json, file1Json, PLAIN_FORMAT));
    }

    @Test
    void format_jsonFiles_json_resultNotNull() {
        assertNotNull(format(file1Json, file2Json, JSON_FORMAT));
    }

    @Test
    void format_jsonFiles_json_doesNotContainStylishMarkers() {
        String result = format(file1Json, file2Json, JSON_FORMAT);
        assertAll(
                () -> assertFalse(result.contains("+ ")),
                () -> assertFalse(result.contains("- ")));
    }

    @Test
    void format_jsonFiles_json_containsUnchangedFieldsWithOriginalValues() {
        String result = format(file1Json, file2Json, JSON_FORMAT);
        assertAll(
                () -> assertTrue(result.contains("\"key\":\"main\",\"type\":\"unchanged\",\"value\":\"index.js\"")),
                () -> assertTrue(result.contains("\"key\":\"name\",\"type\":\"unchanged\",\"value\":\"resources\"")),
                () -> assertTrue(result.contains("\"key\":\"version\",\"type\":\"unchanged\",\"value\":\"1.0.0\"")));
    }

    @Test
    void format_twoEmptyJsonFiles_json_returnsEmptyObject() {
        assertEquals("[]", format(file3Empty, file3Empty, JSON_FORMAT));
    }

    @Test
    void format_ymlFiles_stylish_resultNotNull() {
        assertNotNull(format(file1Yml, file2Yml, DEFAULT_FORMAT));
    }

    @Test
    void format_ymlFiles_stylish_returnsExpectedDiff() {
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
        assertEquals(expected, format(file1Yml, file2Yml, DEFAULT_FORMAT));
    }

    @Test
    void format_oneEmptyYmlFile_stylish_allRemovedMarkers() {
        String result = format(file1Yml, file3Empty, DEFAULT_FORMAT);
        assertAll(
                () -> assertTrue(result.contains("  - address: {street=Main St, city=New York, zip=10001}")),
                () -> assertTrue(result.contains("  - age: 30")),
                () -> assertTrue(result.contains("  - email: john@example.com")),
                () -> assertTrue(result.contains("  - hobbies: [reading, swimming, coding]")),
                () -> assertTrue(result.contains("  - name: John Doe")));
    }

    @Test
    void format_twoEmptyYmlFiles_stylish_returnsEmptyBraces() {
        assertEquals("{\n}", format(file3Empty, file3Empty, DEFAULT_FORMAT));
    }

    @Test
    void format_equalYmlFiles_stylish_noChangedMarkers() {
        String result = format(file1Yml, file1Yml, DEFAULT_FORMAT);
        assertAll(
                () -> assertFalse(result.contains("  - ")),
                () -> assertFalse(result.contains("  + ")));
    }

    @Test
    void format_ymlFiles_plain_resultNotNull() {
        assertNotNull(format(file1Yml, file2Yml, PLAIN_FORMAT));
    }

    @Test
    void format_ymlFiles_plain_returnsExpectedDiff() {
        String expected = """
                Property 'address' was updated. From [complex value] to [complex value]
                Property 'age' was updated. From 30 to 20
                Property 'hobbies' was updated. From [complex value] to [complex value]
                Property 'name' was updated. From 'John Doe' to 'John'""";
        assertEquals(expected, format(file1Yml, file2Yml, PLAIN_FORMAT));
    }

    @Test
    void format_ymlFiles_plain_doesNotContainUnchangedFields() {
        assertFalse(format(file1Yml, file2Yml, PLAIN_FORMAT).contains("email"));
    }

    @Test
    void format_equalYmlFiles_plain_returnsEmptyString() {
        assertEquals("", format(file1Yml, file1Yml, PLAIN_FORMAT));
    }

    @Test
    void format_ymlFiles_json_resultNotNull() {
        assertNotNull(format(file1Yml, file2Yml, JSON_FORMAT));
    }

    @Test
    void format_ymlFiles_json_doesNotContainStylishMarkers() {
        String result = format(file1Yml, file2Yml, JSON_FORMAT);
        assertAll(
                () -> assertFalse(result.contains("+ ")),
                () -> assertFalse(result.contains("- ")));
    }

    @Test
    void format_formatCaseInsensitive_stylish() {
        assertEquals(
                format(file1Json, file2Json, DEFAULT_FORMAT),
                format(file1Json, file2Json, "STYLISH"));
    }

    @Test
    void format_formatCaseInsensitive_plain() {
        assertEquals(
                format(file1Json, file2Json, PLAIN_FORMAT),
                format(file1Json, file2Json, "PLAIN"));
    }

    @Test
    void format_formatCaseInsensitive_json() {
        assertEquals(
                format(file1Json, file2Json, JSON_FORMAT),
                format(file1Json, file2Json, "JSON"));
    }

    @Test
    void format_unknownFormat_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,
                () -> Formatter.format(
                        "unknown",
                        DiffBuilder.build(FileParser.parse(file1Json), FileParser.parse(file2Json))));
    }
}
