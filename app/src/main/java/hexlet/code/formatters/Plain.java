package hexlet.code.formatters;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

public final class Plain {

    private static final String PROPERTY_WAS_UPDATED = "Property '%s' was updated. From %s to %s";
    private static final String PROPERTY_WAS_ADDED= "Property '%s' was added  with value: %s";
    private static final String PROPERTY_WAS_DELETED = "Property '%s' was removed";


    private Plain() {
    }

    public static String generateDiff(Map<String, Object> data1, Map<String, Object> data2) {
        Set<String> allKeys = new TreeSet<>();
        allKeys.addAll(data1.keySet());
        allKeys.addAll(data2.keySet());
        StringBuilder diff = new StringBuilder();

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

        return diff.toString();
    }

    private static String formatKeyWithOtherValue(String key, Object oldValue, Object newValue) {
        return String.format(PROPERTY_WAS_UPDATED, key,
                formatValue(oldValue),
                formatValue(newValue));
    }

    private static String formatKeyWasRemoved(String key) {
        return String.format(PROPERTY_WAS_DELETED, key);
    }

    private static String formatKeyWasAdded(String key, Object value) {
        return String.format(PROPERTY_WAS_ADDED, key, formatValue(value));
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
