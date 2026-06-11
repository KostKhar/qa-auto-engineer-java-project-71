package hexlet.code;

import org.junit.jupiter.api.BeforeEach;

abstract class BaseTest {
    protected final String file1Json = "src/test/resources/file1.json";
    protected final String file2Json = "src/test/resources/file2.json";
    protected final String file3Empty = "src/test/resources/file3.json";
    protected final String file4InvalidJson = "src/test/resources/file4.json";
    protected final String file1Yml = "src/test/resources/file1Yml.yml";
    protected final String file2Yml = "src/test/resources/file2Yml.yml";

    protected String resultJson;
    protected String resultYml;

    @BeforeEach
    void setUp() {
        resultJson = generateDiff(file1Json, file2Json);
        resultYml = generateDiff(file1Yml, file2Yml);
    }

    abstract String generateDiff(String file1, String file2);

}
