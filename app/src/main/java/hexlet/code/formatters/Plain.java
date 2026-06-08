package hexlet.code.formatters;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

public final class Plain {

    private static final String PROPERTY = "Property '";

    private Plain() {
    }

    public static String generateDiff(Map<String, Object> data1, Map<String, Object> data2) {
        Set<String> allKeys = new TreeSet<>();
        allKeys.addAll(data1.keySet());
        allKeys.addAll(data2.keySet());
        StringBuilder diff = new StringBuilder("{\n");

        for (String key : allKeys) {
            if (!data1.containsKey(key)) {
                diff.append(formatKeyWasAdded(key, data2.get(key))).append("\n");

            } else if (!data2.containsKey(key)) {
                diff.append(formatKeyWasRemoved(key)).append("\n");

            } else if (!Objects.equals(data1.get(key), data2.get(key))) {
                diff.append(formatKeyWithOtherValue(key, data1.get(key), data2.get(key)))
                        .append("\n");
            }
        }

        diff.append("}");
        return diff.toString();
    }

    private static String formatKeyWithOtherValue(String key, Object oldValue, Object newValue) {
        if (!(oldValue instanceof String)) {
            oldValue = formatValue(oldValue);
        }

        if (!(newValue instanceof String)) {
            newValue = formatValue(newValue);
        }
        return PROPERTY + key + "' was updated. From " + oldValue + " to " + newValue;
    }

    private static String formatKeyWasRemoved(String key) {
        return PROPERTY + key + "' was removed";
    }

    private static String formatKeyWasAdded(String key, Object value) {
        if (!(value instanceof String)) {
            value = formatValue(value);
        }
        return PROPERTY + key + "' was added with value: " + value;
    }

    private static String formatValue(Object value) {
        return String.valueOf(value);
    }
}
