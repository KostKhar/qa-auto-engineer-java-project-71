package hexlet.code;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

public final class DiffBuilder {

    private DiffBuilder() {
    }

    public static List<DiffNode> build(Map<String, Object> data1, Map<String, Object> data2) {
        Set<String> allKeys = new TreeSet<>();
        allKeys.addAll(data1.keySet());
        allKeys.addAll(data2.keySet());

        List<DiffNode> diff = new ArrayList<>();
        for (String key : allKeys) {
            diff.add(buildNode(key, data1, data2));
        }
        return diff;
    }

    public static Map<String, Object> toJsonMap(List<DiffNode> diff) {
        Map<String, Object> result = new LinkedHashMap<>();
        for (DiffNode node : diff) {
            result.put(node.getKey(), toJsonValue(node));
        }
        return result;
    }

    private static Object toJsonValue(DiffNode node) {
        if ("changed".equals(node.getType())) {
            return node.getPreviousValue() + ", " + node.getValue();
        }
        return node.getValue();
    }

    private static DiffNode buildNode(String key, Map<String, Object> data1, Map<String, Object> data2) {
        if (!data1.containsKey(key)) {
            return new DiffNode(key, "added", data2.get(key));
        }

        if (!data2.containsKey(key)) {
            return new DiffNode(key, "deleted", data1.get(key));
        }

        Object value1 = data1.get(key);
        Object value2 = data2.get(key);

        if (!Objects.equals(value1, value2)) {
            return new DiffNode(key, "changed", value2, value1);
        }

        return new DiffNode(key, "unchanged", value1);
    }
}
