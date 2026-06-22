package hexlet.code;

import hexlet.code.formatters.Json;
import hexlet.code.formatters.Plain;
import hexlet.code.formatters.Stylish;

import java.util.Map;

import static hexlet.code.FileParser.parse;

public final class Formatter {
    private Formatter() {
    }

    public static String generate(String filepath1, String filepath2, String formateName) {
        Map<String, Object> data1 = getData(filepath1);
        Map<String, Object> data2 = getData(filepath2);
        String diff;

        if ("stylish".equalsIgnoreCase(formateName)) {
            diff = Stylish.generateDiff(data1, data2);
        } else if ("plain".equalsIgnoreCase(formateName)) {
            diff = Plain.generateDiff(data1, data2);
        } else if ("json".equalsIgnoreCase(formateName)) {
            diff = Json.generateDiff(data1, data2);
        } else {
            throw new IllegalArgumentException("Unknown format: " + formateName);
        }
        return diff;
    }

    public static String generate(String filePath1, String filePath2) {
        return generate(filePath1, filePath2, "stylish");
    }

    public static Map<String, Object> getData(String content) {
        return parse(content);
    }
}
