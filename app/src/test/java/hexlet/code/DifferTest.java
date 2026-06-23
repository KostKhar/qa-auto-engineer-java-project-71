package hexlet.code;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class DifferTest {

    private static final String FILE1_JSON = "src/test/resources/file1.json";
    private static final String FILE2_JSON = "src/test/resources/file2.json";
    private static final String STYLISH_FORMAT = "stylish";

    @Test
    void generate_defaultFormat_resultNotNull() {
        assertNotNull(Differ.generate(FILE1_JSON, FILE2_JSON));
    }

    @Test
    void generate_defaultFormat_equalsExplicitStylish() {
        assertEquals(
                Differ.generate(FILE1_JSON, FILE2_JSON, STYLISH_FORMAT),
                Differ.generate(FILE1_JSON, FILE2_JSON));
    }

    @Test
    void generate_withFormat_delegatesToFormatter() {
        assertEquals(
                Formatter.generate(FILE1_JSON, FILE2_JSON, STYLISH_FORMAT),
                Differ.generate(FILE1_JSON, FILE2_JSON, STYLISH_FORMAT));
    }

    @Test
    void getData_delegatesToFormatter() {
        assertEquals(Formatter.getData(FILE1_JSON), Differ.getData(FILE1_JSON));
    }
}
