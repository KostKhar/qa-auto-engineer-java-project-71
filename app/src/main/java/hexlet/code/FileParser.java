package hexlet.code;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

public final class FileParser {

    private FileParser() {
    }

    public static Map<String, Object> parse(String filepath) {
        if (filepath == null) {
            throw new IllegalArgumentException("File argument must not be null");
        }
        try {
            Path absolutePath = resolvePath(filepath);
            String content = Files.readString(absolutePath);
            String format = resolveFormat(absolutePath.getFileName().toString());
            return Parser.parse(content, format);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error reading file: " + e.getMessage());
        }
    }

    private static String resolveFormat(String fileName) {
        if (fileName.endsWith(".yml") || fileName.endsWith(".yaml")) {
            return "yaml";
        }
        return "json";
    }

    private static Path resolvePath(String path) {
        String userDir = System.getProperty("user.dir");

        String[] basePaths = {
                userDir,
                userDir + "/src/main/resources",
                userDir + "/src/test/resources",
        };

        for (String basePath : basePaths) {
            Path fullPath = Paths.get(basePath).resolve(path);
            if (Files.exists(fullPath)) {
                return fullPath.normalize();
            }
        }

        return Paths.get(userDir).resolve(path).normalize();
    }
}
