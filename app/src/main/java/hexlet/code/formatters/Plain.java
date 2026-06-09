package hexlet.code.formatters;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

public final class Plain {

    private static final String PROPERTY_WAS_UPDATED = "Property '%s' was updated. From %s to %s";
    private static final String PROPERTY_WAS_ADDED = "Property '%s' was added with value: %s";
    private static final String PROPERTY_WAS_DELETED = "Property '%s' was removed";


    private Plain() {
    }

    public static String generateDiff(Map<String, Object> data1, Map<String, Object> data2) {
        Set<String> allKeys = new TreeSet<>(data1.keySet());
        allKeys.addAll(data2.keySet());
        StringBuilder diff = new StringBuilder();

        for (String key : allKeys) {
            String line = formatKey(key, data1, data2);
            if (line != null) {
                diff.append(line).append("\n");
            }
        }

        String result = diff.toString();
        if (result.endsWith("\n")) {
            return result.substring(0, result.length() - 1);
        }
        return result;
    }

    private static String formatKey(String key, Map<String, Object> data1, Map<String, Object> data2) {
        if (!data1.containsKey(key)) {
            return String.format(PROPERTY_WAS_ADDED, key, formatValue(data2.get(key)));
        } else if (!data2.containsKey(key)) {
            return String.format(PROPERTY_WAS_DELETED, key);
        } else if (!Objects.equals(data1.get(key), data2.get(key))) {
            return String.format(PROPERTY_WAS_UPDATED, key, formatValue(data1.get(key)), formatValue(data2.get(key)));
        }
        return null;
    }

    private static String formatValue(Object value) {
        if (value instanceof String) {
            return String.format("'%s'", value);
        } else if (value instanceof Number || value instanceof Boolean) {
            return String.valueOf(value);
        } else if (value == null) {
            return "null";
        } else {
            return "[complex value]";
        }
    }
}
