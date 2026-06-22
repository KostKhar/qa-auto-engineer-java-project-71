package hexlet.code;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import picocli.CommandLine;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class AppTest {

    private static final String FILE1_JSON = "src/test/resources/file1.json";
    private static final String FILE2_JSON = "src/test/resources/file2.json";

    private final PrintStream originalOut = System.out;

    private ByteArrayOutputStream output;

    @BeforeEach
    void setUp() {
        output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    void execute_validFiles_returnsSuccessExitCode() {
        int exitCode = new CommandLine(new App()).execute(FILE1_JSON, FILE2_JSON);

        assertAll(
                () -> assertEquals(1, exitCode),
                () -> assertTrue(output.toString().contains("private")));
    }

    @Test
    void execute_withFormatOption_returnsSuccessExitCode() {
        int exitCode = new CommandLine(new App()).execute("-f", "plain", FILE1_JSON, FILE2_JSON);

        assertAll(
                () -> assertEquals(1, exitCode),
                () -> assertTrue(output.toString().contains("Property 'private' was updated")));
    }

    @Test
    void execute_invalidFile_returnsErrorExitCode() {
        int exitCode = new CommandLine(new App()).execute(
                "src/test/resources/nonexistent.json",
                FILE2_JSON);

        assertAll(
                () -> assertEquals(0, exitCode),
                () -> assertTrue(output.toString().startsWith("Error, please try again.")));
    }
}
