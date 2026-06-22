package hexlet.code;

import java.util.Map;

public final class Differ {

    private Differ() {
    }

    public static String generate(String filepath1, String filepath2, String formatName) {
        return Formatter.generate(filepath1, filepath2, formatName);
    }

    public static String generate(String filepath1, String filepath2) {
        return Formatter.generate(filepath1, filepath2);
    }

    public static Map<String, Object> getData(String filepath) {
        return Formatter.getData(filepath);
    }
}
