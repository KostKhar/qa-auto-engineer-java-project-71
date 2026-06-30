package hexlet.code;

import java.util.List;
import java.util.Map;

public final class Differ {

    private Differ() {
    }

    public static String generate(String filepath1, String filepath2, String formatName) {
        Map<String, Object> data1 = FileParser.parse(filepath1);
        Map<String, Object> data2 = FileParser.parse(filepath2);
        List<DiffNode> diff = DiffBuilder.build(data1, data2);
        return Formatter.format(formatName, diff);
    }

    public static String generate(String filepath1, String filepath2) {
        return generate(filepath1, filepath2, "stylish");
    }

}
