package hexlet.code.formatters;


import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

public final class Stylish {
    private Stylish() {
    }

    public static String generateDiff(Map<String, Object> data1,
                                      Map<String, Object> data2) {

        Set<String> allKeys = new TreeSet<>();
        allKeys.addAll(data1.keySet());
        allKeys.addAll(data2.keySet());

        StringBuilder diff = new StringBuilder("{\n");

        for (String key : allKeys) {
            diff.append(buildDiffLine(key, data1, data2));
        }

        diff.append("}");

        return diff.toString();
    }

    private static String buildDiffLine(String key,
                                        Map<String, Object> data1,
                                        Map<String, Object> data2) {

        if (!data1.containsKey(key)) {
            return "  + " + key + ": "
                    + formatValue(data2.get(key))
                    + "\n";
        }

        if (!data2.containsKey(key)) {
            return "  - " + key + ": "
                    + formatValue(data1.get(key))
                    + "\n";
        }

        Object value1 = data1.get(key);
        Object value2 = data2.get(key);

        if (!Objects.equals(value1, value2)) {
            return "  - " + key + ": "
                    + formatValue(value1)
                    + "\n"
                    + "  + " + key + ": "
                    + formatValue(value2)
                    + "\n";
        }

        return "    " + key + ": "
                + formatValue(value1)
                + "\n";
    }

    private static String formatValue(Object value) {
        return String.valueOf(value);
    }

}
