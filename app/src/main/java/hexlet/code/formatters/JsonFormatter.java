package hexlet.code.formatters;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

public final class JsonFormatter {
    private static final ObjectMapper JSON_MAPPER = new ObjectMapper();

    private JsonFormatter() {
    }

    public static String generateDiff(Map<String, Object> data1,
                                      Map<String, Object> data2) {

        Map<String, Object> allKeys = new TreeMap<>();
        allKeys.putAll(data1);
        allKeys.putAll(data2);

        Map<String, Object> diff = new TreeMap<>();

        for (String key : allKeys.keySet()) {
            diff.put(key, getDiffValue(key, data1, data2));
        }

        try {
            return JSON_MAPPER.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(diff);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid JSON object", e);
        }
    }

    private static Object getDiffValue(String key,
                                       Map<String, Object> data1,
                                       Map<String, Object> data2) {

        if (!data1.containsKey(key)) {
            return data2.get(key);
        }

        if (!data2.containsKey(key)) {
            return data1.get(key);
        }

        Object value1 = data1.get(key);
        Object value2 = data2.get(key);

        if (!Objects.equals(value1, value2)) {
            return value1 + ", " + value2;
        }
        return value1;
    }

}
