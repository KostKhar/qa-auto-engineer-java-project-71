package hexlet.code;

import hexlet.code.formatters.Json;
import hexlet.code.formatters.Plain;
import hexlet.code.formatters.Stylish;

import java.util.List;

public final class Formatter {

    private Formatter() {
    }

    public static String format(String formatName, List<DiffNode> diff) {
        if ("stylish".equalsIgnoreCase(formatName)) {
            return Stylish.render(diff);
        } else if ("plain".equalsIgnoreCase(formatName)) {
            return Plain.render(diff);
        } else if ("json".equalsIgnoreCase(formatName)) {
            try {
                return Json.render(DiffBuilder.toJsonMap(diff));
            } catch (Exception e) {
                throw new IllegalArgumentException("Invalid JSON object", e);
            }
        } else {
            throw new IllegalArgumentException("Unknown format: " + formatName);
        }
    }
}
